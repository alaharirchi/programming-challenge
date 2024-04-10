package de.bcxp.challenge.weather;

import de.bcxp.challenge.weather.exceptions.DataException;
import de.bcxp.challenge.weather.exceptions.InputException;

import java.util.List;

public interface WeatherReader {
    String DAY_HEADER = "Day";
    String MAX_TEMP_HEADER = "MxT";
    String MIN_TEMP_HEADER = "MnT";
    List<WeatherInfo> read() throws InputException, DataException;
}
