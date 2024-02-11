package name.zasenko.battlesnake.coding.board;

import name.zasenko.battlesnake.entities.MoveRequest;

public class BoardCodingFactory {
    private final MoveRequest moveRequest;

    public BoardCodingFactory(MoveRequest moveRequest) {
        this.moveRequest = moveRequest;
    }

    public BoardCoding create(String type) {
        return switch (type) {
            case "snek" -> new SnekSpecBoardCoding(moveRequest.you().id());
            case "ascii" -> new AsciiBoardCoding();
            default -> throw(new RuntimeException("Unknown coding " + type));
        };
    }
}
