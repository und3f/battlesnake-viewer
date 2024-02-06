package name.zasenko.battlesnake.entities.battlesnake;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EngineEventPoint(
        @JsonProperty("Y")
        int x,

        @JsonProperty("X")
        int y
) {
}
