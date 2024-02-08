package name.zasenko.battlesnake.datasource;

import name.zasenko.battlesnake.entities.Game;

import java.io.IOException;
import java.util.List;

public interface CacheProvider {

    public List<Game> retrieveFrames() throws IOException;

}
