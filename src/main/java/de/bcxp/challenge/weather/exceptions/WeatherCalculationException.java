package de.bcxp.challenge.weather.exceptions;

public class WeatherCalculationException extends Exception {
    public WeatherCalculationException(String errorMessage) {
        super(errorMessage);
    }
}
