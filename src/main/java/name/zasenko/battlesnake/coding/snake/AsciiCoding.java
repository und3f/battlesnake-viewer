package name.zasenko.battlesnake.coding.snake;

public class AsciiCoding implements SnakeCoding {
    @Override
    public String getEmpty() {
        return "·";
    }

    @Override
    public String getHazard(int stack) {
        return "#";
    }

    @Override
    public String getFood() {
        return "●";
    }

    @Override
    public String getSnakeHead(String id) {
        return "◙";
    }

    @Override
    public String getSnakeBody(String id) {
        return "◼";
    }

    @Override
    public String getSnakeTail(String id) {
        return "▪";
    }
}
