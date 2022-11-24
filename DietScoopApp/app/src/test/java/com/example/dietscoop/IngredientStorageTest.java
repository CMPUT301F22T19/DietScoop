package com.example.dietscoop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.dietscoop.Data.Ingredient.IngredientCategory;
import com.example.dietscoop.Data.Ingredient.IngredientInStorage;
import com.example.dietscoop.Data.Ingredient.IngredientUnit;
import com.example.dietscoop.Data.Ingredient.Location;
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
        return new IngredientInStorage("Pop", IngredientUnit.kg,
                5.0, 2022, 4, 24, Location.Freezer, IngredientCategory.Meat);
    }

    @Test
    public void testAddIngredientToStorage() {
        sampleIngredientStorage = new IngredientStorage();
        IngredientInStorage sampleIngredient = new IngredientInStorage("Pop", IngredientUnit.kg,
                5.0, 2022, 4, 24, Location.Freezer, IngredientCategory.Meat);
        sampleIngredientStorage.setupIngredientSnapshotListener(); //TODO: need to add the pass value.
        sampleIngredientStorage.addIngredientToStorage(sampleIngredient);
    }

}
