package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppIntegrationTest {
    static App app;

    static String location = "localhost:33060";

    @BeforeAll
    static void init() {
        app = new App();
        app.connect(location);
    }

    @Test
    void testGetCity() {
        City aCity = app.getCity(1);
        // Expected Results
        assertEquals(aCity.ID, 1);
        assertEquals(aCity.Name, "Kabul");
        assertEquals(aCity.CountryCode, "AFG");
        assertEquals(aCity.District, "Kabol");
        assertEquals(aCity.Population, 1780000);
    }

    @Test
    void testGetCountries() {
        int outputSize = app.getCountries("5").size();
        assertEquals(5, outputSize);
    }

    @Test
    void testGetCountriesNull() {
        int outputSize = app.getCountriesInContinent("I am groot", "-1").size();
        assertEquals(0, outputSize);
    }

    @Test
    void testGetCountriesError() {
        app.disconnect();
        assertNull(app.getCountries("5"));
        app.connect(location);
    }

    @Test
    void testGetCountriesContinent() {
        int outputSize = app.getCountriesInContinent("Asia", "5").size();
        assertEquals(5, outputSize);
    }

    @Test
    void testGetCountriesRegion() {
        int outputSize = app.getCountriesInRegion("Caribbean", "5").size();
        assertEquals(5, outputSize);
    }

    @Test
    void testGetCities() {
        int outputSize = app.getCities("5").size();
        assertEquals(5, outputSize);
    }

    @Test
    void testGetCitiesNull() {
        int outputSize = app.getCitiesContinent("I am groot", "5").size();
        assertEquals(0, outputSize);
    }

    @Test
    void testGetCitiesError() {
        app.disconnect();
        assertNull(app.getCities("5"));
        app.connect(location);
    }

    @Test
    void testGetCitiesContinent() {
        int outputSize = app.getCitiesContinent("Asia", "5").size();
        assertEquals(5, outputSize);
    }

    @Test
    void testGetCitiesRegion() {
        int outputSize = app.getCitiesRegion("Caribbean", "5").size();
        assertEquals(5, outputSize);
    }

    @Test
    void testGetCitiesDistrict() {
        int outputSize = app.getCitiesDistrict("Buenos Aires", "5").size();
        assertEquals(5, outputSize);
    }

    @Test
    void testGetCitiesCountry() {
        int outputSize = app.getCitiesCountry("Cuba", "5").size();
        assertEquals(5, outputSize);
    }

    @Test
    void testGetCapitals() {
        int outputSize = app.getCapitals("5").size();
        assertEquals(5, outputSize);
    }

    @Test
    void testGetCapitalsContinent() {
        int outputSize = app.getCapitalsContinent("Asia", "5").size();
        assertEquals(5, outputSize);
    }

    @Test
    void testGetCapitalsRegion() {
        ArrayList<City> capitals = app.getCapitalsRegion("Caribbean", "5");
        int outputSize = capitals.size();
        assertEquals(5, outputSize);
    }

    @Test
    void testGetCountriesPopulationReport() {
        int outputSize = app.getCountriesPopulationReport().size();
        assertTrue(outputSize > 20);
    }

    @Test
    void testGetContinentsPopulationReport() {
        int outputSize = app.getContinentsPopulationReport().size();
        assertTrue(outputSize > 5);
    }

    @Test
    void testGetRegionsPopulationReport() {
        int outputSize = app.getRegionsPopulationReport().size();
        assertTrue(outputSize > 5);
    }

    @Test
    void testWorldPopulation() {
        ArrayList<PopulationInfo> world = app.getWorldPopulation();
        assertEquals(world.get(0).name, "World");
        assertEquals(world.get(0).population, 6078749450L);
    }

    @Test
    void testContinentPopulation() {
        ArrayList<PopulationInfo> continent = app.getContinentPopulation("Africa");
        assertEquals(continent.get(0).name, "Africa");
        assertEquals(continent.get(0).population, 784475000L);
    }

    @Test
    void testRegionPopulation() {
        ArrayList<PopulationInfo> region = app.getRegionPopulation("North America");
        assertEquals(region.get(0).name, "North America");
        assertEquals(region.get(0).population, 309632000L);
    }

    @Test
    void testCountryPopulation() {
        ArrayList<PopulationInfo> country = app.getCountryPopulation("United Kingdom");
        assertEquals(country.get(0).name, "United Kingdom");
        assertEquals(country.get(0).population, 59623400L);
    }

    @Test
    void testDistrictPopulation()
    {
        ArrayList<PopulationInfo> district = app.getDistrictPopulation("Scotland");
        assertEquals(district.get(0).name,"Scotland");
        assertEquals(district.get(0).population, 1429620L);
    }

    @Test
    void testCityPopulation()
    {
        ArrayList<City> cities = app.getCityPopulation("Springfield");
        int outputSize = cities.size();
        assertEquals(3,outputSize);
    }

    @Test
    void testLanguageReport()
    {
        ArrayList<CountryLanguage> languages = app.getLanguageReport();
        int outputSize = languages.size();
        // Check that there are only 5 results
        assertEquals(5 ,outputSize);
        // Check that the most spoken language is Chinese
        String langName = languages.get(0).language;
        long langPeople = languages.get(0).numberOfPeople;
        double langWorldPercent = languages.get(0).percentageOfWorld;
        assertEquals(langName, "Chinese");
        assertEquals(langPeople, 1191843539L);
        assertEquals(langWorldPercent, 19.60672378540039);
    }
}