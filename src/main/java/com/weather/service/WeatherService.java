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
        JSONObject response = new JSONObject();
        try {
            newApi = new JSONObject(weatherApiBuilder.getApi(location));
            String[] rules = rule.split(",");
            JSONArray intervals = newApi.getJSONObject("data").getJSONArray("timelines").getJSONObject(0).getJSONArray("intervals");
            response.put("timelines", getTimelines(intervals, rules, operator));
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
        return response.toString();
    }

    private JSONArray getTimelines(JSONArray intervals, String[] rules, String operator) throws JSONException {
        JSONArray timelines = new JSONArray();
        boolean firstMet = conditionChecker.checkIfConditionMet(intervals.getJSONObject(0).getJSONObject("values"), rules, operator);
        String startTime = intervals.getJSONObject(0).getString("startTime");
        for (int i = 1; i < intervals.length(); i++) {
            JSONObject values = intervals.getJSONObject(i).getJSONObject("values");
            JSONObject curr = new JSONObject();
            boolean isMet = conditionChecker.checkIfConditionMet(values, rules, operator);
            if (isMet != firstMet) {
                curr.put("startTime", startTime);
                curr.put("endTime", intervals.getJSONObject(i).getString("startTime"));
                curr.put("condition_met", firstMet);
                timelines.put(curr);
                startTime = intervals.getJSONObject(i).getString("startTime");
                firstMet = isMet;
            }
        }
        JSONObject curr = new JSONObject();
        curr.put("startTime", startTime);
        curr.put("endTime", intervals.getJSONObject(intervals.length()-1).getString("startTime"));
        curr.put("condition_met", firstMet);
        timelines.put(curr);
        return timelines;
    }
}
