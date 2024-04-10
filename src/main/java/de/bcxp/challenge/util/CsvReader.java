package de.bcxp.challenge.util;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;

public abstract class CsvReader {

    public static List<String[]> readFromFile(String fileName, char separator) throws URISyntaxException, IOException, CsvException {
        URL resource = CsvReader.class.getClassLoader().getResource(fileName);
        File file = Paths.get(resource.toURI()).toFile();

        List<String[]> lines;
        CSVReader reader = new CSVReaderBuilder(new FileReader(file)).
                withCSVParser(new CSVParserBuilder()
                        .withSeparator(separator)
                        .build()
                ).build();

        lines = reader.readAll();

        return lines;
    }

    public static void checkFormat(List<String[]> lines) throws CsvException {
        if (lines == null || lines.isEmpty()) {
            throw new CsvException("Bad data: no headers or measurements");
        }

        if (lines.size() < 2) {
            throw new CsvException("Bad data: no measurements");
        }

        int headerCount = lines.get(0).length;

        for (int i = 1; i < lines.size(); i++) {
            String[] line = lines.get(i);
            if (line.length != headerCount) {
                throw new CsvException("Bad data: number of elements in line does not match headers");
            }
        }
    }
}
