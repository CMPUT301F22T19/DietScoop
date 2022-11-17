// UML DONE

package com.example.dietscoop.Data.Ingredient;

import android.util.Log;

/**
 * Location of ingredient enum.
 */
public enum Location {
    //TODO: What are locations and categories? Ask Jakaria
    pantry,
    freezer,
    fridge;

    /**
     * Converts string to enum type for location.
     * @param name String name to convert to enum.
     * @return Location enum corresponding to the provided string
     */
    public static Location stringToLocation(String name) {
        name = name.toUpperCase();

        switch (name) {
            case "PANTRY":
                return pantry;
            case "FREEZER":
                return freezer;
            case "FRIDGE":
                return fridge;
            default:
                // TODO: MAKE THIS THROW ERROR
                Log.i("null?", "LOCATION NOT RECOGNIZED");
                return null;
        }
    }
}
