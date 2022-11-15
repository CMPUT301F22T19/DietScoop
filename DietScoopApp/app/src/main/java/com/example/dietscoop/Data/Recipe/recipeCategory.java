package com.example.dietscoop.Data.Recipe;

import java.io.Serializable;
import java.util.Locale;

/**
 * Enum representing recipe categories
 */
public enum recipeCategory implements Serializable {
    Breakfast,
    Lunch,
    Dinner,
    Dessert,
    Appetizer,
    Holiday,
    Beverages,
    Baked;

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
                return Breakfast;
            case "LUNCH":
                return Lunch;
            case "DINNER":
                return Dinner;
            case "APPETIZER":
                return Appetizer;
            case "DESSERT":
                return Dessert;
            case "HOLIDAY":
                return Holiday;
            case "BEVERAGES":
                return Beverages;
            case "BAKED":
                return Baked;
            default:
                // TODO: MAKE THIS THROW ERROR
                return null;
        }

    }
}
