package name.zasenko.battlesnake.entities.battlesnake;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EngineEvent(
        @JsonProperty("Type") String type,
        @JsonProperty("Data") EngineEventFrameData data)
{
}
