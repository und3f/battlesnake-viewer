package name.zasenko.battlesnake.datasource;

import name.zasenko.battlesnake.entities.MoveRequest;

import java.io.IOException;
import java.util.List;

public interface CacheProvider {

    public List<MoveRequest> retrieveFrames() throws IOException;

}
