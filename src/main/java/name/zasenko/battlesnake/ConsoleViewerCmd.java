package name.zasenko.battlesnake;

import name.zasenko.battlesnake.coding.board.BoardCoding;
import name.zasenko.battlesnake.coding.board.BoardCodingFactory;
import name.zasenko.battlesnake.datasource.DataSourceFactory;
import name.zasenko.battlesnake.entities.MoveRequest;
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
                    Uri of the Battlesnake engine game or JSON file.
                    The JSON file should be in move request format. Examples:
                      https://play.battlesnake.com/game/d3537a9c-8f1d-41f3-9596-0f4a38e00e29
                      battlesnake://d3537a9c-8f1d-41f3-9596-0f4a38e00e29 # Alias to previous
                      path/to/local-file.json"""
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

    @CommandLine.Option(
            names = {"--color"},
            description = """
                    Enable colors. Default true"""
    )
    private Boolean colorEnabled = true;

    public static void main(String... args) {
        System.exit(new CommandLine(new ConsoleViewerCmd())
                .setUsageHelpAutoWidth(true)
                .execute(args));
    }

    @Override
    public Integer call() throws IOException {
        MoveRequest moveRequest = getFrame();
        BoardCoding coding = new BoardCodingFactory(moveRequest).create(codingName);
        createPresentation(coding).execute(moveRequest);

        return 0;
    }

    public MoveRequest getFrame() throws IOException {
        List<MoveRequest> frames = DataSourceFactory.create(uri).retrieveFrames();
        if (turn == null) {
            return frames.get(frames.size() - 1);
        }

        return frames.stream().filter(game -> game.turn() == turn).findFirst().orElseThrow(
                () -> new IllegalArgumentException("Turn %d not found.".formatted(turn))
        );
    }

    public GamePresentation createPresentation(BoardCoding coding) {
        return new ConsoleGamePresentation(coding, colorEnabled);
    }

}
