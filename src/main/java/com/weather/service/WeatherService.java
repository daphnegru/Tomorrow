package com.weather.service;

import org.springframework.stereotype.Service;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import com.weather.util.ConditionChecker;
import com.weather.util.WeatherApiBuilder;

import java.io.IOException;

@Service
public class WeatherService {
    private final WeatherApiBuilder weatherApiBuilder = new WeatherApiBuilder();
    private final ConditionChecker conditionChecker = new ConditionChecker();

    public String getWeatherConditions(String location, String rule, String operator) throws IOException, InterruptedException, JSONException {
        JSONObject newApi;
        try {
            String api = weatherApiBuilder.getApi(location);
            String[] rules = rule.split(",");
            newApi = new JSONObject(api);
            JSONArray timelines = newApi.getJSONObject("data").getJSONArray("timelines");
            for (int i = 0; i < timelines.length(); i++) {
                JSONObject timeline = timelines.getJSONObject(i);
                JSONArray intervals = timeline.getJSONArray("intervals");
                for (int j = 0; j < intervals.length(); j++) {
                    JSONObject curr = intervals.getJSONObject(j).getJSONObject("values");
                    curr.put("condition_met", conditionChecker.checkIfConditionMet(curr, rules, operator));
                }
            }
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
        return newApi.toString();
    }
}
