package name.zasenko.battlesnake.datasource;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class DataSourceFactory {

    public static DataSource create(String dataSource) {
        try {
            URI uri = new URI(dataSource);
            if (uri.getScheme() != null) {
                switch (uri.getScheme()) {
                    case "battlesnake":
                        return new BattleSnakeDataSource(uri);

                    case "file":
                        System.out.println(uri.getPath());
                        return new JsonFileDataSource(new File(uri.getPath()));
                }
            }
        } catch (URISyntaxException e) {
        }

        System.out.println(dataSource);
        return new JsonFileDataSource(new File(dataSource));
    }

}