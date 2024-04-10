package de.bcxp.challenge.country;

import com.opencsv.exceptions.CsvException;
import de.bcxp.challenge.util.CsvReader;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class CsvCountryReader extends CsvReader implements CountryReader {

    private static final int NAME_HEADER_INDEX = 0;
    private String fileName;

    public CsvCountryReader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<CountryInfo> read() throws CountryInputException {
        List<String[]> lines;
        try {
            lines = readFromFile(fileName, ';');
        } catch (Exception e) {
            throw new CountryInputException("Cannot read from file", e);
        }

        try {
            checkFormat(lines);
        } catch (CsvException e) {
            throw new CountryInputException("Checking format failed", e);
        }

        String[] header = lines.get(0);
        int populationIndex = Arrays.asList(header).indexOf(POPULATION_HEADER);
        int areaIndex = Arrays.asList(header).indexOf(AREA_HEADER);
        if (populationIndex == -1 || areaIndex == -1) {
            throw new CountryInputException("Missing data");
        }

        List<CountryInfo> countries = new ArrayList<>();

        for (int i = 1; i < lines.size(); i++) {
            String[] line = lines.get(i);
            String name = line[NAME_HEADER_INDEX];
            int population = sanitizedInt(line[populationIndex]);
            // todo maybe also check for zero for area?
            int area = Integer.parseInt(line[areaIndex]);
            CountryInfo country = new CountryInfo(name, population, area);

            countries.add(country);
        }

        return countries;
    }

    private static int sanitizedInt(String number) {
        Number parsed = 0;
        try {
            parsed = NumberFormat.getNumberInstance(Locale.GERMAN).parse(number);
        } catch (ParseException e) {
            // I made the decision to not throw an exception
            // TODO log
        }
        return parsed.intValue();
    }

}
