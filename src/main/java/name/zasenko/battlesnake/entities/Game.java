package name.zasenko.battlesnake.entities;

public record Game(
        int turn,
        Board board,
        Snake you
) {
}

