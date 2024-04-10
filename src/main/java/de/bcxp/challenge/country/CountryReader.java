package de.bcxp.challenge.country;

import java.util.List;

public interface CountryReader {

    String POPULATION_HEADER = "Population";
    String AREA_HEADER = "Area (km²)";

    List<CountryInfo> read() throws CountryInputException;
}
