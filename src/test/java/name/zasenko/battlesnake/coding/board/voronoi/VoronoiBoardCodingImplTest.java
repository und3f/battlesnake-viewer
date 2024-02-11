package name.zasenko.battlesnake.coding.board.voronoi;

import name.zasenko.battlesnake.entities.Snake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static name.zasenko.battlesnake.Utils.buildSimpleSnake;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class VoronoiBoardCodingImplTest {
    VoronoiBoardCodingImpl coding;
    @BeforeEach
    void setUp() {
        coding = new VoronoiBoardCodingImpl();
    }

    @Test
    void test_getSnakeSym_isCorrect() {
        Snake snake = buildSimpleSnake();
        String sym = coding.getControlledAreaSym(snake);

        assertEquals("①", sym);
    }

    @Test
    public void test_getOverlapAreaSym_isCorrect() {
        Snake snake1 = buildSimpleSnake();
        Snake snake2 = buildSimpleSnake();
        String sym = coding.getOverlapAreaSym(List.of(snake1, snake2));

        assertEquals("*", sym);
    }

    @Test
    void test_getSameSnakeSym_isSame() {
        Snake snake = buildSimpleSnake();

        String sym1 = coding.getControlledAreaSym(snake);
        String sym2 = coding.getControlledAreaSym(snake);

        assertEquals(sym1, sym2);
    }

    @Test
    void test_getDifferentSnakeSym_isDifferent() {
        Snake snake1 = buildSimpleSnake();
        Snake snake2 = buildSimpleSnake();

        String sym1 = coding.getControlledAreaSym(snake1);
        String sym2 = coding.getControlledAreaSym(snake2);

        assertNotEquals(sym1, sym2);
    }
}
