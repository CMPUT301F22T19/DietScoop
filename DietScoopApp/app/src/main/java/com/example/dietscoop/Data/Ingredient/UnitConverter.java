package com.example.dietscoop.Data.Ingredient;

import java.util.EnumMap;

public class UnitConverter {

    private static final EnumMap<IngredientUnit, Double> weightConversions;
    private static final EnumMap<IngredientUnit, Double> volumeConversions;

    static {

        weightConversions = new EnumMap<>(IngredientUnit.class);
        weightConversions.put(IngredientUnit.g, 1000.0);
        weightConversions.put(IngredientUnit.kg, 1000000.0);
        weightConversions.put(IngredientUnit.lb, 453592.0);
        weightConversions.put(IngredientUnit.oz, 28349.5);
        weightConversions.put(IngredientUnit.mg, 1.0);

        volumeConversions = new EnumMap<IngredientUnit, Double>(IngredientUnit.class);
        volumeConversions.put(IngredientUnit.cup, 236.588);
        volumeConversions.put(IngredientUnit.tbsp, 14.7868);
        volumeConversions.put(IngredientUnit.tsp, 4.92892);
        volumeConversions.put(IngredientUnit.L, 1000.0);
        volumeConversions.put(IngredientUnit.mL, 1.0);

    }

    public static double convertIngredientUnit(double val, IngredientUnit from, IngredientUnit to) {

        if (weightConversions.containsKey(from) && weightConversions.containsKey(to)) {
            return val * weightConversions.get(from) / weightConversions.get(to);
        } else if (volumeConversions.containsKey(from) && volumeConversions.containsKey(to)) {
            return val * volumeConversions.get(from) / volumeConversions.get(to);
        } else {
            throw new RuntimeException("INVALID UNIT CONVERSION!");
        }

    }

}
