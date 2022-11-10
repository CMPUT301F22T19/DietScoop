package com.example.dietscoop.Data;

import java.io.Serializable;
import java.util.Locale;

/**
 * Enum representing recipe categories
 */
public enum recipeCategory implements Serializable {
    breakfast,
    lunch,
    dinner,
    appetizer,
    salad,
    baked;

    /**
     * Converts string to associated recipe category
     *
     * @param name String name to be converted to recipeCategory enum
     * @return recipeCategory enum value
     */
    public static recipeCategory stringToRecipeCategory(String name) {
        name = name.toUpperCase(Locale.ROOT);

        if (name.equals("BREAKFAST")) {
            return breakfast;
        } else if (name.equals("LUNCH")) {
            return lunch;
        } else if (name.equals("DINNER")) {
            return dinner;
        } else if (name.equals("APPETIZER")) {
            return appetizer;
        } else if (name.equals("SALAD")) {
            return salad;
        } else if (name.equals("BAKED")) {
            return baked;
        } else {
            // TODO: MAKE THIS THROW ERROR
            return null;
        }

    }
}
