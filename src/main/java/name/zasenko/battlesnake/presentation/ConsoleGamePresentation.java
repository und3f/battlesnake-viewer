package name.zasenko.battlesnake.presentation;

import name.zasenko.battlesnake.coding.palette.PaletteCodingImpl;
import name.zasenko.battlesnake.coding.board.BoardCoding;
import name.zasenko.battlesnake.entities.AsciiMatrix;
import name.zasenko.battlesnake.entities.MoveRequest;
import name.zasenko.battlesnake.mapper.asciimatrix.GameToAsciiMatrixMapper;
import name.zasenko.battlesnake.mapper.asciimatrix.GameToBasicAsciiMatrixMapper;
import name.zasenko.battlesnake.mapper.asciimatrix.GameToColorfulAsciiMatrixMapper;
import name.zasenko.battlesnake.mapper.stringifier.AsciiMatrixStringifier;
import name.zasenko.battlesnake.decorator.asciimatrix.GridAsciiMatrixDecorator;

public class ConsoleGamePresentation implements GamePresentation {
    private final GameToAsciiMatrixMapper mapper;

    public ConsoleGamePresentation(BoardCoding coding, Boolean color) {
        if (color) {
            mapper = new GameToColorfulAsciiMatrixMapper(coding, new PaletteCodingImpl());
        } else {
            mapper = new GameToBasicAsciiMatrixMapper(coding);
        }
    }

    public ConsoleGamePresentation(BoardCoding coding) {
        this(coding, false);
    }

    @Override
    public void execute(MoveRequest moveRequest) {

        AsciiMatrix boardGrid = mapper.buildBoardGrid(moveRequest);
        boardGrid = GridAsciiMatrixDecorator.execute(boardGrid);

        System.out.println();
        System.out.println(new AsciiMatrixStringifier().stringify(boardGrid));
    }

}
