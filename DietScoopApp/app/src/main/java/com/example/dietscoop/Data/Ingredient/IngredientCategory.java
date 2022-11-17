// DONE UML

package com.example.dietscoop.Data.Ingredient;

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

        switch (name) {
            case "VEGETABLE":
                return vegetable;
            case "MEAT":
                return meat;
            case "FRUIT":
                return fruit;
            default:
                // TODO: MAKE THIS THROW ERROR
                return null;
        }

    }

}
