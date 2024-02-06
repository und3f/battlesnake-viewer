package name.zasenko.battlesnake.datasource;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import name.zasenko.battlesnake.entities.Game;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class JsonFileDataSource implements DataSource {
    private final static ObjectMapper objectMapper = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    private final File file;

    public JsonFileDataSource(File file) {
        this.file = file;
    }


    @Override
    public Game readState() throws IOException {
        String json = Files.readString(file.toPath());
        return objectMapper.readValue(json, Game.class);
    }
}
