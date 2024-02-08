package name.zasenko.battlesnake.datasource;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class DataSourceFactory {

    public static CacheProvider create(String dataSource) {
        try {
            URI uri = new URI(dataSource);
            if (uri.getScheme() != null) {
                switch (uri.getScheme()) {
                    case "battlesnake":
                        return new LocalJsonFilesCacheProvider(new HttpsBattleSnakeComDataSource(uri));

                    case "file":
                        System.out.println(uri.getPath());
                        return new JsonFileDataSource(new File(uri.getPath()));
                }
            }
        } catch (URISyntaxException e) {
            // Ignore since we will treat dataSource as filepath
        }

        return new JsonFileDataSource(new File(dataSource));
    }

}
