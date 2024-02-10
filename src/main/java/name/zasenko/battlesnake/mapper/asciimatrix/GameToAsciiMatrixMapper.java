package name.zasenko.battlesnake.mapper.asciimatrix;

import name.zasenko.battlesnake.entities.AsciiMatrix;
import name.zasenko.battlesnake.entities.MoveRequest;

public interface GameToAsciiMatrixMapper {
    public AsciiMatrix buildBoardGrid(MoveRequest moveRequest);

}
