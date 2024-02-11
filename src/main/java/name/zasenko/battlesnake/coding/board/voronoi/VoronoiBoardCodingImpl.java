package name.zasenko.battlesnake.coding.board.voronoi;

import name.zasenko.battlesnake.entities.Snake;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoronoiBoardCodingImpl implements VoronoiBoardCoding {
    Map<String, Integer> snakeToIndex = new HashMap<String, Integer>();
    private static final int baseCodePoint = 0x2460;

    @Override
    public String getControlledAreaSym(Snake snake) {
        int snakeIndex = getOrCreateSnakeIndex(snake);
        return new String(Character.toChars(baseCodePoint + snakeIndex - 1));
    }

    @Override
    public String getOverlapAreaSym(List<Snake> snakes) {
        return "*";
    }

    private int getOrCreateSnakeIndex(Snake snake) {
        String snakeId = snake.id();

        if (snakeToIndex.containsKey(snakeId)) {
            return snakeToIndex.get(snakeId);
        }

        int nextIndex = snakeToIndex.size() + 1;
        snakeToIndex.put(snakeId, nextIndex);

        return nextIndex;
    }
}
