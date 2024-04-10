package de.bcxp.challenge.weather;

import java.util.ArrayList;
import java.util.List;

import com.opencsv.exceptions.CsvException;
import de.bcxp.challenge.util.CsvReader;
import de.bcxp.challenge.weather.exceptions.WeatherInputException;


public class CsvWeatherReader extends  CsvReader implements WeatherReader {

    private String fileName;

    public CsvWeatherReader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<WeatherInfo> read() throws WeatherInputException {

        List<String[]> lines;
        try {
            lines = readFromFile(fileName, ',');
        } catch (Exception e) {
            throw new WeatherInputException("Cannot read from file", e);
        }

        try {
            checkSanity(lines);
        } catch (CsvException e) {
            throw new WeatherInputException("Checking sanity failed", e);
        }

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

    public static void checkSanity(List<String[]> lines) throws WeatherInputException, CsvException {
        checkFormat(lines);
        for (int i = 1; i < lines.size(); i++) {
            String[] line = lines.get(i);

            for (String measurement : line) {
                try {
                    Double.parseDouble(measurement);
                } catch (java.lang.NumberFormatException e) {
                    throw new WeatherInputException("Bad data: measurement not parsable to double", e);
                }
            }
        }
    }

}
