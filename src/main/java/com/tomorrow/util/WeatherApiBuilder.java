package com.tomorrow.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;

public class WeatherApiBuilder {

    public String getApi(String location) throws IOException {
        StringBuilder api = new StringBuilder();
        try {
            String API_URL = "https://api.tomorrow.io/v4/timelines";
            String API_KEY = "pXKzkPcZeI7qqUnNSFQP7S9if4glxtwo";
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(API_URL)
                    .queryParam("location", location)
                    .queryParam("fields", "temperature,humidity,windSpeed,rainIntensity")
                    .queryParam("timesteps", "1h")
                    .queryParam("units", "metric")
                    .queryParam("endTime", LocalDateTime.now().plusHours(72))
                    .queryParam("apikey", API_KEY);
            URL url = new URL(builder.toUriString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            int response = conn.getResponseCode();
            if (response == HttpStatus.OK.value()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    api.append(line);
                }
                reader.close();
            }
        } catch (IOException e) {
            throw new IOException("API fails to find any data from Tomorrow.io/timeline API");
        }
        return api.toString();
    }
}
