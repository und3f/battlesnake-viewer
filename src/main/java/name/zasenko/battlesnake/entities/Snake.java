package name.zasenko.battlesnake.entities;

import java.util.List;

public record Snake(
        String id,
        List<Point> body,
        Point head) {
}
