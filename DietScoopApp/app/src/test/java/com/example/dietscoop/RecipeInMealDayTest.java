package com.example.dietscoop;

import com.example.dietscoop.Data.Recipe.Recipe;
import com.example.dietscoop.Data.Recipe.RecipeInMealDay;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecipeInMealDayTest {

    public RecipeInMealDay sampleRecipeInMealDay() {
        return new RecipeInMealDay("SAMPLE");
    }

    @Test
    public void testSetServings() {
        RecipeInMealDay sample = sampleRecipeInMealDay();

        sample.setDesiredNumOfServings(10.0);
        assertEquals(sample.getDesiredNumOfServings().doubleValue(), 10.0);

    }

    @Test
    public void testSetParentRecipeID() {

        RecipeInMealDay sample = sampleRecipeInMealDay();

        sample.setParentRecipeID("TEST");
        assertEquals(sample.getParentRecipeID(), "TEST");
    }

    @Test
    public void testSetMealDayID() {

        RecipeInMealDay sample = sampleRecipeInMealDay();

        sample.setMealdayID("TEST");
        assertEquals(sample.getMealdayID(), "TEST");
    }

}
