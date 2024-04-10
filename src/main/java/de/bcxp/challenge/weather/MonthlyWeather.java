package de.bcxp.challenge.weather;

import de.bcxp.challenge.weather.exceptions.DataException;
import de.bcxp.challenge.weather.exceptions.InputException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MonthlyWeather {
    private List<WeatherInfo> weatherInfos;

    MonthlyWeather(WeatherReader reader) throws DataException, InputException {
        weatherInfos = reader.read();
    }

    List<Map.Entry<String, Double>> getSmallestSpread() {
        Map<String, Double> spreads = weatherInfos.stream()
                .collect(Collectors.toMap(e -> e.getDay(), e -> {
                    try {
                        return e.getSpread();
                    } catch (DataException ex) {
                        // I made the decision that still the rest of spreads should be calculated
                        // TODO log something to show there was an issue
                        return Double.MAX_VALUE; // to ensure this day will not be picked as "smallest" spread
                    }
                }));

        List<Map.Entry<String, Double>> sortedSpreads = spreads.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toList());

        double smallestSpread = sortedSpreads.get(0).getValue();
        return sortedSpreads.stream()
                .filter(e -> e.getValue().equals(smallestSpread))
                .collect(Collectors.toList());
    }
}
