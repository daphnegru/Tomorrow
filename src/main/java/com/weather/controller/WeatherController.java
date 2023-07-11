package com.weather.controller;

import com.weather.error.WeatherError;
import com.weather.error.WeatherErrorDetails;
import com.weather.response.BasicResponse;
import com.weather.response.WeatherResponse;
import com.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class WeatherController {
    private final WeatherService weatherService;
    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather-conditions")
    public ResponseEntity<BasicResponse> getWeatherConditions(@RequestParam(value = "location") String location,
                                                              @RequestParam(value = "rule") String rule,
                                                              @RequestParam("operator") String operator) {
        if (rule.isEmpty() || location.isEmpty()) {
            WeatherErrorDetails details = new WeatherErrorDetails(HttpStatus.BAD_REQUEST.value(), "Rule/Location cannot be empty");
            return new ResponseEntity<>(new WeatherError("error", details), HttpStatus.BAD_REQUEST);
        }
        if (operator.isEmpty()) {
            operator = "AND";
        }
        if (!operator.equals("OR") && !operator.equals("AND")) {
            WeatherErrorDetails details = new WeatherErrorDetails(HttpStatus.BAD_REQUEST.value(), "Invalid operator provided, please use 'AND' or 'OR'");
            return new ResponseEntity<>(new WeatherError("error", details), HttpStatus.BAD_REQUEST);
        }
        try {
            WeatherResponse response = new WeatherResponse("success", weatherService.getWeatherConditions(location, rule, operator));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            WeatherErrorDetails details = new WeatherErrorDetails(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return new ResponseEntity<>(new WeatherError("error", details), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            WeatherErrorDetails details = new WeatherErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return new ResponseEntity<>(new WeatherError("error", details), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (InterruptedException | IOException e) {
            WeatherErrorDetails details = new WeatherErrorDetails(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return new ResponseEntity<>(new WeatherError("error", details), HttpStatus.NOT_FOUND);
        }
    }
}
