package com.example.dietscoop.Data.Recipe;

import java.io.Serializable;
import java.util.Locale;

/**
 * Enum representing recipe categories
 */
public enum recipeCategory implements Serializable {
    breakfast,
    lunch,
    dinner,
    dessert,
    appetizer,
    holiday,
    beverages,
    baked;

    /**
     * Converts string to associated recipe category
     *
     * @param name String name to be converted to recipeCategory enum
     * @return recipeCategory enum value
     */
    public static recipeCategory stringToRecipeCategory(String name) {
        name = name.toUpperCase(Locale.ROOT);

        switch (name) {
            case "BREAKFAST":
                return breakfast;
            case "LUNCH":
                return lunch;
            case "DINNER":
                return dinner;
            case "APPETIZER":
                return appetizer;
            case "DESSERT":
                return dessert;
            case "HOLIDAY":
                return holiday;
            case "BEVERAGES":
                return beverages;
            case "BAKED":
                return baked;
            default:
                // TODO: MAKE THIS THROW ERROR
                return null;
        }

    }
}
