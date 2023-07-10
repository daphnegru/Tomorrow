package com.tomorrow.util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

public class WeatherApiBuilder {

    public String getApi(String location) throws IOException, InterruptedException {
        String API_URL = "https://api.tomorrow.io/v4/timelines";
        String API_KEY = "pXKzkPcZeI7qqUnNSFQP7S9if4glxtwo";
        String url = API_URL +
                "?location=" +
                location +
                "&fields=temperature,humidity,windSpeed,rainIntensity" +
                "&timesteps=1h" +
                "&units=metric" +
                "&endTime=" +
                LocalDateTime.now().plusHours(72) +
                "&apikey=" + API_KEY;

        URI uri = URI.create(url);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new IOException("API fails to find any data from Tomorrow.io/timeline API");
        }
    }
}
