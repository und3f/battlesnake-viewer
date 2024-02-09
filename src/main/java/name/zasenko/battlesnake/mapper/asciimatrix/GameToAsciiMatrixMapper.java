package name.zasenko.battlesnake.mapper.asciimatrix;

import name.zasenko.battlesnake.coding.snake.SnakeCoding;
import name.zasenko.battlesnake.entities.AsciiMatrix;
import name.zasenko.battlesnake.entities.Game;
import name.zasenko.battlesnake.entities.Point;
import name.zasenko.battlesnake.entities.Snake;

import java.util.Arrays;

public class GameToAsciiMatrixMapper {
    private final SnakeCoding coding;

    public GameToAsciiMatrixMapper(SnakeCoding coding) {
        this.coding = coding;
    }

    public AsciiMatrix buildBoardGrid(Game game) {
        var board = game.board();
        int height = board.height();
        int width = board.width();

        String[][] grid = new String[height][width];
        for (int i = 0; i < height; i++) {
            Arrays.fill(grid[i], coding.getEmpty());
        }

        for (Point p : board.food()) {
            grid[p.y()][p.x()] = coding.getFood();
        }

        for (Point p : board.hazards()) {
            grid[p.y()][p.x()] = coding.getHazard(1);
        }

        for (Snake snake : game.board().snakes()) {
            for (var bit = snake.body().iterator(); bit.hasNext(); ) {
                var p = bit.next();
                grid[p.y()][p.x()] = bit.hasNext() ?
                        coding.getSnakeBody(snake.id()) : coding.getSnakeTail(snake.id());
            }

            var head = snake.head();
            grid[head.y()][head.x()] = coding.getSnakeHead(snake.id());
        }
        return new AsciiMatrix(grid);
    }

}
