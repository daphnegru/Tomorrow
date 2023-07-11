package com.weather.error;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WeatherErrorDetails(@JsonProperty("code") int code,
                                  @JsonProperty("message") String message) {
}
