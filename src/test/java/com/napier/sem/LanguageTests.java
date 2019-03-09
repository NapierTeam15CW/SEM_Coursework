package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

class LanguageTests
{
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
    }

    @Test
    void displayLanguagesTestNull()
    {
        ArrayList<CountryLanguage> languages = new ArrayList<>();
    }

    @Test
    void displayLanguagesTestEmpty()
    {
        ArrayList<CountryLanguage> languages = new ArrayList<>();

    }

    @Test
    void displayLanguages()
    {

    }


}
