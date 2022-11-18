package com.example.dietscoop.Data.Comparators;

import android.util.Log;

import com.example.dietscoop.Data.Recipe.Recipe;
import com.example.dietscoop.Data.Recipe.timeUnit;

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
            return Integer.compare(t1.getInMinutes(), t2.getInMinutes());
        }
    }

    /**
     * This orders the Recipes by the number of servings
     */
    public static class byRecipeServingNumber implements  Comparator<Recipe> {
        @Override
        public int compare(Recipe t1, Recipe t2) {
            return Integer.compare(t1.getNumOfServings(), t2.getNumOfServings());
        }
    }

    /**
     * This orders the Recipes by recipe categories
     */
    public static class byRecipeCategory implements  Comparator<Recipe> {
        @Override
        public int compare(Recipe t1, Recipe t2) {
            return t1.getCategoryName().compareTo(t2.getCategoryName());

        }
    }

}
