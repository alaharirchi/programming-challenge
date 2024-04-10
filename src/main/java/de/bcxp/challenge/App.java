package de.bcxp.challenge;

import de.bcxp.challenge.country.CountriesUnion;
import de.bcxp.challenge.country.CountryInputException;
import de.bcxp.challenge.country.CsvCountryReader;
import de.bcxp.challenge.weather.CsvWeatherReader;
import de.bcxp.challenge.weather.MonthlyWeather;
import de.bcxp.challenge.weather.exceptions.WeatherInputException;

import java.util.List;
import java.util.Map;

/**
 * The entry class for your solution. This class is only aimed as starting point and not intended as baseline for your software
 * design. Read: create your own classes and packages as appropriate.
 */
public final class App {

    /**
     * This is the main entry method of your program.
     * @param args The CLI arguments passed
     */
    public static void main(String... args) throws WeatherInputException, CountryInputException {

        MonthlyWeather report = new MonthlyWeather(new CsvWeatherReader("de/bcxp/challenge/weather.csv"));
        List<Map.Entry<String, Double>> results = report.getSmallestSpread();
        if (results.size() > 1 ) {
            System.out.println("More than one result for the day with smallest temperature spread");
        }

        String dayWithSmallestTempSpread = results.get(0).getKey();
        System.out.printf("Day with smallest temperature spread: %s%n", dayWithSmallestTempSpread);

        CountriesUnion eu = new CountriesUnion(new CsvCountryReader("de/bcxp/challenge/countries.csv"));
        List<Map.Entry<String, Double>> countries = eu.highestPopulationDensity();
        if (countries.size() > 1 ) {
            System.out.println("More than one result for the country with highest population density");
        }
        String countryWithHighestPopulationDensity = countries.get(0).getKey();
        System.out.printf("Country with highest population density: %s%n", countryWithHighestPopulationDensity);
    }
}
