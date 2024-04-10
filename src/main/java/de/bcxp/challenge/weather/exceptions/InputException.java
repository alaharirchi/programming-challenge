package de.bcxp.challenge.weather.exceptions;

public class InputException extends Exception {
    public InputException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
