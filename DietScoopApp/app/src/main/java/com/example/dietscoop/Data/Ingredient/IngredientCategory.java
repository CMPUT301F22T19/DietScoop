package com.example.dietscoop.Data.Ingredient;

import java.io.Serializable;
import java.util.Locale;

public enum IngredientCategory implements Serializable {
    /**
     * Category for different ingredients
     */
    Vegetable,
    Meat,
    Dairy,
    Grains,
    Oils,
    Spices,
    Fruit,
    Other;

    /**
     * Method change string to Enum class
     *
     * @param name String to change to enum
     * @return Enum value of category
     */
    public static IngredientCategory stringToCategory(String name) {
        name = name.toUpperCase(Locale.ROOT);

        switch (name) {
            case "VEGETABLE":
                return Vegetable;
            case "MEAT":
                return Meat;
            case "DAIRY":
                return Dairy;
            case "GRAINS":
                return Grains;
            case "OILS":
                return Oils;
            case "SPICES":
                return Spices;
            case "FRUIT":
                return Fruit;
            case "OTHER":
                return Other;
            default:
                // TODO: MAKE THIS THROW ERROR
                return null;
        }

    }

}
