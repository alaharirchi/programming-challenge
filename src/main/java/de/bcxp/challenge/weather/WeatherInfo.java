package de.bcxp.challenge.weather;

import de.bcxp.challenge.weather.exceptions.WeatherCalculationException;

import java.util.HashMap;
import java.util.Map;

import static de.bcxp.challenge.weather.WeatherReader.MAX_TEMP_HEADER;
import static de.bcxp.challenge.weather.WeatherReader.MIN_TEMP_HEADER;

public class WeatherInfo {
    private String day;
    private Map<String, Double> measurements;

    WeatherInfo(String day) {
        this.day = day;
        measurements = new HashMap<>();
    }

    WeatherInfo(String day, Map<String, Double> measurements) {
        this.day = day;
        this.measurements = measurements;
    }

    public void setMeasurement(String item, double value) {
        measurements.put(item, value);
    }

    public String getDay() {
        return day;
    }

    public Double getSpread() throws WeatherCalculationException {
        Double maxTemp = measurements.get(MAX_TEMP_HEADER);
        Double minTemp = measurements.get(MIN_TEMP_HEADER);
        if ((maxTemp == null) || (minTemp == null)) {
            throw new WeatherCalculationException("Cannot calculate temperature spread: Not enough data");
        }
        return maxTemp - minTemp;
    }
}
