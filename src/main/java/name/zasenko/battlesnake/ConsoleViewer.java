package name.zasenko.battlesnake;

import name.zasenko.battlesnake.coding.SnakeCoding;
import name.zasenko.battlesnake.coding.SnakeCodingFactory;
import name.zasenko.battlesnake.datasource.DataSourceFactory;
import name.zasenko.battlesnake.entities.Game;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "battlesnake-viewer",
    description = "Display Battlesnake game state.")
public class ConsoleViewer implements Callable<Integer> {
    @CommandLine.Parameters(
            index = "0",
            description = """
                    Uri of the game state JSON file or Battlesnake game.
                    Example: battlesnake://367d5926-7d06-42be-8af1-2781e0eade93"""
    )
    private String uri;

    @CommandLine.Option(
            names = {"-f", "--format"},
            description = """
                    Format for displaying the state. Possible values: ascii, snek. Default: ascii"""
    )
    private String codingName = "ascii";

    public static void main(String ...args) {
        System.exit(new CommandLine(new ConsoleViewer()).execute(args));
    }

    @Override
    public Integer call() throws Exception {
        Game game = DataSourceFactory.create(uri).readState();
        SnakeCoding coding = new SnakeCodingFactory(game).create(codingName);
        System.out.print(new GameStringifier(coding).stringify(game));
        return 0;
    }

    public static void usage() {
        System.out.println("Usage: battlesnake-viewer <filename>");
        System.exit(1);
    }

}
