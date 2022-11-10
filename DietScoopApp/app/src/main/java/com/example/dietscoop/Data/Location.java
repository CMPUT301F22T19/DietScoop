package com.example.dietscoop.Data;

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
     * @return
     */
    public static Location stringToLocation(String name) {
        name = name.toUpperCase();

        if (name.equals("PANTRY")) {
            return pantry;
        } else if (name.equals("FREEZER")) {
            return freezer;
        } else if (name.equals("FRIDGE")) {
            return fridge;
        } else {
            // TODO: MAKE THIS THROW ERROR
            Log.i("null?", "LOCATION NOT RECOGNIZED");
            return null;
        }
    }
}
