package name.zasenko.battlesnake.presentation;

import name.zasenko.battlesnake.coding.palette.PaletteCodingImpl;
import name.zasenko.battlesnake.coding.snake.SnakeCoding;
import name.zasenko.battlesnake.entities.AsciiMatrix;
import name.zasenko.battlesnake.entities.MoveRequest;
import name.zasenko.battlesnake.mapper.asciimatrix.GameToColorfulAsciiMatrixMapper;
import name.zasenko.battlesnake.mapper.stringifier.AsciiMatrixStringifier;
import name.zasenko.battlesnake.mapper.asciimatrix.GridDecorator;

public class ConsoleGamePresentation implements GamePresentation {
    private final SnakeCoding coding;

    public ConsoleGamePresentation(SnakeCoding coding) {
        this.coding = coding;
    }

    @Override
    public void execute(MoveRequest moveRequest) {
        AsciiMatrix boardGrid = new GameToColorfulAsciiMatrixMapper(coding, new PaletteCodingImpl()).buildBoardGrid(moveRequest);
        boardGrid = GridDecorator.execute(boardGrid);

        System.out.println();
        System.out.println(new AsciiMatrixStringifier().stringify(boardGrid));
    }

}
