package com.weather.service;

import com.weather.util.ConditionChecker;
import com.weather.util.WeatherApiBuilder;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class WeatherService {
    private final WeatherApiBuilder weatherApiBuilder = new WeatherApiBuilder();
    private final ConditionChecker conditionChecker = new ConditionChecker();

    public String getWeatherConditions(String location, String rule, String operator) throws IOException, InterruptedException, JSONException {
        JSONObject newApi;
        try {
            newApi = new JSONObject(weatherApiBuilder.getApi(location));
            String[] rules = rule.split(",");
            JSONArray intervals = newApi.getJSONObject("data").getJSONArray("timelines").getJSONObject(0).getJSONArray("intervals");
            for (int i = 0; i < intervals.length(); i++) {
                JSONObject values = intervals.getJSONObject(i).getJSONObject("values");
                values.put("condition_met", conditionChecker.checkIfConditionMet(values, rules, operator));
            }
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
        return newApi.toString();
    }
}
