package com.example.dietscoop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jetbrains.annotations.TestOnly;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * This Test oversees the 'local' functionality of the methods
 * within the FoodStorage Class.
 *
 *TODO: Add potential interactivity with Database Object.
 */
public class IngredientStorageTest {

    private IngredientStorage sampleIngredientStorage;

    public IngredientStorage getSampleIngredientStorage() {
        return new IngredientStorage();
    }

    public IngredientStorage getSampleIngredientStorageWithData() {
        IngredientStorage sample = new IngredientStorage();
        sample.addIngredientToStorage(new IngredientInStorage("Chicken", "kg",
                5, 2022, 4, 24, Location.freezer, Category.meat));
        return sample;
    }

    public void idle() {
        return;
    }

    @Test
    public void testAddIngredientToStorage() {
        sampleIngredientStorage = getSampleIngredientStorage();
        sampleIngredientStorage.addIngredientToStorage(new IngredientInStorage("Pop", "kg",
                5, 2022, 4, 24, Location.freezer, Category.meat));
    }

//    @Test
//    public void testremoveIngredientFromStorage() {
//
//    }

    @Test
    public void testGetIngredientStorage() {
        sampleIngredientStorage = getSampleIngredientStorage();
        ArrayList<IngredientInStorage> ingredients = sampleIngredientStorage.getIngredientStorage();
        assertEquals(ingredients.size(), 1); //This value might change with the database contents.
    }

}
