package de.bcxp.challenge.weather;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;


public class CsvWeatherReader implements WeatherReader {

    private String fileName;

    public CsvWeatherReader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<WeatherInfo> read() throws InputException {

        List<String[]> lines;
        try {
            lines = readFromFile();
        } catch (Exception e) {
            throw new InputException("Cannot read from file", e);
        }

        return List.of();
    }

    List<String[]> readFromFile() throws URISyntaxException, IOException, CsvException {
        URL resource = CsvWeatherReader.class.getClassLoader().getResource(fileName);
        File file = Paths.get(resource.toURI()).toFile();

        List<String[]> lines;
        CSVReader reader = new CSVReader(new FileReader(file));
        lines = reader.readAll();

        return lines;
    }
}
