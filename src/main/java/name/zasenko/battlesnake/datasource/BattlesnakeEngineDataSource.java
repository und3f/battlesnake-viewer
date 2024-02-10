package name.zasenko.battlesnake.datasource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import name.zasenko.battlesnake.entities.MoveRequest;
import name.zasenko.battlesnake.entities.engine.EngineEvent;
import name.zasenko.battlesnake.entities.engine.EngineGameResponse;
import name.zasenko.battlesnake.mapper.game.EngineEventToGameMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.WebSocket;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

public class BattlesnakeEngineDataSource implements DataSource, CachableDataSource {
    private static final Logger log = LoggerFactory.getLogger(BattlesnakeEngineDataSource.class);
    private static final ObjectMapper objectMapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    private static final String gameInfoEndpointPath = "/games/%s";
    private static final String eventsEndpointPath = "/games/%s/events";
    private static final String battlesnakeComEngineUrl = "https://engine.battlesnake.com";
    public static final String battlesnakePlayHost = "play.battlesnake.com";

    private final String gameId;
    private final URI engineUri;

    public BattlesnakeEngineDataSource(URI engineUri, String gameId) {
        this.engineUri = engineUri;
        this.gameId = gameId;
    }

    public BattlesnakeEngineDataSource(String gameId) {
        this.gameId = gameId;

        try {
            this.engineUri = new URI(battlesnakeComEngineUrl);
        } catch (URISyntaxException e) {
            throw new RuntimeException("Unexpected error.", e);
        }
    }

    @Override
    public List<MoveRequest> readFrames() throws IOException {
        log.info("Loading game {}.", gameId);

        HttpClient client = HttpClient.newHttpClient();

        EngineGameResponse gameResp = getEngineGame(client);

        List<EngineEvent> frames = getEngineEvents(client);
        if (frames.size() != gameResp.lastFrame().turn() + 1) {
            throw new IOException("Failed to load all game frames.");
        }
        // Remove last element
        frames.remove(frames.size()-1);

        EngineEventToGameMapper engineEventMapper = new EngineEventToGameMapper(gameResp.gameInfo());
        return frames.stream().map(f -> engineEventMapper.buildGame(f.data())).toList();
    }

    private EngineGameResponse getEngineGame(HttpClient client) throws IOException {
        EngineGameResponse gameResp;
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(getEngineInfoEndpointUri())
                    .build();
            log.info(getEngineInfoEndpointUri().toString());
            String response = client.send(request, HttpResponse.BodyHandlers.ofString()).body();

            gameResp = objectMapper.readValue(response, EngineGameResponse.class);
        } catch (InterruptedException e) {
            throw new IOException("Failed to load game from engine.", e);
        }
        return gameResp;
    }

    private List<EngineEvent> getEngineEvents(HttpClient client) throws IOException {
        log.info("Loading Battlesnake engine events.");

        BattleSnakeEventsListener listener = new BattleSnakeEventsListener();

        try {
            client.newWebSocketBuilder().buildAsync(getEngineEventsEndpointUri(), listener).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new IOException("Failed to load game from Battlesnake engine.", e);
        }

        List<EngineEvent> frames = listener.join();
        log.info("Received frames count: {}.", frames.size());

        return frames;
    }

    private URI getEngineInfoEndpointUri() {
        try {
            return new URI(
                    engineUri.getScheme(),
                    engineUri.getUserInfo(),
                    engineUri.getHost(),
                    engineUri.getPort(),
                    gameInfoEndpointPath.formatted(gameId),
                    null,
                    null
            );
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Failed to create engine game info endpoint url.", e);
        }
    }

    private URI getEngineEventsEndpointUri() {
        try {
            return new URI(
                    engineUri.getScheme().equals("https") ? "wss" : "ws",
                    engineUri.getUserInfo(),
                    engineUri.getHost(),
                    engineUri.getPort(),
                    eventsEndpointPath.formatted(gameId),
                    null,
                    null
            );
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Failed to construct engine events endpoint uri.", e);
        }
    }


    @Override
    public String cachePrefix() {
        return engineUri.getHost();
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
