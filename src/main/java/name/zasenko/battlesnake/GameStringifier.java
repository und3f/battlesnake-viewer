package name.zasenko.battlesnake;

import name.zasenko.battlesnake.coding.SnakeCoding;

import java.util.Arrays;

public class GameStringifier {
    private final SnakeCoding coding;

    public GameStringifier(SnakeCoding coding) {
        this.coding = coding;
    }

    public String stringify(Game game) {
        var board = game.board;
        int height = board.height;
        int width = board.width;

        String[][] grid = new String[height][width];
        for (int i = 0; i < height; i++) {
            Arrays.fill(grid[i], coding.getEmpty());
        }

        for (var it = board.food.iterator(); it.hasNext(); ) {
            var p = it.next();
            grid[p.y][p.x] = coding.getFood();
        }

        for (var it = board.hazards.iterator(); it.hasNext(); ) {
            var p = it.next();
            grid[p.y][p.x] = coding.getHazard(1);
        }

        for (var it = game.board.snakes.iterator(); it.hasNext(); ) {
            var snake = it.next();

            for (var bit = snake.body.iterator(); bit.hasNext(); ) {
                var p = bit.next();
                grid[p.y][p.x] = bit.hasNext() ?
                        coding.getSnakeBody(snake.id) : coding.getSnakeTail(snake.id);
            }

            var head = snake.head;
            grid[head.y][head.x] = coding.getSnakeHead(snake.id);
        }

        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < height; y++) {
            sb.append(String.join("", grid[y]));
            sb.append("\n");
        }
        return sb.toString();
    }
}
