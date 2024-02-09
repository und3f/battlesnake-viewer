package name.zasenko.battlesnake;

import name.zasenko.battlesnake.coding.snake.SnakeCoding;
import name.zasenko.battlesnake.coding.snake.SnakeCodingFactory;
import name.zasenko.battlesnake.datasource.DataSourceFactory;
import name.zasenko.battlesnake.entities.Game;
import name.zasenko.battlesnake.presentation.ConsoleGamePresentation;
import name.zasenko.battlesnake.presentation.GamePresentation;
import picocli.CommandLine;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "battlesnake-viewer",
    description = "Display Battlesnake game state.")
public class ConsoleViewerCmd implements Callable<Integer> {
    @CommandLine.Parameters(
            index = "0",
            paramLabel = "<uri|file>",
            description = """
                    Uri of the game state JSON file or Battlesnake game.
                    e.g.: battlesnake://367d5926-7d06-42be-8af1-2781e0eade93"""
    )
    private String uri;

    @CommandLine.Option(
            names = {"-f", "--format"},
            description = """
                    Format for displaying the state. Possible values: ascii, snek. Default: ascii"""
    )
    private String codingName = "ascii";

    @CommandLine.Option(
            names = {"-t", "--turn"},
            description = """
                    Display specific turn. Defaults to the last turn"""
    )
    private Integer turn;

    public static void main(String ...args) {
        System.exit(new CommandLine(new ConsoleViewerCmd()).execute(args));
    }

    @Override
    public Integer call() throws IOException {
        Game game = getFrame();
        SnakeCoding coding = new SnakeCodingFactory(game).create(codingName);
        createPresentation(coding).execute(game);

        return 0;
    }

    public Game getFrame() throws IOException {
        List<Game> frames = DataSourceFactory.create(uri).retrieveFrames();
        if (turn == null) {
            return frames.get(frames.size() - 1);
        }

        return frames.stream().filter(game -> game.turn() == turn).findFirst().orElseThrow(
                () -> new IllegalArgumentException("Turn %d not found.".formatted(turn))
        );
    }

    public GamePresentation createPresentation(SnakeCoding coding) {
        return new ConsoleGamePresentation(coding);
    }

}
