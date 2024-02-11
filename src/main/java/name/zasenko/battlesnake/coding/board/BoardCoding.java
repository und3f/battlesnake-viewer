package name.zasenko.battlesnake.coding.board;

public interface BoardCoding {
    String getEmpty();
    String getHazard(int stack);
    String getFood();
    String getSnakeHead(String id);
    String getSnakeBody(String id);
    String getSnakeTail(String id);
}
