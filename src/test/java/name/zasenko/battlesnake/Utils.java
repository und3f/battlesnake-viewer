package name.zasenko.battlesnake;

import name.zasenko.battlesnake.entities.Point;
import name.zasenko.battlesnake.entities.Snake;

import java.util.List;
import java.util.UUID;

public class Utils {

    public static Snake buildSimpleSnake() {
        return buildSnake(
                List.of(
                        new Point(1, 1),
                        new Point(0, 1),
                        new Point(0, 0)
                )
        );
    }

    public static Snake buildSnake(List<Point> body) {
        return new Snake(
                UUID.randomUUID().toString(),
                body,
                body.get(0)
        );
    }

}
