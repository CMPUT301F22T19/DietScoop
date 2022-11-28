package com.example.dietscoop;

import com.example.dietscoop.Data.Ingredient.IngredientCategory;
import com.example.dietscoop.Data.Ingredient.IngredientInMealDay;
import com.example.dietscoop.Data.Ingredient.IngredientInRecipe;
import com.example.dietscoop.Data.Ingredient.IngredientUnit;
import com.example.dietscoop.Data.Meal.MealDay;
import com.example.dietscoop.Data.Recipe.Recipe;
import com.example.dietscoop.Data.Recipe.RecipeInMealDay;
import com.example.dietscoop.Data.Recipe.recipeCategory;
import com.example.dietscoop.Data.Recipe.timeUnit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;

public class MealTest {

    public ArrayList<IngredientInRecipe> generateSampleIngredients() {
        //Generating a sampleIngredients array list to contain the sample ingredients.
        ArrayList<IngredientInRecipe> sampleIngredients = new ArrayList<>();
        sampleIngredients.add(new IngredientInRecipe("Chicken", IngredientUnit.kg, 5, IngredientCategory.Meat));
        sampleIngredients.add(new IngredientInRecipe("Beans", IngredientUnit.kg, 10, IngredientCategory.Vegetable));
        sampleIngredients.add(new IngredientInRecipe("Rice", IngredientUnit.mg, 1, IngredientCategory.Vegetable));
        return sampleIngredients;
    }

    //Here getSampleIngredients handles the retrieval of sample ingredient arraylist.
    public ArrayList<Recipe> generateSampleRecipe() {
        ArrayList<Recipe> sampleRecipe = new ArrayList<>();
        //Instantiating the test Recipe Object:
        sampleRecipe.add(new Recipe("Frijoles Con Pollo", 5, 12, timeUnit.hr, recipeCategory.Breakfast, generateSampleIngredients(), "Unique breakfast."));
        sampleRecipe.add(new Recipe("Chicken and Rice", 12, 5, timeUnit.min, recipeCategory.Dinner, generateSampleIngredients(), "Sad Dinner."));
        sampleRecipe.add(new Recipe("Steamed Hams", 2, 2, timeUnit.hr, recipeCategory.Lunch, generateSampleIngredients(), "Unforgettable Luncheon."));
        return sampleRecipe;
    }

    public IngredientInMealDay sampleIngInMealDay() {

        return new IngredientInMealDay("desc", IngredientUnit.g, 1.0, IngredientCategory.Meat, "sampleID");

    }

    public MealDay sampleMealDay()
    {
        MealDay sample = new MealDay(LocalDate.of(2022, 10, 31));
        return sample;
    }



    @Test
    public void testAddRemoveIngredientInMealDay() {
        MealDay sample = sampleMealDay();

        sample.addIngredientInMealDay(sampleIngInMealDay());

        assertEquals(1, sample.getIngredientInMealDays().size());

        sample.removeIngredientInMealDay(0);

        assertEquals(0, sample.getIngredientInMealDays().size());

    }

    @Test
    public void testAddRemoveRecipeInMealDay() {

        MealDay sample = sampleMealDay();

        for (Recipe recipe : generateSampleRecipe()) {
            recipe.setId("test");
            sample.addRecipeInMealDay(new RecipeInMealDay(recipe));
        }

        assertEquals(3, sample.getRecipeInMealDays().size());

        for (int i = 0; i < 3; i++) {
            sample.removeRecipeInMealDay(0);
        }

        assertEquals(0, sample.getRecipeInMealDays().size());

    }

    @Test
    public void testDate() {

        MealDay sample = sampleMealDay();

        sample.setDate(LocalDate.of(2022, 12, 12));
        assertEquals(sample.getDate(), LocalDate.of(2022, 12, 12));

    }

    @Test
    public void testID() {

        MealDay sample = sampleMealDay();

        sample.setId("test");
        assertEquals(sample.getId(), "test");

    }

}
