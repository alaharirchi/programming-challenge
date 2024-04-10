package de.bcxp.challenge.weather;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import de.bcxp.challenge.weather.exceptions.DataException;
import de.bcxp.challenge.weather.exceptions.InputException;


public class CsvWeatherReader implements WeatherReader {

    private String fileName;

    public CsvWeatherReader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<WeatherInfo> read() throws InputException, DataException {

        List<String[]> lines;
        try {
            lines = readFromFile();
        } catch (Exception e) {
            throw new InputException("Cannot read from file", e);
        }

        checkSanity(lines);

        ArrayList<WeatherInfo> infos = new ArrayList<>();
        String[] header = lines.get(0);
        for (int i = 1; i < lines.size(); i++) {
            String[] line = lines.get(i);

            WeatherInfo info = new WeatherInfo(line[0]);
            for (int j = 1; j < line.length; j++) {
                info.setMeasurement(header[j], Double.parseDouble(line[j]));
            }

            infos.add(info);
        }

        return infos;
    }

    static void checkSanity(List<String[]> lines) throws DataException {
        if (lines == null || lines.isEmpty()) {
            throw new DataException("Bad data: no headers or measurements");
        }

        if (lines.size() < 2) {
            throw new DataException("Bad data: no measurements");
        }

        int headerCount = lines.get(0).length;

        for (int i = 1; i < lines.size(); i++) {
            String[] line = lines.get(i);
            if (line.length != headerCount) {
                throw new DataException("Bad data: number of elements in line does not match headers");
            }

            for (String measurement : line) {
                try {
                    Double.parseDouble(measurement);
                } catch (java.lang.NumberFormatException e) {
                    throw new DataException("Bad data: measurement not parsable to double", e);
                }
            }
        }
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
