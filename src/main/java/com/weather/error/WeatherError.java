package com.weather.error;

import com.weather.response.BasicResponse;

public class WeatherError extends BasicResponse {
    private final WeatherErrorDetails error;

    public WeatherError(String status, WeatherErrorDetails error) {
        super(status);
        this.error = error;
    }

    public WeatherErrorDetails getError() {
        return error;
    }
}
