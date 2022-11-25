package com.example.dietscoop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.dietscoop.Data.Ingredient.Ingredient;
import com.example.dietscoop.Data.Ingredient.IngredientCategory;
import com.example.dietscoop.Data.Ingredient.IngredientInRecipe;
import com.example.dietscoop.Data.Ingredient.IngredientInStorage;
import com.example.dietscoop.Data.Ingredient.IngredientUnit;
import com.example.dietscoop.Data.Ingredient.Location;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.time.LocalDate;

public class IngredientTest {

    public IngredientInRecipe sampleIngredientInRecipe() {
        IngredientInRecipe
            test = new IngredientInRecipe("Chicken", IngredientUnit.kg, 5.0, IngredientCategory.Meat);
        return test;
    }

    public IngredientInStorage sampleIngredientInStorage() {
        IngredientInStorage
                test = new IngredientInStorage("Chicken", IngredientUnit.kg, 5.0, 2022, 12, 11, Location.Pantry, IngredientCategory.Meat);
        return test;
    }

    @Test
    public void testGettersStorage() {
        //Testing Getters:

        IngredientInStorage sampleIngredient = sampleIngredientInStorage();
        try {
            assertEquals(sampleIngredient.getDescription(), "Chicken");
            assertEquals(sampleIngredient.getMeasurementUnit(), IngredientUnit.kg);
            assertEquals(sampleIngredient.getAmount(), 5.0);
            assertEquals(sampleIngredient.getCategory(), IngredientCategory.Meat);
            assertEquals((sampleIngredient.getCategoryName()), "Meat");
            assertEquals(sampleIngredient.getCountWithMeasurement(), "5.0 kg");
            assertEquals(sampleIngredient.getBestBeforeDate(), LocalDate.of(2022, 12, 11));
            assertEquals(sampleIngredient.getFormattedBestBefore(), "2022-12-11");
            assertEquals(sampleIngredient.getId(), null);
        } catch (Exception e) {
            System.err.println("Error loading ingredient");
        }

    }

    @Test
    public void testGettersRecipe() {
        //Testing Getters:
        IngredientInRecipe sampleIngredient = sampleIngredientInRecipe();
        try {
            assertEquals(sampleIngredient.getDescription(), "Chicken");
            assertEquals(sampleIngredient.getMeasurementUnit(), IngredientUnit.kg);
            assertEquals(sampleIngredient.getAmount(), 5.0);
            assertEquals(sampleIngredient.getCategory(), IngredientCategory.Meat);
            assertEquals((sampleIngredient.getCategoryName()), "Meat");
            assertEquals(sampleIngredient.getCountWithMeasurement(), "5.0 kg");
            assertEquals(sampleIngredient.getId(), null);
        } catch (Exception e) {
            System.err.println("Error loading ingredient");
        }

    }

    @Test
    public void testSettersStorage() {
        //Testing Setters:
        IngredientInStorage sampleIngredient = sampleIngredientInStorage();
        try {
            sampleIngredient.setDescription("canola oil");
            sampleIngredient.setMeasurementUnit(IngredientUnit.mL);
            sampleIngredient.setAmount(7.0);
            sampleIngredient.setCategory(IngredientCategory.Oils);
            sampleIngredient.setBestBeforeDate(2019, 11, 30);
            sampleIngredient.setId("1");


            assertEquals(sampleIngredient.getDescription(), "canola oil");
            assertEquals(sampleIngredient.getMeasurementUnit(), IngredientUnit.mL);
            assertEquals(sampleIngredient.getAmount(), 7.0);
            assertEquals(sampleIngredient.getCategory(), IngredientCategory.Oils);
            assertEquals((sampleIngredient.getCategoryName()), "Oils");
            assertEquals(sampleIngredient.getCountWithMeasurement(), "7.0 mL");
            assertEquals(sampleIngredient.getBestBeforeDate(), LocalDate.of(2019, 11, 30));
            assertEquals(sampleIngredient.getFormattedBestBefore(), "2019-11-30");
            assertEquals(sampleIngredient.getFormattedBestBefore(), "2019-11-30");
            assertEquals(sampleIngredient.getId(), "1");
        } catch (Exception e) {
            System.err.println("Error loading ingredient");
        }
    }

    @Test
    public void testSettersRecipe() {
        //Testing Setters:
        IngredientInRecipe sampleIngredient = sampleIngredientInRecipe();
        try {
            sampleIngredient.setDescription("canola oil");
            sampleIngredient.setMeasurementUnit(IngredientUnit.mL);
            sampleIngredient.setAmount(7.0);
            sampleIngredient.setCategory(IngredientCategory.Oils);
            sampleIngredient.setId("1");


            assertEquals(sampleIngredient.getDescription(), "canola oil");
            assertEquals(sampleIngredient.getMeasurementUnit(), IngredientUnit.mL);
            assertEquals(sampleIngredient.getAmount(), 7.0);
            assertEquals(sampleIngredient.getCategory(), IngredientCategory.Oils);
            assertEquals((sampleIngredient.getCategoryName()), "Oils");
            assertEquals(sampleIngredient.getCountWithMeasurement(), "7.0 mL");
            assertEquals(sampleIngredient.getId(), "1");
        } catch (Exception e) {
            System.err.println("Error loading ingredient");
        }
    }

    @Test
    public void testHasSameUnitType() {
        IngredientInStorage sampleIngredient = sampleIngredientInStorage();
        IngredientInStorage compareIngredient = sampleIngredientInStorage();
        assertTrue(sampleIngredient.hasSameUnitType(compareIngredient));

        compareIngredient.setMeasurementUnit(IngredientUnit.mg);
        assertTrue(sampleIngredient.hasSameUnitType(compareIngredient));

        compareIngredient.setMeasurementUnit(IngredientUnit.g);
        assertTrue(sampleIngredient.hasSameUnitType(compareIngredient));

        compareIngredient.setMeasurementUnit(IngredientUnit.mL);
        assertFalse(sampleIngredient.hasSameUnitType(compareIngredient));

        compareIngredient.setMeasurementUnit(IngredientUnit.lb);
        assertTrue(sampleIngredient.hasSameUnitType(compareIngredient));

        compareIngredient.setMeasurementUnit(IngredientUnit.oz);
        assertTrue(sampleIngredient.hasSameUnitType(compareIngredient));

        compareIngredient.setMeasurementUnit(IngredientUnit.cup);
        assertFalse(sampleIngredient.hasSameUnitType(compareIngredient));

        compareIngredient.setMeasurementUnit(IngredientUnit.L);
        assertFalse(sampleIngredient.hasSameUnitType(compareIngredient));

        compareIngredient.setMeasurementUnit(IngredientUnit.tbsp);
        assertFalse(sampleIngredient.hasSameUnitType(compareIngredient));

        compareIngredient.setMeasurementUnit(IngredientUnit.tsp);
        assertFalse(sampleIngredient.hasSameUnitType(compareIngredient));

    }

}
