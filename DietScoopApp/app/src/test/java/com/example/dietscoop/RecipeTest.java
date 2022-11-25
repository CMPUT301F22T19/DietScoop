package com.example.dietscoop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.example.dietscoop.Data.Ingredient.IngredientCategory;
import com.example.dietscoop.Data.Ingredient.IngredientInRecipe;
import com.example.dietscoop.Data.Ingredient.IngredientUnit;
import com.example.dietscoop.Data.Recipe.Recipe;
import com.example.dietscoop.Data.Recipe.recipeCategory;
import com.example.dietscoop.Data.Recipe.timeUnit;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class RecipeTest {
    /**
     * Generates a sample Recipe for testing methods.
     * @return ArrayList<Ingredients></Ingredients>
     */
    public ArrayList<IngredientInRecipe> generateSampleIngredients() {
        //Generating a sampleIngredients array list to contain the sample ingredients.
        ArrayList<IngredientInRecipe> sampleIngredients = new ArrayList<>();
        sampleIngredients.add(new IngredientInRecipe("Chicken", IngredientUnit.kg, 5, IngredientCategory.Meat));
        sampleIngredients.add(new IngredientInRecipe("Beans", IngredientUnit.kg, 10, IngredientCategory.Vegetable));
        sampleIngredients.add(new IngredientInRecipe("Rice", IngredientUnit.mg, 1, IngredientCategory.Vegetable));
        return sampleIngredients;
    }

    //Here getSampleIngredients handles the retrieval of sample ingredient arraylist.
    public Recipe getSampleRecipe() {
        //Instantiating the test Recipe Object:
        return new Recipe("Frijoles Con Pollo", 5, 12, timeUnit.hr, recipeCategory.Breakfast, generateSampleIngredients(), "Unique breakfast.");
    }

    @Test
    public void testGetDescription() {
        Recipe sampleRecipe = getSampleRecipe();
        assertEquals(sampleRecipe.getDescription(), "Frijoles Con Pollo");
    }

    @Test
    public void testAddIngredient() {
        Recipe sampleRecipe = getSampleRecipe();

        IngredientInRecipe sampleIngredient = new IngredientInRecipe("Salami", IngredientUnit.kg,
                5.0, IngredientCategory.Meat);

        sampleRecipe.addIngredient(sampleIngredient);
        assertEquals(4, sampleRecipe.getIngredients().size());
        assertEquals("Salami", sampleRecipe.getIngredients().get(3).getDescription());
        assertEquals(IngredientUnit.kg, sampleRecipe.getIngredients().get(3).getMeasurementUnit());
        assertEquals(5.0, sampleRecipe.getIngredients().get(3).getAmount());
        assertEquals(IngredientCategory.Meat, sampleRecipe.getIngredients().get(3).getCategory());
    }

    @Test
    public void testGetComments() {
        Recipe sampleRecipe = getSampleRecipe();
        assertEquals(sampleRecipe.getDescription(), "Frijoles Con Pollo");
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
        assertEquals(sampleRecipe.getCategory(), recipeCategory.Breakfast);
    }

    @Test
    public void testGetNumOfServings() {
        Recipe sampleRecipe = getSampleRecipe();
        assertEquals(sampleRecipe.getNumOfServings(), 12);
    }

    @Test
    public void testRemoveIngredient() {
        Recipe sampleRecipe = getSampleRecipe();

        IngredientInRecipe sampleIngredient = new IngredientInRecipe("eggs", IngredientUnit.kg, 4, IngredientCategory.Vegetable);

        sampleRecipe.addIngredient(sampleIngredient);

        assertEquals(sampleRecipe.getNumberOfIngredients(), 4);

        sampleRecipe.removeIngredient(sampleIngredient);

        assertEquals(sampleRecipe.getNumberOfIngredients(), 3);

        for(int i = 0; i < sampleRecipe.getNumberOfIngredients(); ++i)
        {
            assertNotEquals("eggs", sampleRecipe.getIngredients().get(i)
                    .getDescription());
        }

    }

    @Test
    public void testforAction() {
        assertFalse(true);
    }
}
