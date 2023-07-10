package com.tomorrow.service;

import com.tomorrow.util.ConditionChecker;
import com.tomorrow.util.WeatherApiBuilder;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class WeatherService {
    private final WeatherApiBuilder weatherApiBuilder = new WeatherApiBuilder();
    private final ConditionChecker conditionChecker = new ConditionChecker();

    public String getWeatherConditions(String location, String rule, String operator) throws IOException {
        String api = weatherApiBuilder.getApi(location);
        String[] rules = rule.split(",");
        JSONObject newApi;
        try {
            newApi = new JSONObject(api);
            JSONArray timelines = newApi.getJSONObject("data").getJSONArray("timelines");
            for (int i = 0; i < timelines.length(); i++) {
                JSONObject object = timelines.getJSONObject(i);
                JSONArray intervals = object.getJSONArray("intervals");
                for (int j = 0; j < intervals.length(); j++) {
                    JSONObject curr = intervals.getJSONObject(j).getJSONObject("values");
                    boolean ans = conditionChecker.checkIfConditionMet(curr, rules, operator);
                    curr.put("condition_met", ans);
                }
            }
        } catch (JSONException e) {
            throw new IllegalArgumentException(e);
        }
        return newApi.toString();
    }
}
