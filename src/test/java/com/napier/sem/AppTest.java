package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

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
}
