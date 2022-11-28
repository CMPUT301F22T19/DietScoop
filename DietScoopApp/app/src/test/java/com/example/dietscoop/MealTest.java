package com.example.dietscoop;

import com.example.dietscoop.Data.Ingredient.IngredientCategory;
import com.example.dietscoop.Data.Ingredient.IngredientInMealDay;
import com.example.dietscoop.Data.Ingredient.IngredientInRecipe;
import com.example.dietscoop.Data.Ingredient.IngredientUnit;
import com.example.dietscoop.Data.Meal.MealDay;
import com.example.dietscoop.Data.Recipe.Recipe;
import com.example.dietscoop.Data.Recipe.recipeCategory;
import com.example.dietscoop.Data.Recipe.timeUnit;

import org.junit.jupiter.api.Test;

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

    public MealDay sampleMealDay()
    {
        MealDay sample = new MealDay(LocalDate.of(2022, 10, 31));
        return sample;
    }

    @Test
    public void testAddIngredientFromMealDay() {
        MealDay sample = sampleMealDay();
//        sample.addIngredientInMealDay(new IngredientInMealDay());
    }

    @Test
    public void testDeleteIngredientFromMealDay() {

    }



}
