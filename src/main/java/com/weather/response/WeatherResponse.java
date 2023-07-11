package com.weather.response;

import com.weather.util.Timeline;

public class WeatherResponse extends BasicResponse {
    private final Timeline data;
    public WeatherResponse(String status, Timeline data) {
        super(status);
        this.data = data;
    }

    public Timeline getData() {
        return data;
    }
}
