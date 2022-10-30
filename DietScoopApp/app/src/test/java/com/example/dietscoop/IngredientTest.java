package com.example.dietscoop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

public class IngredientTest {

    //Variables:
    Ingredient sampleIngredient;

    public Ingredient sampleIngredient() {
        sampleIngredient = new Ingredient("Chicken", "kg", 5, Category.meat);
        return sampleIngredient;
    }

    @Test
    public void testGetters() {
        //Testing Getters:
        sampleIngredient = sampleIngredient();
        try {
            assertEquals(sampleIngredient.measurementUnit, "kg");
            assertEquals(sampleIngredient.description, "Chicken");
            assertEquals(sampleIngredient.amount, 5);
        } catch (Exception e) {
            System.err.println("Error loading ingredient");
        }

    }

    @Test
    public void testSetters() {
        //Testing Setters:
        sampleIngredient = sampleIngredient();
        try {
            sampleIngredient.setAmount(3);
            sampleIngredient.setMeasurementUnit("mg");
            sampleIngredient.setDescription("potato");
            assertEquals(sampleIngredient.measurementUnit, "mg");
            assertEquals(sampleIngredient.description, "potato");
            assertEquals(sampleIngredient.amount, 3);
        } catch (Exception e) {
            System.err.println("Error setting ingredient properties");
        }
    }

}
