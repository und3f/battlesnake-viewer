package name.zasenko.battlesnake.entities.battlesnake;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record EngineEventSnake (
    @JsonProperty("ID")
    String id,

    @JsonProperty("Body")
    List<EngineEventPoint> body,

    @JsonProperty("Death")
    Object death
) {
}
