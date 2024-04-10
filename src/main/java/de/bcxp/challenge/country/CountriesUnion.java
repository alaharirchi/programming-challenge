package de.bcxp.challenge.country;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.reverseOrder;

public class CountriesUnion {
    List<CountryInfo> countries;

    public CountriesUnion(CountryReader reader) throws CountryInputException {
        countries = reader.read();
    }

    public List<Map.Entry<String, Double>> highestPopulationDensity() {
        Map<String, Double> densities = countries.stream()
                .collect(Collectors.toMap(e -> e.getName(), e -> e.populationDensity()));

        List<Map.Entry<String, Double>> sortedDensities = densities.entrySet().stream()
                .sorted(reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toList());

        double highlyDensed = sortedDensities.get(0).getValue();
        return sortedDensities.stream()
                .filter(e -> e.getValue().equals(highlyDensed))
                .collect(Collectors.toList());
    }
}
