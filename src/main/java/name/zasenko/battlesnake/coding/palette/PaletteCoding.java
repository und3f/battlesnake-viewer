package name.zasenko.battlesnake.coding.palette;

public interface PaletteCoding {

    public String emptyCell(String cell);
    public String food(String cell);
    public String hazard(String cell);
    public String snake(String cell, int snakeIndex);

}
