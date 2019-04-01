package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppIntegrationTest
{
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
        app.connect("localhost:33060");
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
}