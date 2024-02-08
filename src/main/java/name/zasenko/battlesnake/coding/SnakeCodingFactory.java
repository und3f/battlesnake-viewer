package name.zasenko.battlesnake.coding;

import name.zasenko.battlesnake.entities.Game;

public class SnakeCodingFactory {
    private final Game game;

    public SnakeCodingFactory (Game game) {
        this.game = game;
    }

    public SnakeCoding create(String type) {
        return switch (type) {
            case "snek" -> new SnekSpecCoding(game.you().id());
            case "ascii" -> new AsciiCoding();
            default -> throw(new RuntimeException("Unknown coding " + type));
        };
    }
}
