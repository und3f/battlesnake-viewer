package name.zasenko.battlesnake.mapper;

import name.zasenko.battlesnake.entities.Board;
import name.zasenko.battlesnake.entities.Point;
import name.zasenko.battlesnake.entities.Snake;
import name.zasenko.battlesnake.entities.battlesnake.*;
import name.zasenko.battlesnake.entities.Game;

import java.util.List;

public class EngineEventAdapter {
    private final EngineGameInfo gameInfo;

    public EngineEventAdapter(EngineGameInfo gameInfo) {
        this.gameInfo = gameInfo;
    }

    public Game toGame(EngineEventFrameData frame) {
        List<Point> food = adaptListPoints(frame.food());
        List<Point> hazards = adaptListPoints(frame.hazards());
        List<Snake> snakes = frame.snakes().stream()
                .filter(s -> s.death() == null)
                .map(EngineEventAdapter::adaptSnake)
                .toList();
        assert snakes.size() > 0;

        return new Game(
            new Board(
                gameInfo.height(),
                gameInfo.width(),
                food,
                hazards,
                snakes
            ), snakes.get(0)
        );
    }

    private static List<Point> adaptListPoints(List<EngineEventPoint> points) {
       return points.stream().map(p -> new Point(p.x(), p.y())).toList();
    }

    private static Snake adaptSnake(EngineEventSnake snake) {
        List<Point> body = adaptListPoints(snake.body());

        return new Snake(
                snake.id(),
                body,
                body.get(0)
        );
    }
}
