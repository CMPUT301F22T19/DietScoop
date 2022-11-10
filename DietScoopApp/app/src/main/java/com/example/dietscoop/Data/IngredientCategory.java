package com.example.dietscoop.Data;

import java.io.Serializable;
import java.util.Locale;

public enum IngredientCategory implements Serializable {
    /**
     * Category for different ingredients
     */
    vegetable,
    meat,
    fruit;

    /**
     * Method change string to Enum class
     *
     * @param name String to change to enum
     * @return Enum value of category
     */
    public static IngredientCategory stringToCategory(String name) {
        name = name.toUpperCase(Locale.ROOT);

        if (name.equals("VEGETABLE")) {
            return vegetable;
        } else if (name.equals("MEAT")) {
            return meat;
        } else if (name.equals("FRUIT")) {
            return fruit;
        } else {
            // TODO: MAKE THIS THROW ERROR
            return null;
        }

    }

}
