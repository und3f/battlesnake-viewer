package name.zasenko.battlesnake.decorator.asciimatrix;

import name.zasenko.battlesnake.entities.AsciiMatrix;
import name.zasenko.battlesnake.entities.MoveRequest;
import name.zasenko.battlesnake.mapper.asciimatrix.GameToAsciiMatrixMapper;

public class VoronoiDiagramAsciiMatrixDecorator implements GameToAsciiMatrixMapper {

    private final GameToAsciiMatrixMapper parentMapper;

    public VoronoiDiagramAsciiMatrixDecorator(GameToAsciiMatrixMapper parentMapper) {
        this.parentMapper = parentMapper;
    }

    @Override
    public AsciiMatrix buildBoardGrid(MoveRequest moveRequest) {
        return parentMapper.buildBoardGrid(moveRequest);
    }

}
