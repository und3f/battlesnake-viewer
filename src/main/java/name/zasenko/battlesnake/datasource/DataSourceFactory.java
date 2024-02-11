package name.zasenko.battlesnake.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;

public class DataSourceFactory {
    final static Logger log = LoggerFactory.getLogger(DataSourceFactory.class);

    public static CacheProvider create(String dataSource) {
        try {
            URI uri = new URI(dataSource);
            if (uri.getScheme() != null) {
                switch (uri.getScheme()) {
                    case "battlesnake" -> {
                        return new LocalJsonFilesCacheProvider(new BattlesnakeEngineDataSource(parseBattlesnakeUriGameId(uri)));
                    }
                    case "https", "http" -> {
                        String gameId = parseBattlesnakeEngineGameId(uri);
                        if (uri.getHost().equals(BattlesnakeEngineDataSource.battlesnakePlayHost)) {
                            return new LocalJsonFilesCacheProvider(new BattlesnakeEngineDataSource(gameId));
                        }
                        URI engineUri = parseBattlesnakeEngineUri(uri);
                        return new LocalJsonFilesCacheProvider(new BattlesnakeEngineDataSource(engineUri, gameId));
                    }
                    case "file" -> {
                        return new JsonFileDataSource(new File(uri.getPath()));
                    }
                    default -> throw new IllegalArgumentException("Usupported schema: %s".formatted(uri.getScheme()));
                }
            }
        } catch (URISyntaxException e) {
            // Ignore since we will treat dataSource as filepath
        }

        return new JsonFileDataSource(new File(dataSource));
    }

    public static String parseBattlesnakeUriGameId(URI uri) {
        String gameId = Optional
                .ofNullable(uri.getHost())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Battlesnake uri."));

        String path = uri.getPath();
        if (path != null && !path.isEmpty()) {
            path = path.substring(1);
        }

        return gameId;
    }

    public static URI parseBattlesnakeEngineUri(URI uri) {
        try {
            return new URL(uri.getScheme(), uri.getHost(), uri.getPort(), "").toURI();
        } catch (MalformedURLException | URISyntaxException e){
            throw new IllegalArgumentException("Invalid Battlesnake engine url.", e);
        }
    }

    public static String parseBattlesnakeEngineGameId(URI uri) {
        try {
            return uri.getPath().split("/")[2];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Unable to find game id in url.");
        }
    }

}
