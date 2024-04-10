package de.bcxp.challenge.country;


import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class CountriesUnionTest {

    @Test
    void getHighestDensity() throws CountryInputException {
        // given
        CountryInfo croatia = new CountryInfo("Croatia", 4036355, 56594); // 71
        CountryInfo cyprus = new CountryInfo("Cyprus", 896000, 9251); // 96
        CountryInfo czechRepublic = new CountryInfo("Czech Republic", 10574153, 78866); // 134
        CountriesUnion eu = new CountriesUnion(new MockParser(List.of(croatia, cyprus, czechRepublic)));

        // when
        List<Map.Entry<String, Double>> highlyDensed = eu.highestPopulationDensity();

        // then
        assertAll(
                () -> assertEquals(1, highlyDensed.size()),
                () -> assertEquals(czechRepublic.getName(), highlyDensed.get(0).getKey())
        );
    }

    @Test
    void moreThanOneResult() throws CountryInputException {
        // given
        CountryInfo wonderland = new CountryInfo("Wonderland", 10, 1);
        CountryInfo neverland = new CountryInfo("Neverland", 20, 2);
        CountryInfo oz = new CountryInfo("Oz", 20, 5);
        CountriesUnion magicalUnion = new CountriesUnion(new MockParser(List.of(wonderland, neverland, oz)));

        // when
        List<Map.Entry<String, Double>> highlyDensed = magicalUnion.highestPopulationDensity();

        // then
        assertAll(
                () -> assertFalse(highlyDensed.isEmpty()),
                () -> assertEquals(2, highlyDensed.size()),
                () -> assertTrue(highlyDensed.stream().anyMatch(e -> e.getKey().equals(wonderland.getName()))),
                () -> assertTrue(highlyDensed.stream().anyMatch(e -> e.getKey().equals(neverland.getName())))
        );
    }

    private class MockParser implements CountryReader {

        private List<CountryInfo>  returnValue;

        public MockParser(List<CountryInfo>  returnValue) {
            this.returnValue = returnValue;
        }


        @Override
        public List<CountryInfo> read() {
            return returnValue;
        }
    }
}