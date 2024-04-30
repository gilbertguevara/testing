package com.gilbertguevara.testing.weather;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Main(
        Double temp,
        @JsonProperty("feels_like") Double feelsLike,
        Integer pressure,
        Integer humidity,
        @JsonProperty("temp_min") Double tempMin,
        @JsonProperty("temp_max") Double tempMax,
        @JsonProperty("sea_level") Double seaLevel,
        @JsonProperty("grnd_level") Double grndLevel
        ) {
}
