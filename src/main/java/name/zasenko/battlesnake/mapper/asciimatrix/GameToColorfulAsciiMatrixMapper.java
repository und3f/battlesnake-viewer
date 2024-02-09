package name.zasenko.battlesnake.mapper.asciimatrix;

import name.zasenko.battlesnake.coding.palette.PaletteCoding;
import name.zasenko.battlesnake.coding.snake.SnakeCoding;
import name.zasenko.battlesnake.entities.AsciiMatrix;
import name.zasenko.battlesnake.entities.Game;
import name.zasenko.battlesnake.entities.Point;
import name.zasenko.battlesnake.entities.Snake;

import java.util.Arrays;

public class GameToColorfulAsciiMatrixMapper extends GameToAsciiMatrixMapper {

    private final PaletteCoding palette;

    public GameToColorfulAsciiMatrixMapper(SnakeCoding coding, PaletteCoding palette) {
        super(coding);
        this.palette = palette;
    }

    @Override
    public AsciiMatrix buildBoardGrid(Game game) {
        AsciiMatrix m = super.buildBoardGrid(game);
        boolean[][] marked = new boolean[m.grid().length][m.grid()[0].length];


        int snakeInd = 0;
        for (Snake snake : game.board().snakes()) {
            for (Point p : snake.body()) {
                m.grid()[p.y()][p.x()] = palette.snake(m.grid()[p.y()][p.x()], snakeInd);
                marked[p.y()][p.x()] = true;
            }

            ++snakeInd;
        }

        for (Point p : game.board().food()) {
            m.grid()[p.y()][p.x()] = palette.food(m.grid()[p.y()][p.x()]);
            marked[p.y()][p.x()] = true;
        }

        for (int y = 0; y < marked.length; ++y) {
            for (int x = 0; x < marked[y].length; ++x) {
                if (!marked[y][x]) {
                    m.grid()[y][x] = palette.emptyCell(m.grid()[y][x]);
                }
            }
        }

        return m;
    }

}
