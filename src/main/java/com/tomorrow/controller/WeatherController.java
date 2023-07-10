package com.tomorrow.controller;

import com.tomorrow.service.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather-conditions")
    public ResponseEntity<String> getWeatherConditions(@RequestParam("location") String location, @RequestParam("rule") String rule, @RequestParam("operator") String operator) {
        return new ResponseEntity<>(weatherService.getWeatherConditions(location, rule, operator), HttpStatus.OK);
    }
}
