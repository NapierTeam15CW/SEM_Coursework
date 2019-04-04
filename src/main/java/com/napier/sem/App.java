package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;

/**
 * App class
 *
 * Class containing functions to produce reports
 */
public class App
{
    /**
     * Main method for app
     */
    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        // Connect to database
        if (args.length < 1)
        {
            a.connect("localhost:3306");
        }
        else
        {
            a.connect(args[0]);
        }

        // Get city
        City kabul = a.getCity(1);
        // Display results
        a.displayCity(kabul);

//        // Get Countries
//        ArrayList<Country> countries = a.getCountriesInRegion("Eastern Asia",5);
//
//        // Display countries
//        a.displayCountries(countries);

        // Get Cities by Region - Limited to top 5
        ArrayList<City> cities1 = a.getCitiesRegion("Eastern Asia",5);
        // Display countries
        a.displayCities(cities1);
        System.out.println();

        // Get Cities by District - Limited to top 3
        ArrayList<City> cities2 = a.getCitiesDistrict("Inner Mongolia",3);
        // Display countries
        a.displayCities(cities2);
        System.out.println();

        // Get Cities by Country - Limited to top 15
        ArrayList<City> cities3 = a.getCitiesCountry("United Kingdom",15);
        // Display countries
        a.displayCities(cities3);
        System.out.println();

        // Get Cities by Continent - limited to top 10
        ArrayList<City> cities4 = a.getCitiesContinent("Africa",10);
        // Display countries
        a.displayCities(cities4);
        System.out.println();

        // Get Cities (all) - Limited to top 5
        ArrayList<City> cities5 = a.getCities("",5);
        // Display countries
        a.displayCities(cities5);
        System.out.println();

