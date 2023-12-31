package name.zasenko.battlesnake;

import name.zasenko.battlesnake.coding.SnakeCoding;
import name.zasenko.battlesnake.entities.Game;
import name.zasenko.battlesnake.entities.Point;
import name.zasenko.battlesnake.entities.Snake;

import java.util.Arrays;

public class GameStringifier {
    private final SnakeCoding coding;

    public GameStringifier(SnakeCoding coding) {
        this.coding = coding;
    }

    public String stringify(Game game) {
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

        StringBuilder sb = new StringBuilder();

        // Mirror grid horizontally
        for (int y = height - 1; y >= 0; y--) {
            sb.append(String.join("", grid[y]));
            sb.append("\n");
        }
        return sb.toString();
    }
}
