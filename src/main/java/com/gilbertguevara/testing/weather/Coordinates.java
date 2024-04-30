package com.gilbertguevara.testing.weather;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Coordinates(
        @JsonProperty("lat") Double latitude,
        @JsonProperty("lon") Double longitude
) {
}
