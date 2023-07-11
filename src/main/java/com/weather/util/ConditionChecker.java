package com.weather.util;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

public class ConditionChecker {
    private final String HUMIDITY = "humidity";
    private final String RAIN_INTENSITY = "rainIntensity";
    private final String TEMPERATURE = "temperature";
    private final String WIND_SPEED = "windSpeed";

    public boolean checkIfConditionMet(JSONObject interval, String[] rules, String operator) throws JSONException {
        double humidity = interval.getDouble(HUMIDITY);
        double rainIntensity = interval.getDouble(RAIN_INTENSITY);
        double temperature = interval.getDouble(TEMPERATURE);
        double windSpeed = interval.getDouble(WIND_SPEED);
        for (String rule : rules) {
            String[] parts = rule.trim().split("(?<=[><=])|(?=[><=])");
            String field = parts[0].trim();
            String op = parts[1].trim();
            double val = Double.parseDouble(parts[2].trim());
            boolean met = false;
            switch (field) {
                case HUMIDITY -> met = compareVals(humidity, op, val);
                case RAIN_INTENSITY -> met = compareVals(rainIntensity, op, val);
                case TEMPERATURE -> met = compareVals(temperature, op, val);
                case WIND_SPEED -> met = compareVals(windSpeed, op, val);
            }
            if ((operator.equals("OR") && met) || (operator.equals("AND") && !met)) {
                return met;
            }
        }
        return operator.equals("AND");
    }

    private boolean compareVals(double val, String op, double condition) {
        return switch (op) {
            case ">" -> val > condition;
            case "<" -> val < condition;
            case ">=" -> val >= condition;
            case "<=" -> val <= condition;
            case "=" -> val == condition;
            default -> false;
        };
    }
}