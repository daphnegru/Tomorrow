package com.weather.response;

public class BasicResponse {
    private final String status;

    public BasicResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
