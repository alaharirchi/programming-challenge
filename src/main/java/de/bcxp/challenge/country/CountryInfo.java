package de.bcxp.challenge.country;

public class CountryInfo {

    private String name;
    private int population;
    private int area;

    public CountryInfo(String name, int population, int area) {
        this.name = name;
        this.population = population;
        this.area = area; // TODO custom exception for when area is zero
    }

    public String getName() {
        return name;
    }

    public double populationDensity() {
        return population / area;
    }

}