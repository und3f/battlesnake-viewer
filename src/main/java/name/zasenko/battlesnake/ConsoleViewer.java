package name.zasenko.battlesnake;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import name.zasenko.battlesnake.coding.SnakeCoding;
import name.zasenko.battlesnake.coding.SnakeCodingFactory;
import picocli.CommandLine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "battlesnake-viewer",
    description = "Print Battlesnake State File.")
public class ConsoleViewer implements Callable<Integer> {
    @CommandLine.Parameters(index = "0", description = "Game state JSON file")
    private File file;

    @CommandLine.Option(names = {"-f", "--format"}, description = "Format for displaying the state. Possible values: ascii, snek. Default: ascii")
    private final String codingName = "ascii";

    private final static ObjectMapper objectMapper = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    public static void main(String ...args) {
        System.exit(new CommandLine(new ConsoleViewer()).execute(args));
    }

    @Override
    public Integer call() throws Exception {
        String json = Files.readString(file.toPath());
        Game game = objectMapper.readValue(json, Game.class);
        SnakeCoding coding = new SnakeCodingFactory(game).create(codingName);
        System.out.print(new GameStringifier(coding).stringify(game));
        return 0;
    }

    public static void usage() {
        System.out.println("Usage: battlesnake-viewer <filename>");
        System.exit(1);
    }

    private static String loadFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        StringBuilder stringBuilder = new StringBuilder();
        char[] buffer = new char[10];
        while (reader.read(buffer) != -1) {
            stringBuilder.append(new String(buffer));
            buffer = new char[10];
        }
        reader.close();

        return stringBuilder.toString();
    }
}
