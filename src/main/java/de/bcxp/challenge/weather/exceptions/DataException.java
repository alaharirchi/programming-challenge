package de.bcxp.challenge.weather.exceptions;

public class DataException extends Exception {
    public DataException(String errorMessage) {
        super(errorMessage);
    }
    public DataException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
