package name.zasenko.battlesnake.coding.palette;

import name.zasenko.battlesnake.utils.AnsiColorConstants;
import static name.zasenko.battlesnake.utils.AnsiColorConstants.wrap;

public class PaletteCodingImpl implements PaletteCoding {

    final static String Bg = AnsiColorConstants.WHITE;
    final static String Food = AnsiColorConstants.PURPLE_BOLD;
    final static String[] Snakes = {
            AnsiColorConstants.CYAN,
            AnsiColorConstants.GREEN,
            AnsiColorConstants.RED,
            AnsiColorConstants.YELLOW
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
        return wrap(cell, Snakes[snakeIndex]);
    }

}
