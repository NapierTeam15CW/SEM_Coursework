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
     * Country code
     */
    public String code;

    /**
     * Language spoken in the country
     */
    public String language;

    /**
     * Determines if the language is official for the country
     */
    public boolean isOfficial;

    /**
     * Percentage of people that speak the language
     */
    public float percentage;
}