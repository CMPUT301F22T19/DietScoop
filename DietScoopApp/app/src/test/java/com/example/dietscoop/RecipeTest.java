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
    public void testGetComments() {
        Recipe sampleRecipe = getSampleRecipe();
        assertEquals(sampleRecipe.getComments(), "Tasty breakfast");
    }

    @Test
    public void testGetNumberOfIngredients() {
        Recipe sampleRecipe = getSampleRecipe();
        assertEquals(sampleRecipe.getNumberOfIngredients(), 3);
    }

    @Test
    public void testGetIngredients() {
        Recipe sampleRecipe = getSampleRecipe();
        assertEquals(sampleRecipe.getIngredients().size(), 3);
    }

    @Test
    public void testGetPrepTime() {
        Recipe sampleRecipe = getSampleRecipe();
        assertEquals(sampleRecipe.getPrepTime(), 5);
    }

    @Test
    public void testGetCategory() {
        Recipe sampleRecipe = getSampleRecipe();
        assertEquals(sampleRecipe.getCategory(), RecipeCategory.breakfast);
    }

    @Test
    public void testGetNumOfServings() {
        Recipe sampleRecipe = getSampleRecipe();
        assertEquals(sampleRecipe.getNumOfServings(), 12);
    }

    @Test
    public void testRemoveIngredient() {
        Recipe sampleRecipe = getSampleRecipe();

        Ingredient sampleIngredient = new Ingredient("eggs", "kg", 4);

        sampleRecipe.addIngredient(sampleIngredient);

        assertEquals(sampleRecipe.getNumberOfIngredients(), 4);

        sampleRecipe.removeIngredient(sampleIngredient);

        assertEquals(sampleRecipe.getNumberOfIngredients(), 3);

    }
}
