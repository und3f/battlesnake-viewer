package name.zasenko.battlesnake.entities;

import java.util.List;

public record Board(
        int height, int width,
        List<Point> food, List<Point> hazards,
        List<Snake> snakes) {
}
