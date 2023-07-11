package com.weather.service;

import com.weather.util.Interval;
import com.weather.util.Timeline;
import com.weather.util.ConditionChecker;
import com.weather.util.WeatherApiBuilder;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherService {
    private final WeatherApiBuilder weatherApiBuilder = new WeatherApiBuilder();
    private final ConditionChecker conditionChecker = new ConditionChecker();

    public Timeline getWeatherConditions(String location, String rule, String operator) throws IOException, InterruptedException, JSONException {
        JSONObject newApi;
        try {
            newApi = new JSONObject(weatherApiBuilder.getApi(location));
            String[] rules = rule.split(",");
            JSONArray intervals = newApi.getJSONObject("data").getJSONArray("timelines").getJSONObject(0).getJSONArray("intervals");
            return new Timeline(getTimelines(intervals, rules, operator));
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Interval> getTimelines(JSONArray intervals, String[] rules, String operator) throws JSONException {
        List<Interval> timelines = new ArrayList<>();
        boolean firstMet = conditionChecker.checkIfConditionMet(intervals.getJSONObject(0).getJSONObject("values"), rules, operator);
        String startTime = intervals.getJSONObject(0).getString("startTime");
        for (int i = 1; i < intervals.length(); i++) {
            JSONObject values = intervals.getJSONObject(i).getJSONObject("values");
            boolean isMet = conditionChecker.checkIfConditionMet(values, rules, operator);
            if (isMet != firstMet) {
                Interval interval = new Interval(startTime, intervals.getJSONObject(i).getString("startTime"), firstMet);
                timelines.add(interval);
                startTime = intervals.getJSONObject(i).getString("startTime");
                firstMet = isMet;
            }
        }
        Interval interval = new Interval(startTime, intervals.getJSONObject(intervals.length()-1).getString("startTime"), firstMet);
        timelines.add(interval);
        return timelines;
    }
}
