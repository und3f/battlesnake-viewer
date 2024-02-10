package name.zasenko.battlesnake.mapper.game;

import name.zasenko.battlesnake.entities.Board;
import name.zasenko.battlesnake.entities.MoveRequest;
import name.zasenko.battlesnake.entities.Point;
import name.zasenko.battlesnake.entities.Snake;
import name.zasenko.battlesnake.entities.engine.EngineEventFrameData;
import name.zasenko.battlesnake.entities.engine.EngineEventPoint;
import name.zasenko.battlesnake.entities.engine.EngineEventSnake;
import name.zasenko.battlesnake.entities.engine.EngineGameInfo;

import java.util.List;

public class EngineEventToGameMapper {
    private final EngineGameInfo gameInfo;

    public EngineEventToGameMapper(EngineGameInfo gameInfo) {
        this.gameInfo = gameInfo;
    }

    public MoveRequest buildGame(EngineEventFrameData frame) {
        List<Point> food = adaptListPoints(frame.food());
        List<Point> hazards = adaptListPoints(frame.hazards());
        List<Snake> snakes = frame.snakes().stream()
                .filter(s -> s.death() == null)
                .map(EngineEventToGameMapper::adaptSnake)
                .toList();

        return new MoveRequest(
            frame.turn(),
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
