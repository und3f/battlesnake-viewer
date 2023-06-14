package name.zasenko.battlesnake.coding;

import name.zasenko.battlesnake.Game;

public class SnakeCodingFactory {
    private final Game game;

    public SnakeCodingFactory (Game game) {
        this.game = game;
    }

    public SnakeCoding create(String type) {
        switch (type) {
            case "snek":
                return new SnekSpecCoding(game.you.id);
            case "ascii":
                return new ASCIICoding();
        }
        throw(new RuntimeException("Unknown coding " + type));
    }
}
