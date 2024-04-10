package de.bcxp.challenge.weather;

import de.bcxp.challenge.weather.exceptions.DataException;
import de.bcxp.challenge.weather.exceptions.InputException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static de.bcxp.challenge.weather.WeatherReader.MAX_TEMP_HEADER;
import static de.bcxp.challenge.weather.WeatherReader.MIN_TEMP_HEADER;

class MonthlyWeatherTest {

    @Test
    void getSmallestSpread() throws DataException, InputException {

        // given
        WeatherInfo day1 = new WeatherInfo("1",
                Map.of(MAX_TEMP_HEADER, 14.0, MIN_TEMP_HEADER, 10.0));

        WeatherInfo day2 = new WeatherInfo("2",
                Map.of(MAX_TEMP_HEADER, 6.0, MIN_TEMP_HEADER, 5.0));

        WeatherInfo day3 = new WeatherInfo("3",
                Map.of(MAX_TEMP_HEADER, 20.0, MIN_TEMP_HEADER, 10.0));

        MonthlyWeather mw = new MonthlyWeather(
                new MonthlyWeatherTest.MockReader(List.of(day1, day2, day3)));

        // when
        List<Map.Entry<String, Double>> result = mw.getSmallestSpread();

        // then
        assertAll(
                () -> assertEquals(1, result.size()),
                () -> assertEquals("2", result.get(0).getKey())
        );
    }

    @Test
    void givenMissingMeasurementsForSomeDay_ThenCalculateForTheRest() throws DataException, InputException {

        // given
        WeatherInfo day1 = new WeatherInfo("1",
                Map.of(MAX_TEMP_HEADER, 14.0, MIN_TEMP_HEADER, 10.0));

        WeatherInfo day2 = new WeatherInfo("2",
                Map.of(MAX_TEMP_HEADER, 6.0));

        WeatherInfo day3 = new WeatherInfo("3",
                Map.of(MAX_TEMP_HEADER, 20.0, MIN_TEMP_HEADER, 10.0));

        MonthlyWeather mw = new MonthlyWeather(
                new MonthlyWeatherTest.MockReader(List.of(day1, day2, day3)));

        // when
        List<Map.Entry<String, Double>> result = mw.getSmallestSpread();

        // then
        assertAll(
                () -> assertEquals(1, result.size()),
                () -> assertEquals("1", result.get(0).getKey())
        );
    }

    @Test
    void givenMultipleDaysWithSmallestSpread() throws DataException, InputException {

        // given
        WeatherInfo day1 = new WeatherInfo("1",
                Map.of(MAX_TEMP_HEADER, 14.0, MIN_TEMP_HEADER, 10.0));

        WeatherInfo day2 = new WeatherInfo("2",
                Map.of(MAX_TEMP_HEADER, 24.0, MIN_TEMP_HEADER, 20.0));

        WeatherInfo day3 = new WeatherInfo("3",
                Map.of(MAX_TEMP_HEADER, 20.0, MIN_TEMP_HEADER, 10.0));

        MonthlyWeather mw = new MonthlyWeather(
                new MonthlyWeatherTest.MockReader(List.of(day1, day2, day3)));

        // when
        List<Map.Entry<String, Double>> result = mw.getSmallestSpread();

        // then
        assertAll(
                () -> assertEquals(2, result.size()),
                () -> assertTrue(result.stream().anyMatch(e -> e.getKey().equals("1"))),
                () -> assertTrue(result.stream().anyMatch(e -> e.getKey().equals("2")))
        );
    }

    private class MockReader implements WeatherReader {

        private List<WeatherInfo> returnValue;

        public MockReader(List<WeatherInfo> returnValue) {
            this.returnValue = returnValue;
        }

        @Override
        public List<WeatherInfo> read() throws InputException, DataException {
            return returnValue;
        }
    }
}