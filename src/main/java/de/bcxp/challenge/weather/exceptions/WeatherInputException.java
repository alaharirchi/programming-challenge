package de.bcxp.challenge.weather.exceptions;

public class WeatherInputException extends Exception {
    public WeatherInputException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
