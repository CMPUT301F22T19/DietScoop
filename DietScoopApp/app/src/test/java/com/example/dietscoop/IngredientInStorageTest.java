package com.example.dietscoop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.dietscoop.Data.Ingredient.IngredientCategory;
import com.example.dietscoop.Data.Ingredient.IngredientInStorage;
import com.example.dietscoop.Data.Ingredient.IngredientUnit;
import com.example.dietscoop.Data.Ingredient.Location;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class IngredientInStorageTest {

    IngredientInStorage sampleIngredientSTR;

    public IngredientInStorage startSampleIngredientSTR() {
        return new IngredientInStorage("Chicken", IngredientUnit.kg,
                5.0, 2022, 4, 24, Location.Freezer, IngredientCategory.Meat);
    }

    @Test
    public void testGetLocation() {
        //Initializing object:
        sampleIngredientSTR = startSampleIngredientSTR();

        //Asserting the validity of Object:
        assertEquals(sampleIngredientSTR.getLocation(), Location.Freezer);
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
        assertEquals(sampleIngredientSTR.getCategory(), IngredientCategory.Meat);
    }

    @Test
    public void testSetLocation() {
        //Initializing object:
        sampleIngredientSTR = startSampleIngredientSTR();

        sampleIngredientSTR.setLocation(Location.Fridge);

        //Asserting the validity of Object:
        assertEquals(sampleIngredientSTR.getLocation(), Location.Fridge);
    }

    @Test
    public void testSetCategory() {
        //Initializing object:
        sampleIngredientSTR = startSampleIngredientSTR();

        sampleIngredientSTR.setCategory(IngredientCategory.Fruit);

        //Asserting the validity of Object:
        assertEquals(sampleIngredientSTR.getCategory(), IngredientCategory.Fruit);
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
