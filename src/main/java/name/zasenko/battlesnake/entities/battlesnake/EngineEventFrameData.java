package name.zasenko.battlesnake.entities.battlesnake;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record EngineEventFrameData(
        @JsonProperty("Turn")
        int turn,

        @JsonProperty("Snakes")
        List<EngineEventSnake> snakes,

        @JsonProperty("Food")
        List<EngineEventPoint> food,

        @JsonProperty("Hazards")
        List<EngineEventPoint> hazards

) {
}
