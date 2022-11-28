package com.example.dietscoop;

import com.example.dietscoop.Data.Comparators.IngredientComparator;
import com.example.dietscoop.Data.Ingredient.IngredientCategory;
import com.example.dietscoop.Data.Ingredient.IngredientInStorage;
import com.example.dietscoop.Data.Ingredient.Location;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class IngredientComparatorTest {

    private IngredientComparator.byName nameComparator() {
        return new IngredientComparator.byName();
    }

    private IngredientComparator.byCategory categoryComparator() {
        return new IngredientComparator.byCategory();
    }

    private IngredientComparator.byLocation locationComparator() {
        return new IngredientComparator.byLocation();
    }

    private IngredientComparator.byBestBefore bestBeforeComparator() {
        return new IngredientComparator.byBestBefore();
    }

    private IngredientInStorage emptyIngredient() {

        return new IngredientInStorage();

    }

    @Test
    public void testByName() {

        IngredientInStorage a = emptyIngredient();
        IngredientInStorage b = emptyIngredient();

        a.setDescription("AAA");
        b.setDescription("BBB");

        assertTrue(nameComparator().compare(a, b) < 0);
        assertTrue(nameComparator().compare(b, a) > 0);
        assertEquals(0, nameComparator().compare(a, a));
    }

    @Test
    public void testByCategory() {

        IngredientInStorage a = emptyIngredient();
        IngredientInStorage b = emptyIngredient();

        a.setCategory(IngredientCategory.Dairy);
        b.setCategory(IngredientCategory.Vegetable);

        assertTrue(categoryComparator().compare(a, b) < 0);
        assertTrue(categoryComparator().compare(b, a) > 0);
        assertEquals(0, categoryComparator().compare(a, a));

    }

    @Test
    public void testByLocation() {

        IngredientInStorage a = emptyIngredient();
        IngredientInStorage b = emptyIngredient();

        a.setLocation(Location.Fridge);
        b.setLocation(Location.Pantry);

        assertTrue(locationComparator().compare(a, b) < 0);
        assertTrue(locationComparator().compare(b, a) > 0);
        assertEquals(0, locationComparator().compare(a, a));

    }

    @Test
    public void testByBestBefore() {

        IngredientInStorage a = emptyIngredient();
        IngredientInStorage b = emptyIngredient();

        a.setBestBeforeDate(2000, 12, 12);
        b.setBestBeforeDate(2001, 1, 1);

        assertTrue(bestBeforeComparator().compare(a, b) < 0);
        assertTrue(bestBeforeComparator().compare(b, a) > 0);
        assertEquals(0, bestBeforeComparator().compare(a, a));

    }

}
