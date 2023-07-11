package com.weather.util;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Timeline(@JsonProperty("timeline") List<Interval> timeline) {
}
