package de.bcxp.challenge.weather;

import com.opencsv.exceptions.CsvException;
import de.bcxp.challenge.weather.exceptions.WeatherInputException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static de.bcxp.challenge.weather.CsvWeatherReader.checkFormat;
import static de.bcxp.challenge.weather.CsvWeatherReader.checkSanity;
import static de.bcxp.challenge.weather.WeatherReader.*;
import static org.junit.jupiter.api.Assertions.*;

class CsvWeatherReaderTest {

    @Test
    void givenNonexistentFile() {
        assertThrows(WeatherInputException.class, () -> new CsvWeatherReader("w3ather.csv").read());
    }

    @Test
    void givenCsvFile() throws Exception {
        List<WeatherInfo> data = new CsvWeatherReader("weather.csv").read();
        assertNotNull(data);
        assertFalse(data.isEmpty());
    }

    // Sanity tests
    @Test
    void givenCleanData() {
        // given
        String[] headers =  {DAY_HEADER, MAX_TEMP_HEADER, MIN_TEMP_HEADER};
        String[] measurements1 =  {"1", "15", "13"};
        String[] measurements2 =  {"1", "17", "14"};

        List<String[]> lines = List.of(headers, measurements1, measurements2);

        // when, then
        assertDoesNotThrow(() -> checkFormat(lines));
    }

    @Test
    void givenNullData() {
        assertThrows(CsvException.class, () -> checkFormat(null));
    }

    @Test
    void givenEmptyData() {
        assertThrows(CsvException.class, () -> checkFormat(new ArrayList<>()));
    }

    @Test
    void givenNoMeasurements() {
        // given
        String[] headers =  {DAY_HEADER, MAX_TEMP_HEADER, MIN_TEMP_HEADER};
        List<String[]> lines = new ArrayList<>();
        lines.add(headers);

        // when, then
        assertThrows(CsvException.class, () -> checkFormat(lines));
    }

    @Test
    void givenTooFewMeasurements() {
        // given
        String[] headers =  {DAY_HEADER, MAX_TEMP_HEADER, MIN_TEMP_HEADER};
        String[] measurements =  {"1", "15"};

        List<String[]> lines = List.of(headers, measurements);

        // when. then
        assertThrows(CsvException.class, () -> checkFormat(lines));
    }

    @Test
    void givenTooManyMeasurements() {
        // given
        String[] headers =  {DAY_HEADER, MAX_TEMP_HEADER, MIN_TEMP_HEADER};
        String[] measurements =  {"1", "15", "13", "42"};

        List<String[]> lines = List.of(headers, measurements);

        // when, then
        assertThrows(CsvException.class, () -> checkFormat(lines));
    }

    @Test
    void givenUnparsableMeasurements() {
        // given
        String[] headers =  {DAY_HEADER, MAX_TEMP_HEADER, MIN_TEMP_HEADER};
        String[] measurements =  {"1", "fifteen", "thirteen"};

        List<String[]> lines = List.of(headers, measurements);

        // when, then
        assertThrows(WeatherInputException.class, () -> checkSanity(lines));
    }
}