package de.bcxp.challenge.weather;

import java.util.List;

public interface WeatherReader {
    List<WeatherInfo> read() throws InputException;
}
