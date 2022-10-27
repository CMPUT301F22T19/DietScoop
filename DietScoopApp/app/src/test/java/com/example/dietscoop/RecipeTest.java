package com.example.dietscoop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class RecipeTest {
    /**
     * Generates a sample Recipe for testing methods.
     * @return ArrayList<Ingredients></Ingredients>
     */
    public ArrayList<Ingredient> generateSampleIngredients() {
        //Generating a sampleIngredients array list to contain the sample ingredients.
        ArrayList<Ingredient> sampleIngredients = new ArrayList<>();
        sampleIngredients.add(new Ingredient("Chicken", "kg", 5));
        sampleIngredients.add(new Ingredient("Beans", "kg", 10));
        sampleIngredients.add(new Ingredient("Rice", "mg", 1));
        return sampleIngredients;
    }

    //Here getSampleIngredients handles the retrieval of sample ingredient arraylist.
    public Recipe getSampleRecipe() {
        //Instantiating the test Recipe Object:
        return new Recipe(12, 5, "Tasty breakfast", RecipeCategory.breakfast,
                generateSampleIngredients(), "Frijoles Con Pollo");
    }

    @Test
    public void testGetDescription() {
        Recipe sampleRecipe = getSampleRecipe();
        assertEquals(sampleRecipe.getDescription(), "Frijoles Con Pollo");
    }

    @Test
    public void testAddIngredient() {
        Recipe sampleRecipe = getSampleRecipe();

        Ingredient sampleIngredient = new Ingredient("eggs", "kg", 4);

        sampleRecipe.addIngredient(sampleIngredient);
        assertTrue(sampleRecipe.containsIngredient(sampleIngredient));
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
