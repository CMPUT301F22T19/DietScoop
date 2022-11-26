package com.example.dietscoop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.dietscoop.Data.Ingredient.IngredientCategory;
import com.example.dietscoop.Data.Ingredient.IngredientInRecipe;
import com.example.dietscoop.Data.Ingredient.IngredientUnit;

import org.junit.jupiter.api.Test;

public class IngredientInRecipeTest {
    IngredientInRecipe sampleIngredientREC;

    public IngredientInRecipe startSampleIngredientREC() {
        return new IngredientInRecipe("Chicken", IngredientUnit.kg,
                5.0, IngredientCategory.Meat);

    }

    @Test
    public void testGetCategory() {
        //Initializing object:
        sampleIngredientREC = startSampleIngredientREC();

        //Asserting the validity of Object:
        assertEquals(sampleIngredientREC.getCategory(), IngredientCategory.Meat);
    }


    @Test
    public void testSetCategory() {
        //Initializing object:
        sampleIngredientREC = startSampleIngredientREC();

        sampleIngredientREC.setCategory(IngredientCategory.Fruit);

        //Asserting the validity of Object:
        assertEquals(sampleIngredientREC.getCategory(), IngredientCategory.Fruit);
    }

}
