package com.example.dietscoop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.example.dietscoop.Data.Ingredient.IngredientCategory;
import com.example.dietscoop.Data.Ingredient.IngredientInMealDay;
import com.example.dietscoop.Data.Ingredient.IngredientInStorage;
import com.example.dietscoop.Data.Ingredient.IngredientUnit;
import com.example.dietscoop.Data.Ingredient.Location;
import com.example.dietscoop.Database.IngredientStorage;

import org.junit.jupiter.api.Test;

import java.util.Optional;

/**
 * This Test oversees the 'local' functionality of the methods
 * within the FoodStorage Class.
 */
public class IngredientInMealDayTest {

    public IngredientInMealDay getSampleIngredient() {
        return new IngredientInMealDay("Pop", IngredientUnit.kg, 5, IngredientCategory.Meat, null);
    }

    @Test
    public void testSetScalingFactor() {
        IngredientInMealDay sample = getSampleIngredient();

        sample.setScalingFactor(0.25);
        assertNotNull(sample.getScalingFactor());
        assertEquals(Optional.ofNullable(sample.getScalingFactor()), Optional.of(0.25));
    }

    @Test
    public void testGetScalingFactorTest() {
        IngredientInMealDay sample = getSampleIngredient();
        assertNull(sample.getScalingFactor());
        sample.setScalingFactor(-0.25);
        assertNotNull(sample.getScalingFactor());
        assertEquals(Optional.ofNullable(sample.getScalingFactor()), Optional.of(-0.25));
    }

}
