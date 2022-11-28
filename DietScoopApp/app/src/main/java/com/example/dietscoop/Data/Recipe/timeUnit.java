package com.example.dietscoop.Data.Recipe;

import java.io.Serializable;
import java.util.Locale;

public enum timeUnit implements Serializable {
    /**
     * Enum representing time units
     */
    hr,
    min;

    /**
     * Converts string to associated time unit
     * @param unit String unit to be converted to timeUnit Enum
     * @return timeUnit enum value
     */
    public static timeUnit stringToTimeUnit(String unit) {
        unit = unit.toUpperCase(Locale.ROOT);

        if (unit.equals("HR")) {
            return hr;
        } else if (unit.equals("MIN")) {
            return min;
        } else {
            // TODO: MAKE THIS THROW ERROR
            return null;
        }

    }
}
