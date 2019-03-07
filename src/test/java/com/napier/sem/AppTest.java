package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

public class AppTest {

    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
    }

    @Test
    void displayCountriesTestNull()
    {
        app.displayCountries(null);
    }

    @Test
    void displayCountriesTestEmpty()
    {
        ArrayList<Country> countries = new ArrayList<>();
        app.displayCountries(countries);
    }

    @Test
    void displayCountriesTestContainsNull()
    {
        ArrayList<Country> countries = new ArrayList<>();
        countries.add(null);
        app.displayCountries(countries);
    }

    @Test
    void displayCountries()
    {
        // Create country list
        ArrayList<Country> countries = new ArrayList<>();
        Country aruba = new Country();
        aruba.code = "ABW";
        aruba.name = "Aruba";
        aruba.continent = "North America";
        aruba.region = "Caribbean";
        aruba.population = 103000;
        aruba.capital_name = "Oranjestad";
        countries.add(aruba);

        // Display country list
        app.displayCountries(countries);
    }
}
