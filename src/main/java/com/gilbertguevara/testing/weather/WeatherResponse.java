package com.gilbertguevara.testing.weather;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WeatherResponse(
    @JsonProperty("coord") Coordinates coordinates,
    @JsonProperty("weather") Weather[] weather,
    @JsonProperty("base") String base,
    @JsonProperty("main") Main main,
    @JsonProperty("visibility") Long visibility,
    @JsonProperty("wind") Wind wind,
    Precipitation rain,
    Precipitation snow,
    @JsonProperty("clouds") Clouds clouds,
    @JsonProperty("dt") Long dt,
    @JsonProperty("sys") Sys sys,
    @JsonProperty("timezone") Integer timezone,
    @JsonProperty("id") Long id,
    @JsonProperty("name") String name,
    @JsonProperty("cod") Integer cod
) {

}

