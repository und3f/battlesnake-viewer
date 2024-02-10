package name.zasenko.battlesnake.coding.snake;

import name.zasenko.battlesnake.entities.MoveRequest;

public class SnakeCodingFactory {
    private final MoveRequest moveRequest;

    public SnakeCodingFactory (MoveRequest moveRequest) {
        this.moveRequest = moveRequest;
    }

    public SnakeCoding create(String type) {
        return switch (type) {
            case "snek" -> new SnekSpecCoding(moveRequest.you().id());
            case "ascii" -> new AsciiCoding();
            default -> throw(new RuntimeException("Unknown coding " + type));
        };
    }
}
