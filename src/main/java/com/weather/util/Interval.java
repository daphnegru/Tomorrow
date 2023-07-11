package com.weather.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record Interval(@JsonProperty("startTime") String startTime,
                       @JsonProperty("endTime") String endTime,
                       @JsonProperty("condition_met") boolean condition_met) {

}
