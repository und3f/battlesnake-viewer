package name.zasenko.battlesnake.presentation;

import name.zasenko.battlesnake.coding.SnakeCoding;
import name.zasenko.battlesnake.entities.Game;
import name.zasenko.battlesnake.entities.Point;
import name.zasenko.battlesnake.entities.Snake;

import java.util.Arrays;

public class ConsoleGamePresentation implements GamePresentation {
    private final SnakeCoding coding;

    public ConsoleGamePresentation(SnakeCoding coding) {
        this.coding = coding;
    }

    @Override
    public void execute(Game game) {
        BoardGrid boardGrid = buildBoardGrid(game);

        System.out.println(boardGrid);
    }
    protected record BoardGrid(String[][] grid) {

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();

            // Display in reverse order to convert battlesnake coordinates
            for (int y = grid.length - 1; y >= 0; y--) {
                sb.append(String.join("", grid()[y]));
                sb.append("\n");
            }
            return sb.toString();
        }

    }

    protected BoardGrid buildBoardGrid(Game game) {
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
        return new BoardGrid(grid);
    }

}
