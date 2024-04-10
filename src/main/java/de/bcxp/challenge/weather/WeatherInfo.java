package de.bcxp.challenge.weather;

import java.util.HashMap;
import java.util.Map;

public class WeatherInfo {
    private String day;
    private Map<String, Double> measurements;

    WeatherInfo(String day) {
        this.day = day;
        measurements = new HashMap<>();
    }

    public void setMeasurement(String item, double value) {
        measurements.put(item, value);
    }
}
