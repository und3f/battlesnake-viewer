package name.zasenko.battlesnake.entities.engine;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EngineGameResponse(
        @JsonProperty("Game")
        EngineGameInfo gameInfo,

        @JsonProperty("LastFrame")
        EngineEventFrameData lastFrame
) {
}
