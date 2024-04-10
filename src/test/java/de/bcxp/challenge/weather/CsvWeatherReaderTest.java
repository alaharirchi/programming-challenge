package de.bcxp.challenge.weather;

import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvWeatherReaderTest {

    @Test
    void givenNonexistentFile() {
        assertThrows(InputException.class, () -> new CsvWeatherReader("w3ather.csv").read());
    }

    @Test
    void givenCsvFile() throws InputException {
        List<WeatherInfo> data = new CsvWeatherReader("weather.csv").read();
        assertNotNull(data);
    }
}