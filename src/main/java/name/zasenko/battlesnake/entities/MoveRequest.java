package name.zasenko.battlesnake.entities;

public record MoveRequest(
        // TODO: add "game"
        int turn,
        Board board,
        Snake you
) {
}

