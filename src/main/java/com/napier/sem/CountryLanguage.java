package com.napier.sem;

/**
 * CountryLanguage class
 *
 * Class containing all the variables
 * for storing information from the
 * countryLanguage table from the
 * World SQL database
 */


public class CountryLanguage
{
    /**
     * Language spoken in the country
     */
    public String language;

    /**
     * Number of people that speak the language
     */
    public long numberOfPeople;

    /**
     * Percentage of world population that speak the language
     */
    public double percentageOfWorld;
}