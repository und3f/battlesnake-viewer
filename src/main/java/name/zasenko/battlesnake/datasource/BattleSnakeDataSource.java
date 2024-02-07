package name.zasenko.battlesnake.datasource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import name.zasenko.battlesnake.entities.Game;
import name.zasenko.battlesnake.entities.battlesnake.EngineEvent;
import name.zasenko.battlesnake.entities.battlesnake.EngineEventFrameData;
import name.zasenko.battlesnake.entities.battlesnake.EngineGameResponse;
import name.zasenko.battlesnake.mapper.EngineEventAdapter;
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

public class BattleSnakeDataSource implements DataSource {
    private static final Logger log = LoggerFactory.getLogger(BattleSnakeDataSource.class);
    private static final ObjectMapper objectMapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    private static final String battleSnakeGameInfoUrl = "https://engine.battlesnake.com/games/%s";
    private static final String battleSnakeEngineUrl = "wss://engine.battlesnake.com/games/%s/events";
    private final String gameId;
    private final Integer frameId;

    public BattleSnakeDataSource(URI uri) {
        this.gameId = Optional.ofNullable(uri.getHost()).orElseThrow();
        String path = uri.getPath();
        if (path != null && !path.isEmpty()) {
            path = path.substring(1);
        }

        this.frameId = (path == null) || path.isEmpty() ? null : Integer.valueOf(path);
    }

    @Override
    public Game readState() throws IOException {
        log.info("Loading game {}, frame {}.", gameId, frameId);

        HttpClient client = HttpClient.newHttpClient();

        EngineGameResponse gameResp = null;
        try {
            String response = client.send(HttpRequest.newBuilder().uri(URI.create(battleSnakeGameInfoUrl.formatted(gameId))).build(), HttpResponse.BodyHandlers.ofString()).body();

            gameResp = objectMapper.readValue(response, EngineGameResponse.class);
        } catch (InterruptedException e) {
            throw new IOException("Failed to load game from engine.", e);
        }

        EngineEventFrameData frame = null;

        if (frameId == null) {
            frame = gameResp.lastFrame();
        } else {
            frame = getEngineEvent(client, frameId).data();
        }


        assert frame != null;
        return new EngineEventAdapter(gameResp.gameInfo()).toGame(frame);
    }

    private EngineEvent getEngineEvent(
            HttpClient client,
            int frameId
    ) throws IOException {
        log.info("Loading Battlesnake engine events.");

        BattleSnakeEventsListener listener = new BattleSnakeEventsListener();

        URI engineUri = URI.create(battleSnakeEngineUrl.formatted(gameId));
        try {
            client.newWebSocketBuilder().buildAsync(engineUri, listener).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new IOException("Failed to load game from Battlesnake engine.", e);
        }

        List<EngineEvent> frames = listener.join();
        log.info("Loaded {} frames.", frames.size());

        EngineEventFrameData frame = frames
                .stream().filter(f -> f.data().turn() == frameId)
                .findFirst()
                .orElseThrow(() -> new IOException("Failed to load frame %d.".formatted(frameId)))
                .data();

        return frames.get(frameId);
    }

    public static class BattleSnakeEventsListener implements WebSocket.Listener {
        private final List<EngineEvent> frames = new Vector<>();
        public CompletableFuture<List<EngineEvent>> accumulatedFrames = new CompletableFuture<>();
        private StringBuilder sb = new StringBuilder();
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
