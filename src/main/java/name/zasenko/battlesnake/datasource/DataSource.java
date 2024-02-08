package name.zasenko.battlesnake.datasource;

import name.zasenko.battlesnake.entities.Game;

import java.io.IOException;
import java.util.List;

public interface DataSource {

    List<Game> readFrames() throws IOException;

}
