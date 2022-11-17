package com.example.dietscoop.Data.Comparators;

import com.example.dietscoop.Data.Recipe.Recipe;
import java.lang.Integer;
import java.util.Comparator;

public class RecipeComparator {

    /**
     * This will order recipes by title
     */
    public static class byRecipeTitle implements Comparator<Recipe> {
        @Override
        public int compare(Recipe t1, Recipe t2) {
            return t1.getDescription().compareTo(t2.getDescription());
        }
    }

    /**
     * This orders the Recipes by prep time
     */
    public static class byRecipePrepTime implements  Comparator<Recipe> {
        @Override
        public int compare(Recipe t1, Recipe t2) {
            return Integer.compare(t1.getPrepTime(), t2.getPrepTime());
        }
    }



}
