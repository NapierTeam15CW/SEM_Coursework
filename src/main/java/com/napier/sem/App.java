package com.napier.sem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.ArrayList;

/**
 * App class
 *
 * Class containing functions to produce reports
 */
@SpringBootApplication
@RestController
public class App
{
    /**
     * Main method for app
     */
    public static void main(String[] args)
    {
        // Connect to database
        if (args.length < 1)
        {
            connect("localhost:33060");
        }
        else
        {
            connect(args[0]);
        }

        SpringApplication.run(App.class, args);
    }

    /**
     * Connection to MySQL database.
     */
    private static Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public static void connect(String location)
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
    public static void disconnect()
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
            String region = (country.region.length()>15)?country.region.substring(0,15):country.region;
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
        return getCountries("","-1");
    }

    /**
     * Gets the top most populous countries.
     * The number of countries returned is given by the user
     * @param limit the number of countries to be returned
     * @return List of countries organised from largest to smallest population
     */
    @RequestMapping("countries")
    public ArrayList<Country> getCountries(@RequestParam(value="limit", defaultValue="-1") String limit) {
        return getCountries("", limit);
    }

    /**
     * Gets all countries in a continent organised
     * from largest population to smallest
     * @param continent the continent the countries belong to
     * @return List of countries in a continent
     */
    public ArrayList<Country> getAllCountriesInContinent(String continent) {
        return getCountries("WHERE country.Continent = '"+continent+"'\n","-1");
    }

    /**
     * Gets a number of countries in a continent organised
     * from largest population to smallest
     * The number of countries returned is specified by the user.
     * @param continent the continent the countries belong to
     * @param limit the number of countries to be returned
     * @return List of top most populous countries in a continent
     */
    @RequestMapping("countries_continent")
    public ArrayList<Country> getCountriesInContinent(@RequestParam(value="continent") String continent, @RequestParam(value = "limit", defaultValue="-1") String limit) {
        return getCountries("WHERE country.Continent = '"+continent+"'\n", limit);
    }

    /**
     * Gets all countries in a region organised
     * from largest population to smallest
     * @param region the region the countries are in
     * @return List of countries in a region
     */
    public ArrayList<Country> getAllCountriesInRegion(String region) {
        return getCountries("WHERE country.Region = '"+region+"'\n","-1");
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
    @RequestMapping("countries_region")
    public ArrayList<Country> getCountriesInRegion(@RequestParam(value="region") String region, @RequestParam(value="limit",defaultValue = "-1") String limit) {
        return getCountries("WHERE country.Region = '"+region+"'\n", limit);
    }

    /**
     * Gets some or all countries within SQL condition
     *
     * @param condition the SQL condition
     * @param limit the number of results to return. If less than zero, returns all results
     * @return List of countries
     */
    public ArrayList<Country> getCountries(String condition, String limit) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String limitCondition = (!limit.equals("-1"))?"LIMIT "+limit+"\n":""; // Limit results if limit isn't "-1"
            String strSelect =
                    "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name, city.ID\n"+
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
                country.capital_code = rset.getInt("city.ID");
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
     * Returns a list of cities
     * List can be limited
     * @param limitResult the size of the list returned
     * @return
     */
    @RequestMapping("cities")
    public ArrayList<City> getCities(@RequestParam(value="limit",defaultValue = "-1") String limitResult)
    {
        return getCities("",limitResult);
    }

    private String capitalCondition = "city.ID = country.Capital";

    /**
     * Returns a list of capital cities ordered by population.
     * List can be limited
     * @param limitResult the size of the list returned
     * @return a list of capital cities
     */
    @RequestMapping("capitals")
    public ArrayList<City> getCapitals(@RequestParam(value="limit", defaultValue="-1") String limitResult)
    {
        return getCities("WHERE "+capitalCondition+"\n",limitResult);
    }

    /**
     * Returns a list of capital cities filtered by continent
     * ordered by population (largest to smallest)
     * @param continent the continent the capital cities are from
     * @param limitResult the number of capital cities returned
     * @return a list of capital cities from the same continent
     */
    @RequestMapping("capitals_continent")
    public ArrayList<City> getCapitalsContinent(@RequestParam(value="continent") String continent, @RequestParam(value="limit", defaultValue = "-1") String limitResult)
    {
        return getCities("WHERE "+capitalCondition+"\n"+" AND country.continent = '"+continent+"'\n",limitResult);
    }

    /**
     * Returns a list of capital cities filtered by region
     * orderd by population (largest to smallest)
     * @param region the region the capital cities are from
     * @param limitResult the number of capital cities returned
     * @return a list of capital cities in the same region
     */
    @RequestMapping("capitals_region")
    public ArrayList<City> getCapitalsRegion(@RequestParam(value="region") String region, @RequestParam(value="limit",defaultValue="-1") String limitResult)
    {
        return getCities("WHERE "+capitalCondition+"\n"+"AND country.region = '"+region+"'\n",limitResult);
    }

    @RequestMapping("countries_population_report")
    public ArrayList<PopulationReport> getCountriesPopulationReport()
    {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT Name, Population, cityPop.PopInCities AS PopInCities\n"+
                            "FROM country\n"+
                            "LEFT JOIN (SELECT CountryCode, SUM(Population) AS PopInCities FROM city GROUP BY CountryCode) cityPop ON country.Code = cityPop.CountryCode\n"+
                    "ORDER BY Name";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            ArrayList<PopulationReport> reports = new ArrayList<>();
            while (rset.next())
            {
                PopulationReport pr = new PopulationReport();
                pr.Name = rset.getString("Name");
                pr.Population = rset.getInt("Population");
                pr.InCities = rset.getInt("PopInCities");
                pr.NotInCities = pr.Population - pr.InCities;
                pr.InCitiesPercentage = ((double) pr.InCities / (double) pr.Population)*100;
                pr.InCitiesPercentage = ((double) Math.round(pr.InCitiesPercentage*100))/100;
                pr.NotInCitiesPercentage = ((double) pr.NotInCities /(double) pr.Population)*100;
                pr.NotInCitiesPercentage = ((double) Math.round(pr.NotInCitiesPercentage*100))/100;
                reports.add(pr);
            }
            return reports;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    /**
     * Method searches through the database and returns a list
     * of all cities that are in the world
     *
     * Results obtained can also be limited
     */
    public ArrayList<City> getCities(String searchCondition, String limitResult)
    {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.ID, city.Name, country.Name, country.Code, city.District, city.Population\n"
                            + "FROM country\n"
                            + "JOIN city ON country.Code = city.CountryCode\n"
                            + searchCondition
                            + "ORDER BY city.population DESC\n";
            if(!limitResult.equals("-1"))
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
                city.ID = rset.getInt("city.ID");
                city.Name = rset.getString("city.Name");
                city.CountryCode = rset.getString("country.Code");
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
    @RequestMapping("cities_continent")
    public ArrayList<City> getCitiesContinent(@RequestParam(value="continent") String aContinent, @RequestParam(value="limit",defaultValue = "-1") String aLimit)
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
    @RequestMapping("cities_region")
    public ArrayList<City> getCitiesRegion(@RequestParam(value="region") String aRegion, @RequestParam(value="limit", defaultValue = "-1") String aLimit)
    {
        String searchCondition = "WHERE country.Region = '" + aRegion + "'\n";

        return getCities(searchCondition, aLimit);
    }

    /**
     * Method searches through the database and returns a list
     * of all cities that are in a specific country
     *
     * Results obtained can also be limited
     */
    @RequestMapping("cities_country")
    public ArrayList<City> getCitiesCountry(@RequestParam(value="country")String aCountry, @RequestParam(value="limit", defaultValue = "-1") String aLimit)
    {
        String searchCondition = "WHERE country.Name = '" + aCountry + "'\n";

        return getCities(searchCondition, aLimit);
    }

    /**
     * Method searches through the database and returns a list
     * of all cities that are in a specific district
     *
     * Results obtained can also be limited
     */
    @RequestMapping("cities_district")
    public ArrayList<City> getCitiesDistrict(@RequestParam(value="district") String aDistrict, @RequestParam(value="limit",defaultValue = "-1") String aLimit)
    {
        String searchCondition = "WHERE city.District = '" + aDistrict + "'\n";

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