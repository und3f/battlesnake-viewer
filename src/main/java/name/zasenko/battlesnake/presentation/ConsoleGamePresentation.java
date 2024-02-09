package name.zasenko.battlesnake.presentation;

import name.zasenko.battlesnake.mapper.asciimatrix.GameToAsciiMatrixMapper;
import name.zasenko.battlesnake.coding.SnakeCoding;
import name.zasenko.battlesnake.entities.AsciiMatrix;
import name.zasenko.battlesnake.entities.Game;
import name.zasenko.battlesnake.mapper.stringifier.AsciiMatrixStringifier;
import name.zasenko.battlesnake.mapper.asciimatrix.GridDecorator;

public class ConsoleGamePresentation implements GamePresentation {
    private final SnakeCoding coding;

    public ConsoleGamePresentation(SnakeCoding coding) {
        this.coding = coding;
    }

    @Override
    public void execute(Game game) {
        AsciiMatrix boardGrid = new GameToAsciiMatrixMapper(coding).buildBoardGrid(game);
        boardGrid = GridDecorator.execute(boardGrid);

        System.out.println();
        System.out.println(new AsciiMatrixStringifier().stringify(boardGrid));
    }

}
