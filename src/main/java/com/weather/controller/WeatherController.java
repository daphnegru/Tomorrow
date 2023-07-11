package com.weather.controller;

import com.weather.service.WeatherService;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class WeatherController {
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather-conditions")
    public ResponseEntity<String> getWeatherConditions(@RequestParam("location") String location, @RequestParam("rule") String rule, @RequestParam("operator") String operator) {
        if (rule.isEmpty()) {
            return new ResponseEntity<>("Rule cannot be empty", HttpStatus.BAD_REQUEST);
        }
        if (operator.isEmpty()) {
            operator = "AND";
        }
        if (!operator.equals("OR") && !operator.equals("AND")) {
            return new ResponseEntity<>("Invalid operator provided, please use 'AND' or 'OR'", HttpStatus.BAD_REQUEST);
        }
        try {
            String conditions = weatherService.getWeatherConditions(location, rule, operator);
            return new ResponseEntity<>(conditions, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (InterruptedException | IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
