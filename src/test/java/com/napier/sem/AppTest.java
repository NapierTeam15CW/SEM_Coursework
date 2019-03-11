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

    /**
     * Method tests the displayCities method
     * by using a null value instead of a
     * list
     */
    @Test
    void displayCitiesNull()
    {
        // Invoke displayCities method
        // with null value
        app.displayCities(null);
    }

    /**
     * Method tests the displayCities method
     * by using a city list with no entries
     */
    @Test
    void displayCitiesEmptyList()
    {
        // Creating city list
        ArrayList<City> cities = new ArrayList<>();

        // Invoking displayCity method with no
        // values added to list
        app.displayCities(cities);
    }

    /**
     * Method tests the displayCities method
     * by using a city list that only has
     * a null entry
     */
    @Test
    void displayCitiesWithNullCity()
    {
        // Creating new city list
        ArrayList<City> cities = new ArrayList<>();

        // Adding null value to list
        cities.add(null);

        // Invoking displayCities method
        // with null value added to city
        // list
        app.displayCities(cities);
    }

    /**
     * Method tests the displayCities method
     * by using a list of cities with
     * test data
     */
    @Test
    void displayCitiesWithValues()
    {
        // Creating new city list
        ArrayList<City> cities = new ArrayList<>();

        // Creating 4 test cities
        City testCity1 = new City();
        City testCity2 = new City();
        City testCity3 = new City();
        City testCity4 = new City();

        // Adding values to each test city (Spot the games dev student)
        testCity1.Name = "Los Santos";
        testCity1.District = "San Andreas";
        testCity1.CountryName = "North America";
        testCity1.Population = 4100300;

        testCity2.Name = "City 17";
        testCity2.District = "Eastern Europe";
        testCity2.CountryName = "Bulgaria";
        testCity2.Population = 333333;

        testCity3.Name = "Racoon City"; /** YES XD **/
        testCity3.District = "Missouri";
        testCity3.CountryName = "North America";
        testCity3.Population = 1234321;

        testCity4.Name = "Midgar";
        testCity4.District = "Eastern Europe";
        testCity4.CountryName = "Bulgaria";
        testCity4.Population = 541234;

        // Adding test cities to list
        cities.add(testCity1);
        cities.add(testCity2);
        cities.add(testCity3);
        cities.add(testCity4);

        // Invoking displayCities method with city list
        app.displayCities(cities);
    }

    /**
     * Method tests the displayLanguages method
     * by using a null value
     */
    @Test
    void displayLanguagesNull()
    {
        app.displayLanguages(null);
    }

    /**
     * Method tests the displayLanguages method by using
     * a language list with no entries
     */
    @Test
    void displayLanguagesEmptyList()
    {
        ArrayList<CountryLanguage> languages = new ArrayList<>();

        app.displayLanguages(languages);
    }

    /**
     * Method tests the displayLanguages method
     * by using a language list that only has
     * one null entry
     */
    @Test
    void displayLanguagesWithNullLanguage()
    {
        ArrayList<CountryLanguage> languages = new ArrayList<>();

        languages.add(null);

        app.displayLanguages(languages);
    }

    /**
     * Method tests the displayLanguages method
     * by using a list of languages with test data
     */
    @Test
    void displayLanguagesWithValues()
    {
        ArrayList<CountryLanguage> languages = new ArrayList<>();

        CountryLanguage testLanguage1 = new CountryLanguage();
        CountryLanguage testLanguage2 = new CountryLanguage();
        CountryLanguage testLanguage3 = new CountryLanguage();
        CountryLanguage testLanguage4 = new CountryLanguage();
        CountryLanguage testLanguage5 = new CountryLanguage();

        testLanguage1.code = "123";
        testLanguage1.language = "English";
        testLanguage1.isOfficial = true;
        testLanguage1.percentage = 0.9f;

        testLanguage2.code = "456";
        testLanguage2.language = "Spanish";
        testLanguage2.isOfficial = false;
        testLanguage2.percentage = 0.89f;

        testLanguage3.code = "789";
        testLanguage3.language = "Madarin";
        testLanguage3.isOfficial = true;
        testLanguage3.percentage = 0.5f;

        testLanguage4.code = "321";
        testLanguage4.language = "Yiddish";
        testLanguage4.isOfficial = false;
        testLanguage4.percentage = 0.6f;

        testLanguage5.code = "654";
        testLanguage5.language = "Gaelic";
        testLanguage5.isOfficial = false;
        testLanguage5.percentage = 0.1f;

        languages.add(testLanguage1);
        languages.add(testLanguage2);
        languages.add(testLanguage3);
        languages.add(testLanguage4);
        languages.add(testLanguage5);

        app.displayLanguages(languages);
    }



}
