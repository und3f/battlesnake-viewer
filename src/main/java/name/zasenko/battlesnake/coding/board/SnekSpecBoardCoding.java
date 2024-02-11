package name.zasenko.battlesnake.coding.board;

import java.util.HashMap;
import java.util.Map;

public class SnekSpecBoardCoding implements BoardCoding {
    private char nextSnakeChar = 's';
    private final Map<String, SnakeCharCodes> snakes = new HashMap<>();

    private static class SnakeCharCodes {
        String head;
        String body;
    }

    public SnekSpecBoardCoding(String me) {
        getOrInitSnakeCharCoding(me);
    }

    public SnakeCharCodes getOrInitSnakeCharCoding(String id) {
        var coding = snakes.get(id);
        if (coding != null) {
            return coding;
        }
        coding = new SnakeCharCodes();

        if (nextSnakeChar >= 'z') {
            nextSnakeChar = 'a';
        }
        coding.head = Character.toString(Character.toUpperCase(nextSnakeChar++));
        coding.body = Character.toString(nextSnakeChar++);
        snakes.put(id, coding);
        return coding;
    }

    @Override
    public String getEmpty() {
        return "-";
    }

    @Override
    public String getHazard(int stack) {
        return "/";
    }

    @Override
    public String getFood() {
        return "0";
    }

    @Override
    public String getSnakeHead(String id) {
        return getOrInitSnakeCharCoding(id).head;
    }

    @Override
    public String getSnakeBody(String id) {
        return getOrInitSnakeCharCoding(id).body;
    }

    @Override
    public String getSnakeTail(String id) {
        return getOrInitSnakeCharCoding(id).body.toUpperCase();
    }
}
