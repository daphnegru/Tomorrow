package com.weather.util;

import com.fasterxml.jackson.annotation.JsonProperty;
public record Interval(@JsonProperty("startTime") String startTime,
                       @JsonProperty("endTime") String endTime,
                       @JsonProperty("condition_met") boolean condition_met) {

}
