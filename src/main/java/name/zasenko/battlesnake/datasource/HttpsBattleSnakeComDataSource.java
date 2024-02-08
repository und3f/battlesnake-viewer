package name.zasenko.battlesnake.datasource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import name.zasenko.battlesnake.entities.Game;
import name.zasenko.battlesnake.entities.engine.EngineEvent;
import name.zasenko.battlesnake.entities.engine.EngineGameResponse;
import name.zasenko.battlesnake.mapper.EngineEventMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.WebSocket;
import java.util.List;
import java.util.Optional;
import java.util.Vector;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

public class HttpsBattleSnakeComDataSource implements DataSource, CachableDataSource {
    private static final Logger log = LoggerFactory.getLogger(HttpsBattleSnakeComDataSource.class);
    private static final ObjectMapper objectMapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    private static final String battleSnakeGameInfoUrl = "https://engine.battlesnake.com/games/%s";
    private static final String battleSnakeEngineUrl = "wss://engine.battlesnake.com/games/%s/events";
    private final String gameId;

    public HttpsBattleSnakeComDataSource(URI uri) {
        this.gameId = Optional.ofNullable(uri.getHost()).orElseThrow();
        String path = uri.getPath();
        if (path != null && !path.isEmpty()) {
            path = path.substring(1);
        }
    }

    @Override
    public List<Game> readFrames() throws IOException {
        log.info("Loading game {}.", gameId);

        HttpClient client = HttpClient.newHttpClient();

        EngineGameResponse gameResp = null;
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(battleSnakeGameInfoUrl.formatted(gameId)))
                    .build();
            String response = client.send(request, HttpResponse.BodyHandlers.ofString()).body();

            gameResp = objectMapper.readValue(response, EngineGameResponse.class);
        } catch (InterruptedException e) {
            throw new IOException("Failed to load game from engine.", e);
        }

        List<EngineEvent> frames = getEngineEvents(client);
        if (frames.size() != gameResp.lastFrame().turn() + 1) {
            throw new IOException("Failed to load all game frames.");
        }
        // Remove last element
        frames.remove(frames.size()-1);

        EngineEventMapper engineEventMapper = new EngineEventMapper(gameResp.gameInfo());
        return frames.stream().map(f -> engineEventMapper.toGame(f.data())).toList();
    }

    private List<EngineEvent> getEngineEvents(HttpClient client) throws IOException {
        log.info("Loading Battlesnake engine events.");

        BattleSnakeEventsListener listener = new BattleSnakeEventsListener();

        URI engineUri = URI.create(battleSnakeEngineUrl.formatted(gameId));
        try {
            client.newWebSocketBuilder().buildAsync(engineUri, listener).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new IOException("Failed to load game from Battlesnake engine.", e);
        }

        List<EngineEvent> frames = listener.join();
        log.info("Received frames count: {}.", frames.size());

        return frames;
    }

    @Override
    public String cachePrefix() {
        return "engine.battlesnake.com";
    }

    @Override
    public String itemId() {
        return gameId;
    }

    public static class BattleSnakeEventsListener implements WebSocket.Listener {
        private final List<EngineEvent> frames = new Vector<>();
        public CompletableFuture<List<EngineEvent>> accumulatedFrames = new CompletableFuture<>();
        private StringBuilder sb = new StringBuilder();
        private Boolean completed = false;
        private CompletableFuture<?> accumulatedMessage = new CompletableFuture<>();

        @Override
        public CompletionStage<?> onText(WebSocket ws, CharSequence message, boolean last) {
            sb.append(message);

            ws.request(1);
            if (last) {
                try {
                    EngineEvent frame = objectMapper.readValue(sb.toString(), EngineEvent.class);
                    if (frame.type().equals("frame")) {
                        frames.add(frame);
                    }

                    sb = new StringBuilder();
                } catch (JsonProcessingException e) {
                    throw new RuntimeException("Failed to parse engine event.", e);
                }

                accumulatedMessage.complete(null);

                CompletionStage<?> cf = accumulatedMessage;
                accumulatedMessage = new CompletableFuture<>();
                return cf;
            }

            return accumulatedMessage;
        }

        List<EngineEvent> join() {
            return accumulatedFrames.join();
        }

        @Override
        public void onError(WebSocket ws, Throwable error) {
            log.error("Websocket error.", error);
        }

        @Override
        public CompletionStage<?> onClose(WebSocket ws, int statusCode, String reason) {
            accumulatedFrames.complete(frames);

            return new CompletableFuture<>();
        }

    }

}
