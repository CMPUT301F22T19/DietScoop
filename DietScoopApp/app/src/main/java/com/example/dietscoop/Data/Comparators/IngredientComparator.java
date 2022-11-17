package com.example.dietscoop.Data.Comparators;

import com.example.dietscoop.Data.Ingredient.IngredientInStorage;

import java.util.Comparator;

/**
 * Comparator class for ingredient object allows for ordering and comparisons.
 */
public class IngredientComparator {

    /**
     * static ordering method for ordering by ingredient name
     */
    public static class byName implements Comparator<IngredientInStorage> {
        
        @Override
        public int compare(IngredientInStorage t1, IngredientInStorage t2) {
            return t1.getDescription().compareTo(t2.getDescription());
        }
    }

    /**
     * static ordering method for ordering by location
     */
    public static class byLocation implements  Comparator<IngredientInStorage> {

        @Override
        public int compare(IngredientInStorage t1, IngredientInStorage t2) {
            return t1.getLocationName().compareTo(t2.getLocationName());
        }
    }

    /**
     * static ordering method for ordering by category
     */
    public static class byCategory implements Comparator<IngredientInStorage> {

        @Override
        public int compare(IngredientInStorage t1, IngredientInStorage t2) {
            return t1.getCategoryName().compareTo(t2.getCategoryName());
        }
    }

    /**
     * static ordering method for ordering by best before date (ASC)
     */
    public static class byBestBefore implements Comparator<IngredientInStorage> {

        @Override
        public int compare(IngredientInStorage t1, IngredientInStorage t2) {
            return t1.getBestBeforeDate().compareTo(t2.getBestBeforeDate());
        }
    }
}
