package com.weather.error;

public record WeatherError(String status, WeatherErrorDetails details) {
}
