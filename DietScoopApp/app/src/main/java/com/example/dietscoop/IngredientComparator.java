package com.example.dietscoop;

import java.util.Comparator;

public class IngredientComparator {

    public static class byName implements Comparator<IngredientInStorage> {
        
        @Override
        public int compare(IngredientInStorage t1, IngredientInStorage t2) {
            return t1.getDescription().compareTo(t2.getDescription());
        }
    }

    public static class byLocation implements  Comparator<IngredientInStorage> {

        @Override
        public int compare(IngredientInStorage t1, IngredientInStorage t2) {
            return t1.getLocationName().compareTo(t2.getLocationName());
        }
    }

    public static class byCategory implements Comparator<IngredientInStorage> {

        @Override
        public int compare(IngredientInStorage t1, IngredientInStorage t2) {
            return t1.getCategoryName().compareTo(t2.getCategoryName());
        }
    }

    public static class byBestBefore implements Comparator<IngredientInStorage> {

        @Override
        public int compare(IngredientInStorage t1, IngredientInStorage t2) {
            return t1.getBestBeforeDate().compareTo(t2.getBestBeforeDate());
        }
    }
}
