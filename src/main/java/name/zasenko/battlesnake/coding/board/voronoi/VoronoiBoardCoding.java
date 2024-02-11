package name.zasenko.battlesnake.coding.board.voronoi;

import name.zasenko.battlesnake.entities.Snake;

import java.util.List;

public interface VoronoiBoardCoding {

    String getControlledAreaSym(Snake snake);
    String getOverlapAreaSym(List<Snake> snakes);

}
