package name.zasenko.battlesnake.datasource;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import name.zasenko.battlesnake.entities.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class JsonFileDataSource implements DataSource {

    private static final Logger log = LoggerFactory.getLogger(JsonFileDataSource.class);

    private final static ObjectMapper objectMapper = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    private final File file;

    public JsonFileDataSource(File file) {
        this.file = file;
    }


    @Override
    public Game readState() throws IOException {
        log.info("Reading file \"{}\".", file);

        String json = Files.readString(file.toPath());
        return objectMapper.readValue(json, Game.class);
    }

}
