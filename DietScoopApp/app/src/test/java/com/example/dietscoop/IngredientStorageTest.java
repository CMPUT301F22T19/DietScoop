package com.example.dietscoop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.dietscoop.Data.IngredientCategory;
import com.example.dietscoop.Data.IngredientInStorage;
import com.example.dietscoop.Data.Location;
import com.example.dietscoop.Database.IngredientStorage;

import org.junit.jupiter.api.Test;

/**
 * This Test oversees the 'local' functionality of the methods
 * within the FoodStorage Class.
 */
public class IngredientStorageTest {

    private IngredientStorage sampleIngredientStorage;

    public IngredientStorage getSampleIngredientStorage() {
        return new IngredientStorage();
    }

    public IngredientInStorage getSampleIngredient() {
        return new IngredientInStorage("Pop", "kg",
                5, 2022, 4, 24, Location.freezer, IngredientCategory.meat);
    }

    public void idle() {
        return;
    }

    @Test
    public void testAddIngredientToStorage() {
        sampleIngredientStorage = new IngredientStorage();
        IngredientInStorage sampleIngredient = new IngredientInStorage("Pop", "kg",
                5, 2022, 4, 24, Location.freezer, IngredientCategory.meat);
        sampleIngredientStorage.setupIngredientSnapshotListener(); //TODO: need to add the pass value.
        sampleIngredientStorage.addIngredientToStorage(sampleIngredient);
    }

}
