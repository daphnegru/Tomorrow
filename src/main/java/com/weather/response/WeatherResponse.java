package com.weather.response;

public class WeatherResponse extends BasicResponse {
    private final String data;
    public WeatherResponse(String status, String data) {
        super(status);
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
