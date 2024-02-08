package name.zasenko.battlesnake.entities.engine;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EngineEventPoint(
        @JsonProperty("X")
        int x,

        @JsonProperty("Y")
        int y
) {
}
