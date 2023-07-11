package com.weather.error;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record WeatherErrorDetails(@JsonProperty("code") int code,
                                  @JsonProperty("message") String message) {
}
