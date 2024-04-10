package de.bcxp.challenge.weather;

import de.bcxp.challenge.weather.exceptions.WeatherCalculationException;
import org.junit.jupiter.api.Test;

import static de.bcxp.challenge.weather.WeatherReader.MAX_TEMP_HEADER;
import static de.bcxp.challenge.weather.WeatherReader.MIN_TEMP_HEADER;
import static org.junit.jupiter.api.Assertions.*;

class WeatherInfoTest {

    @Test
    void givenTemperatureMeasurements() throws WeatherCalculationException {
        // given
        WeatherInfo day = new WeatherInfo("1");
        day.setMeasurement(MAX_TEMP_HEADER, 25.0);
        day.setMeasurement(MIN_TEMP_HEADER, 22.0);

        // when
        Double spread = day.getSpread();

        // then
        assertEquals(3, spread);
    }

    @Test
    void givenMissingTemperatureMeasurements() {
        // given
        WeatherInfo day = new WeatherInfo("1");
        day.setMeasurement(MAX_TEMP_HEADER, 25.0);

        // when, then
        assertThrows(WeatherCalculationException.class, () -> day.getSpread());
    }
}