package com.tomorrow.controller;

import com.tomorrow.service.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
        if (operator.length() == 0) {
            operator = "AND";
        }
        if (!operator.equals("OR") && !operator.equals("AND")) {
            return new ResponseEntity<>("Invalid operator provided, please use 'AND' or 'OR'", HttpStatusCode.valueOf(400));
        }
        if (rule.length() == 0) {
            return new ResponseEntity<>("Rule cannot be empty", HttpStatusCode.valueOf(400));
        }
        try {
            String conditions = weatherService.getWeatherConditions(location, rule, operator);
            return new ResponseEntity<>(conditions, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(400));
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(404));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(500));
        }
    }
}
