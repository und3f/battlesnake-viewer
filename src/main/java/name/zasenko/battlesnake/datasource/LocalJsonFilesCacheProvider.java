package name.zasenko.battlesnake.datasource;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import name.zasenko.battlesnake.entities.Game;
import name.zasenko.battlesnake.utils.WorkingDirectoryManger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static name.zasenko.battlesnake.ConsoleViewerConfiguration.APPLICATION_NAME;

public class LocalJsonFilesCacheProvider implements CacheProvider {
    private static final Logger log = LoggerFactory.getLogger(LocalJsonFilesCacheProvider.class);
    private final CachableDataSource dataSource;
    private final File cacheFile;

    public LocalJsonFilesCacheProvider(CachableDataSource dataSource) {
        this.dataSource = dataSource;
        Path workDir = WorkingDirectoryManger.getWorkingDirectory(
                Paths.get(APPLICATION_NAME, dataSource.cachePrefix()).toString()
        ).toPath();

        this.cacheFile = new File(Paths.get(workDir.toString(), dataSource.itemId() + ".json").toUri());
    }


    @Override
    public List<Game> retrieveFrames() throws IOException {
        if (!isCacheAvailable()) {
            List<Game> game = this.dataSource.readFrames();
            storeCache(game);

            return game;
        }

        return loadCache();
    }

    private boolean isCacheAvailable() {
        return cacheFile.isFile();
    }

    private void storeCache(List<Game> games) throws IOException {
        log.info("Storing cache file.");
        BufferedWriter writer = new BufferedWriter(new FileWriter(cacheFile));

        JsonFactory jsonFactory = new JsonFactory();
        jsonFactory.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET,false);
        final ObjectMapper mapper = new ObjectMapper(jsonFactory);

        for (Game game : games) {
            mapper.writeValue(writer, game);
            writer.write('\n');
        }
        writer.close();
    }

    private List<Game> loadCache() throws IOException {
        log.info("Loading cache file.");
        return new JsonFileDataSource(cacheFile).readFrames();
    }

}
