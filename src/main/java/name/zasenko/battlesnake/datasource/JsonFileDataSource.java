package name.zasenko.battlesnake.datasource;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import name.zasenko.battlesnake.entities.MoveRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class JsonFileDataSource implements DataSource, CacheProvider {

    private static final Logger log = LoggerFactory.getLogger(JsonFileDataSource.class);

    private final static ObjectReader objectReader = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .reader().forType(MoveRequest.class);
    private final File file;

    public JsonFileDataSource(File file) {
        this.file = file;
    }


    @Override
    public List<MoveRequest> readFrames() throws IOException {
        log.info("Reading file \"{}\".", file);

        String json = Files.readString(file.toPath());
        List<MoveRequest> frames = new ArrayList<>();
        var it = objectReader.readValues(json);
        while (it.hasNext()) {
            frames.add((MoveRequest) it.nextValue());
        }

        return frames;
    }

    @Override
    public List<MoveRequest> retrieveFrames() throws IOException {
        return readFrames();
    }

}
