package com.example.dietscoop.Data;

import java.io.Serializable;
import java.util.Locale;

public enum timeUnit implements Serializable {
    /**
     * Enum representing time units
     */
    hour,
    minute;

    /**
     * Converts string to associated time unit
     * @param unit String unit to be converted to timeUnit Enum
     * @return timeUnit enum value
     */
    public static timeUnit stringToTimeUnit(String unit) {
        unit = unit.toUpperCase(Locale.ROOT);

        if (unit.equals("HOUR")) {
            return hour;
        } else if (unit.equals("MINUTE")) {
            return minute;
        } else {
            // TODO: MAKE THIS THROW ERROR
            return null;
        }

    }
}
