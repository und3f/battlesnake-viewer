package name.zasenko.battlesnake;

import java.util.Arrays;
import java.util.List;

public class Game {
    public Board board;
    public Snake you;

    public static class Board {
        public int height, width;
        public List<Point> food, hazards;
        public List<Snake> snakes;
    }

    public static class Snake {
        public String id;
        public List<Point> body;
        public Point head;
    }

    public static class Point {
        public int x, y;
    }
}