        // Disconnect from database
        a.disconnect();
    }

    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect(String location)
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://" + location + "/world?allowPublicKeyRetrieval=true&useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }

    /**
     * Gets a city from the database with the ID given
     * @param ID the ID of the city to be fetched
     * @return city object, based on the city
     */
    public City getCity(int ID) {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT * "
                            + "FROM city "
                            + "WHERE ID = " + ID;
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Return new city if valid.
            // Check one is returned
            if (rset.next())
            {
                City city = new City();
                city.ID = rset.getInt("ID");
                city.Name = rset.getString("Name");
                city.CountryCode = rset.getString("CountryCode");
                city.District = rset.getString("District");
                city.Population = rset.getInt("Population");
                return city;
            }
            else
                return null;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    /**
     * Method returns information stored by a city
     * object, based on the city object provided to the
     * method
     * @param city the city to be displayed
     */
    public void displayCity(City city) {
        if(city != null) {
            System.out.println("ID: "+city.ID+"\n"+
                               "Name: "+city.Name+"\n"+
                               "CountryCode: "+city.CountryCode+"\n"+
                               "District: "+city.District+"\n"+
                               "Population: "+city.Population+"\n");
        }
    }

    /**
     * Displays the code, name, continent, region,
     * population and capital city of all countries given
     * @param countries the countries given
     */
    public void displayCountries(ArrayList<Country> countries) {
        // Check countries is not null
        if(countries == null)
        {
            System.out.println("No countries");
            return;
        }

        // Print header
        String header_format = "%-4s %-15s %-15s %-15s %-10s %-10s";
        String headers = String.format(header_format,"Code","Name","Continent","Region","Population","Capital City");
        System.out.println(headers);

        // Loop through countries and print
        for(Country country : countries) {
            // Check country is not null
            if(country == null)
                continue;

            // If the string is bigger than the column,
            // clip the string.
            String name = (country.name.length()>15)?country.name.substring(0,15):country.name;
            String continent = (country.continent.length()>15)?country.continent.substring(0,15):country.continent;
            String region = (country.region.length()>15)?country.region.substring(0,15):country.region;;
            String capital = (country.capital_name.length()>15)?country.capital_name.substring(0,10):country.capital_name;

            // Print country
            String record_format = "%-4s %-15s %-15s %-15s %-10d %-10s";
            String country_string =
                    String.format(record_format, country.code, name, continent, region, country.population, capital);
            System.out.println(country_string);
        }
    }

    /**
     * Gets all countries organised by largest
     * population to smallest
     * @return List of countries organised from largest to smallest population
     */
    public ArrayList<Country> getAllCountries() {
        return getCountries("");
    }

    /**
     * Gets the top most populous countries.
     * The number of countries returned is given by the user
     * @param limit the number of countries to be returned
     * @return List of countries organised from largest to smallest population
     */
    public ArrayList<Country> getCountries(int limit) {
        return getCountries("", limit);
    }

    /**
     * Gets all countries in a continent organised
     * from largest population to smallest
     * @param continent the continent the countries belong to
     * @return List of countries in a continent
     */
    public ArrayList<Country> getAllCountriesInContinent(String continent) {
        return getCountries("WHERE country.Continent = '"+continent+"'\n");
    }

    /**
     * Gets a number of countries in a continent organised
     * from largest population to smallest
     * The number of countries returned is specified by the user.
     * @param continent the continent the countries belong to
     * @param limit the number of countries to be returned
     * @return List of top most populous countries in a continent
     */
    public ArrayList<Country> getCountriesInContinent(String continent, int limit) {
        return getCountries("WHERE country.Continent = '"+continent+"'\n", limit);
    }

    /**
     * Gets all countries in a region organised
     * from largest population to smallest
     * @param region the region the countries are in
     * @return List of countries in a region
     */
    public ArrayList<Country> getAllCountriesInRegion(String region) {
        return getCountries("WHERE country.Region = '"+region+"'\n");
    }

    /**
     * Gets top countries most populous countries in a region
     * organised from largest to smallest. The number of
     * countries returned is given by "limit".
     * The number of countries returned is limited by "limit"
     * @param region the region the countries are in
     * @param limit the number of results to return
     * @return List of top most populous countries in a region
     */
    public ArrayList<Country> getCountriesInRegion(String region, int limit) {
        return getCountries("WHERE country.Region = '"+region+"'\n", limit);
    }

    /**
     * Gets some or all countries within SQL condition
     *
     * @param condition the SQL condition
     * @param limit the number of results to return. If less than zero, returns all results
     * @return List of countries
     */
    public ArrayList<Country> getCountries(String condition, int limit) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String limitCondition = (limit>0)?"LIMIT "+limit+"\n":""; // Limit results if limit is positive and nonzero
            String strSelect =
                    "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name\n"+
                            "FROM country\n"+
                            "JOIN city ON country.Capital = city.ID\n"+
                            condition+
                            "ORDER BY country.population DESC\n"+
                            limitCondition;
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract Country information
            ArrayList<Country> countries = new ArrayList<>();
            while (rset.next())
            {
                Country country = new Country();
                country.code = rset.getString("country.Code");
                country.name = rset.getString("country.Name");
                country.continent = rset.getString("country.Continent");
                country.region = rset.getString("country.Region");
                country.population = rset.getInt("country.Population");
                country.capital_name = rset.getString("city.Name");
                countries.add(country);
            }
            return countries;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }
    /**
     * Gets all countries within SQL condition
     *
     * @param condition the SQL condition
     * @return List of countries
     */
    public ArrayList<Country> getCountries(String condition) {
        return getCountries(condition, -1);
    }


    /**
     * Method searches through the database and returns a list
     * of all cities that are in the world
     *
     * Results obtained can also be limited
     */
    public ArrayList<City> getCities(String searchCondition, int limitResult)
    {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.Name, country.Name, city.District, city.Population "
                            + "FROM country "
                            + "JOIN city ON country.Code = city.CountryCode "
                            + searchCondition
                            + "ORDER BY city.population DESC ";
            if(limitResult > 0)
            {
                strSelect = strSelect + "LIMIT " + limitResult + " ";
            }
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract Country information
            ArrayList<City> cities = new ArrayList<>();
            while (rset.next())
            {
                City city = new City();
                city.Name = rset.getString("city.Name");
                city.CountryName = rset.getString("country.Name");
                city.District = rset.getString("city.District");
                city.Population = rset.getInt("city.Population");
                cities.add(city);
            }
            return cities;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get list of cities and details");
            return null;
        }
    }

    /**
     * Method searches through the database and returns a list
     * of all cities that are in a specific continent
     *
     * Results obtained can also be limited
     */
    public ArrayList<City> getCitiesContinent(String aContinent, int aLimit)
    {
        String searchCondition = "WHERE country.Continent = '" + aContinent + "'";

        return getCities(searchCondition, aLimit);
    }

    /**
     * Method searches through the database and returns a list
     * of all cities that are in a specific region
     *
     * Results obtained can also be limited
     */
    public ArrayList<City> getCitiesRegion(String aRegion, int aLimit)
    {
        String searchCondition = "WHERE country.Region = '" + aRegion + "'";

        return getCities(searchCondition, aLimit);
    }

    /**
     * Method searches through the database and returns a list
     * of all cities that are in a specific country
     *
     * Results obtained can also be limited
     */
    public ArrayList<City> getCitiesCountry(String aCountry, int aLimit)
    {
        String searchCondition = "WHERE country.Name = '" + aCountry + "'";

        return getCities(searchCondition, aLimit);
    }

    /**
     * Method searches through the database and returns a list
     * of all cities that are in a specific district
     *
     * Results obtained can also be limited
     */
    public ArrayList<City> getCitiesDistrict(String aDistrict, int aLimit)
    {
        String searchCondition = "WHERE city.District = '" + aDistrict + "'";

        return getCities(searchCondition, aLimit);
    }

    /**
     * Displays the city name, country where city belongs, district
     * of the country where the city is found and the
     * population of the city.
     */
    public void displayCities(ArrayList<City> cities)
    {
        // Check cities list is populated with valies
        if(cities == null)
        {
            System.out.println("No cities found");
            return;
        }

        //Length limits of values from SQL database
        int cityNameLength = 35;
        int countryNameLength = 38;
        int districtNameLength = 20;
        int populationNumberLength = 11;

        // Print header
        String headerFormat = "%-"
                + cityNameLength + "s %-"
                + countryNameLength + "s %-"
                + districtNameLength + "s %-"
                + populationNumberLength + "s";
        String headers = String.format(headerFormat, "City Name", "Country", "District", "Population");
        System.out.println(headers);

        // Print details of each city in list
        for(City city : cities)
        {

            // Check city in the list is not null
            if(city == null)
                continue;

            // Check if strings are longer than
            // the column and trim if they are
            String name = (city.Name.length() > cityNameLength) ? city.Name.substring(0, cityNameLength) : city.Name;
            String country = (city.CountryName.length() > countryNameLength) ? city.CountryName.substring(0, countryNameLength) : city.CountryName;
            String district = (city.District.length() > districtNameLength) ? city.District.substring(0 , districtNameLength) : city.District;

            // Print city details
            String recordFormat = "%-"
                    + cityNameLength + "s %-"
                    + countryNameLength + "s %-"
                    + districtNameLength + "s %-"
                    + populationNumberLength + "s";
            String cityString =
                    String.format(recordFormat, name, country, district, city.Population);
            System.out.println(cityString);
        }
    }


    /**
     * Returns details for a given country
     *
     */

    public Country getCountryDetails(String aCountry)
    {
        try
        {
            Country country = new Country();
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT country.Code, country.Name "
                            + "FROM country "
                            + "WHERE country.Name = '" + aCountry + "' ";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract Country information

            while (rset.next())
            {
                country.code = rset.getString("country.Code");
                country.name = rset.getString("country.Name");

            }
            return country;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }


    /**
     * Returns a list of languages for a given country
     */

    public ArrayList<CountryLanguage> getLanguage(Country aCountry)
    {

        String countryName = aCountry.name;
        try
        {
            Statement s = con.createStatement();

            String strSelect =
                    "SELECT country.Name, countryLanguage.Language, countryLanguage.percentage"
                            + "FROM country JOIN countryLanguage ON (country.Code = countryLanguage.CountryCode)"
                            + "WHERE country.Name = '" + aCountry.name + "' "
                            + "AND countryLanguage.Language IN ('Chinese', 'English', 'Hindi', 'Spanish', 'Arabic')"
                            + "ORDER BY countryLanguage.Language ";


            ResultSet r = s.executeQuery(strSelect);

            ArrayList<CountryLanguage> languages = new ArrayList<>();
            while(r.next())
            {
                CountryLanguage language = new CountryLanguage();
                language.code = r.getString("language.Code");
                language.isOfficial = r.getBoolean("language.IsOfficial");
                language.language = r.getString("language.Name");
                language.percentage = r.getFloat("language.Percentage");
                languages.add(language);
            }

            return languages;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get list of languages");
            return null;
        }
    }
//
//    public ArrayList<CountryLanguage> getLanguagesContinent(String continent, int limit)
//    {
//        String condition = "WHERE country.Continent = '" + continent + "'";
//
//        return getLanguage(condition, limit);
//    }
//
//    public ArrayList<CountryLanguage> getLanguagesRegion(String region, int limit)
//    {
//        String condition = "WHERE country.Regon = '" + region + "'";
//
//        return getLanguage(condition, limit);
//    }
//
//    public ArrayList<CountryLanguage> getLanguagesCountry(String country, int limit)
//    {
//        String condition = "WHERE country.Name = '" + country + "'";
//
//        return getLanguage(condition, limit);
//    }

    public void displayLanguages(ArrayList<CountryLanguage> languages)
    {
        if (languages == null)
        {
            System.out.println("No Languages found");
            return;
        }

        for(CountryLanguage l : languages)
        {
            if(l == null)
            {
                continue;
            }

            String id = l.code;
            String language = l.language;
            boolean isOfficial = l.isOfficial;
            float percentage = l.percentage;

            String languageString = id + " " +
                    language +  " " +
                    isOfficial +  " " +
                    percentage + "% ";

            System.out.println(languageString);
        }
    }
}