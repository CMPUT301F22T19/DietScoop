package com.example.dietscoop.Data.Comparators;

import com.example.dietscoop.Data.Recipe.Recipe;

import java.util.Comparator;

public class RecipeComparator {

    public static class byRecipeTitle implements Comparator<Recipe> {

        @Override
        public int compare(Recipe t1, Recipe t2) {
            return t1.getDescription().compareTo(t2.getDescription());
        }
    }


}
