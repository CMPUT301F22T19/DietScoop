package com.example.dietscoop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.dietscoop.Data.IngredientCategory;
import com.example.dietscoop.Data.IngredientInStorage;
import com.example.dietscoop.Data.Location;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

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
        assertEquals(sampleIngredientSTR.getLocation(), Location.freezer);
    }

    @Test
    public void getBestBeforeDate() {
        //Initializing objects:
        sampleIngredientSTR = startSampleIngredientSTR();
        LocalDate calendarCheck = LocalDate.of(2022, 4, 24);

        //Asserting the validity of Object:
        assertEquals(sampleIngredientSTR.getBestBeforeDate(), calendarCheck);
    }

    @Test
    public void testGetCategory() {
        //Initializing object:
        sampleIngredientSTR = startSampleIngredientSTR();

        //Asserting the validity of Object:
        assertEquals(sampleIngredientSTR.getCategory(), IngredientCategory.meat);
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

        LocalDate newDate = LocalDate.of(2023, 10, 1);
        sampleIngredientSTR.setBestBeforeDate(2023, 10, 1);

        //Asserting the validity of Object:
        assertEquals(sampleIngredientSTR.getBestBeforeDate(), newDate);
    }



}
