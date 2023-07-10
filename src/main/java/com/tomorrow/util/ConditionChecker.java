package com.tomorrow.util;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

public class ConditionChecker {

    public boolean checkIfConditionMet(JSONObject interval, String[] rules, String operator) throws JSONException {
        double humidity = interval.getDouble("humidity");
        double rainIntensity = interval.getDouble("rainIntensity");
        double temperature = interval.getDouble("temperature");
        double windSpeed = interval.getDouble("windSpeed");
        for (String rule : rules) {
            String[] parts = rule.trim().split("(?<=[><=])|(?=[><=])");
            String field = parts[0].trim();
            String op = parts[1].trim();
            double val = Double.parseDouble(parts[2].trim());
            boolean met = false;
            switch (field) {
                case "humidity" -> met = compareVals(humidity, op, val);
                case "rainIntensity" -> met = compareVals(rainIntensity, op, val);
                case "temperature" -> met = compareVals(temperature, op, val);
                case "windSpeed" -> met = compareVals(windSpeed, op, val);
            }
            if (operator.equals("OR") && met) {
                return true;
            } else if (operator.equals("AND") && !met) {
                return false;
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