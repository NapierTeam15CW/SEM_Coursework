package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppIntegrationTest
{
    static App app;

    static String location = "localhost:33060";

    @BeforeAll
    static void init()
    {
        app = new App();
        app.connect(location);
    }

    @Test
    void testGet()
    {
        City aCity = app.getCity(1);
        // Expected Results
        assertEquals(aCity.ID, 1);
        assertEquals(aCity.Name, "Kabul");
        assertEquals(aCity.CountryCode, "AFG");
        assertEquals(aCity.District, "Kabol");
        assertEquals(aCity.Population, 1780000);
    }

    @Test
    void testGetCountries()
    {
        int outputSize = app.getCountries("5").size();
        assertEquals(5, outputSize);
    }

    @Test
    void testGetCountriesNull()
    {
        int outputSize = app.getCountriesInContinent("I am groot","-1").size();
        assertEquals(0,outputSize);
    }

    @Test
    void testGetCountriesError()
    {
        app.disconnect();
        assertNull(app.getCountries("5"));
        app.connect(location);
    }

    @Test
    void testGetCountriesContinent()
    {
        int outputSize = app.getCountriesInContinent("Asia","5").size();
        assertEquals(5,outputSize);
    }

    @Test
    void testGetCountriesRegion()
    {
        int outputSize = app.getCountriesInRegion("Caribbean","5").size();
        assertEquals(5,outputSize);
    }

    @Test
    void testGetCities()
    {
        int outputSize = app.getCities("5").size();
        assertEquals(5,outputSize);
    }

    @Test
    void testGetCitiesNull()
    {
        int outputSize = app.getCitiesContinent("I am groot","5").size();
        assertEquals(0,outputSize);
    }

    @Test
    void testGetCitiesError()
    {
        app.disconnect();
        assertNull(app.getCities("5"));
        app.connect(location);
    }

    @Test
    void testGetCitiesContinent()
    {
        int outputSize = app.getCitiesContinent("Asia", "5").size();
        assertEquals(5,outputSize);
    }

    @Test
    void testGetCitiesRegion()
    {
        int outputSize = app.getCitiesRegion("Caribbean", "5").size();
        assertEquals(5,outputSize);
    }

    @Test
    void testGetCitiesDistrict()
    {
        int outputSize = app.getCitiesDistrict("Buenos Aires","5").size();
        assertEquals(5,outputSize);
    }

    @Test
    void testGetCitiesCountry()
    {
        int outputSize = app.getCitiesCountry("Cuba","5").size();
        assertEquals(5,outputSize);
    }

    @Test
    void testGetCapitals()
    {
        int outputSize = app.getCapitals("5").size();
        assertEquals(5,outputSize);
    }

    @Test
    void testGetCapitalsContinent()
    {
        int outputSize = app.getCapitalsContinent("Asia","5").size();
        assertEquals(5,outputSize);
    }

    @Test
    void testGetCapitalsRegion()
    {
        ArrayList<City> capitals = app.getCapitalsRegion("Caribbean", "5");
        int outputSize = capitals.size();
        assertEquals(5,outputSize);
    }

    @Test
    void testGetCountriesPopulationReport()
    {
        int outputSize = app.getCountriesPopulationReport().size();
        assertTrue(outputSize>20);
    }

    @Test
    void testGetContinentsPopulationReport()
    {
        int outputSize = app.getContinentsPopulationReport().size();
        assertTrue(outputSize>5);
    }
}