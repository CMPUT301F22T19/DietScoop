// UML DONE

package com.example.dietscoop.Data.Ingredient;

import android.util.Log;

/**
 * Location of ingredient enum.
 */
public enum Location {
    //TODO: What are locations and categories? Ask Jakaria
    Pantry,
    Freezer,
    Fridge;

    /**
     * Converts string to enum type for location.
     * @param name String name to convert to enum.
     * @return Location enum corresponding to the provided string
     */
    public static Location stringToLocation(String name) {
        name = name.toUpperCase();

        switch (name) {
            case "PANTRY":
                return Pantry;
            case "FREEZER":
                return Freezer;
            case "FRIDGE":
                return Fridge;
            default:
                // TODO: MAKE THIS THROW ERROR
                Log.i("null?", "LOCATION NOT RECOGNIZED");
                return null;
        }
    }
}
