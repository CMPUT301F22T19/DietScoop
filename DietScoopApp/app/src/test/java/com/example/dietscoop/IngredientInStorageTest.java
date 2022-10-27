package com.example.dietscoop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.Calendar;

public class IngredientInStorageTest {

    IngredientInStorage sampleIngredientSTR;

    public IngredientInStorage startSampleIngredientSTR() {
        return new IngredientInStorage("Chicken", "kg",
                5, 2022, 4, 24, Location.freezer, IngredientCategory.meat);
    }

    @Test
    public void testGetLocation() {
        //Initializing object:
        sampleIngredientSTR = startSampleIngredientSTR();

        //Asserting the validity of Object:
        assertEquals(sampleIngredientSTR.location, Location.freezer);
    }

    @Test
    public void getBestBeforeDate() {
        //Initializing objects:
        sampleIngredientSTR = startSampleIngredientSTR();
        Calendar calendarCheck = new Calendar.Builder().setDate(2022, 4, 24).build();

        //Asserting the validity of Object:
        assertEquals(sampleIngredientSTR.getBestBeforeDate(), calendarCheck);
    }

    @Test
    public void testGetCategory() {
        //Initializing object:
        sampleIngredientSTR = startSampleIngredientSTR();

        //Asserting the validity of Object:
        assertEquals(sampleIngredientSTR.category, IngredientCategory.meat);
    }

    @Test
    public void testSetLocation() {
        //Initializing object:
        sampleIngredientSTR = startSampleIngredientSTR();

        sampleIngredientSTR.setLocation(Location.fridge);

        //Asserting the validity of Object:
        assertEquals(sampleIngredientSTR.getLocation(), Location.fridge);
    }

    @Test
    public void testSetCategory() {
        //Initializing object:
        sampleIngredientSTR = startSampleIngredientSTR();

        sampleIngredientSTR.setCategory(IngredientCategory.fruit);

        //Asserting the validity of Object:
        assertEquals(sampleIngredientSTR.getCategory(), IngredientCategory.fruit);
    }

    @Test
    public void testSetBestBeforeDate() {
        //Initializing object:
        sampleIngredientSTR = startSampleIngredientSTR();

        Calendar newDate = new Calendar.Builder().setDate(2023, 10, 1).build();
        sampleIngredientSTR.setBestBeforeDate(2023, 10, 1);

        //Asserting the validity of Object:
        assertEquals(sampleIngredientSTR.getBestBeforeDate(), newDate);
    }



}
