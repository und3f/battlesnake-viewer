package name.zasenko.battlesnake.datasource;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import name.zasenko.battlesnake.entities.MoveRequest;
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
    public List<MoveRequest> retrieveFrames() throws IOException {
        if (!isCacheAvailable()) {
            List<MoveRequest> moveRequest = this.dataSource.readFrames();
            storeCache(moveRequest);

            return moveRequest;
        }

        return loadCache();
    }

    private boolean isCacheAvailable() {
        return cacheFile.isFile();
    }

    private void storeCache(List<MoveRequest> moveRequests) throws IOException {
        log.info("Storing cache file.");
        BufferedWriter writer = new BufferedWriter(new FileWriter(cacheFile));

        JsonFactory jsonFactory = new JsonFactory();
        jsonFactory.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET,false);
        final ObjectMapper mapper = new ObjectMapper(jsonFactory);

        for (MoveRequest moveRequest : moveRequests) {
            mapper.writeValue(writer, moveRequest);
            writer.write('\n');
        }
        writer.close();
    }

    private List<MoveRequest> loadCache() throws IOException {
        log.info("Loading cache file.");
        return new JsonFileDataSource(cacheFile).readFrames();
    }

}
