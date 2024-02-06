package name.zasenko.battlesnake.entities.battlesnake;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EngineGameInfo(
        @JsonProperty("ID")
        String id,

        @JsonProperty("Width")
        int width,

        @JsonProperty("Height")
        int height
) {
}
