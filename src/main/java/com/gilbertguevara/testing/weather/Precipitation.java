package com.gilbertguevara.testing.weather;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Precipitation(@JsonProperty("1h") Double oneHour, @JsonProperty("3h") Double threeHours) {

}
