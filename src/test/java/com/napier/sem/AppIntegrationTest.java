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
        app.connect();
    }

    @Test
    void testGetCity()
    {
        City city = app.getCity(1);
        assertEquals(city.ID, 1);
        assertEquals(city.Name, "Kabul");
        assertEquals(city.CountryCode, "AFG");
    }
}