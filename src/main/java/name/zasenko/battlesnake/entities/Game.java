package name.zasenko.battlesnake.entities;

public record Game(
        // TODO: add "game"
        int turn,
        Board board,
        Snake you
) {
}

