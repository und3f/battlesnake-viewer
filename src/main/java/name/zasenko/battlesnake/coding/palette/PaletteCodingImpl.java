package name.zasenko.battlesnake.coding.palette;

import static name.zasenko.battlesnake.utils.Ansi.*;

public class PaletteCodingImpl implements PaletteCoding {

    final static String Bg = "";

    final static String Empty = Bg + foregroundTrueColor(0xf1, 0xf1, 0xf1);;

    final static String Food = Bg + foregroundTrueColor(0xf4, 0x3f, 0x5e);

    final static String[] Snakes = {
            foregroundTrueColor(148, 0, 211),
            foregroundTrueColor(0, 255, 0),
            foregroundTrueColor(255, 0, 85),
            foregroundTrueColor(255, 229, 143)
    };

    @Override
    public String emptyCell(String cell) {
        return wrap(cell, Bg);
    }

    @Override
    public String food(String cell) {
        return wrap(cell, Food);
    }

    @Override
    public String hazard(String cell) {
        return wrap(cell, Bg);
    }

    @Override
    public String snake(String cell, int snakeIndex) {
        return wrap(cell, Bg + Snakes[snakeIndex]);
    }

}
