package de.bcxp.challenge.country;

public class CountryInputException extends Exception {
    public CountryInputException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public CountryInputException(String errorMessage) {
        super(errorMessage);
    }
}