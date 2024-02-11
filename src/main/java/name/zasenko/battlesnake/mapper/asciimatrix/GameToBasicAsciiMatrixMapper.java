package name.zasenko.battlesnake.mapper.asciimatrix;

import name.zasenko.battlesnake.coding.board.BoardCoding;
import name.zasenko.battlesnake.entities.AsciiMatrix;
import name.zasenko.battlesnake.entities.MoveRequest;
import name.zasenko.battlesnake.entities.Point;
import name.zasenko.battlesnake.entities.Snake;

import java.util.Arrays;

public class GameToBasicAsciiMatrixMapper implements GameToAsciiMatrixMapper {
    private final BoardCoding coding;

    public GameToBasicAsciiMatrixMapper(BoardCoding coding) {
        this.coding = coding;
    }

    @Override
    public AsciiMatrix buildBoardGrid(MoveRequest moveRequest) {
        var board = moveRequest.board();
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

        for (Snake snake : moveRequest.board().snakes()) {
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
