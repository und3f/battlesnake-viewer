package name.zasenko.battlesnake.datasource;

import name.zasenko.battlesnake.entities.Game;

import java.io.IOException;

public interface DataSource {

    Game readState() throws IOException;

}
