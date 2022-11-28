package com.example.dietscoop.Data.Ingredient;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

public class UnitConverter {

    public enum unitType {
        mass,
        volume
    }

    private static final EnumMap<IngredientUnit, Double> weightConversions;
    private static final EnumMap<IngredientUnit, Double> volumeConversions;
    private static final List<IngredientUnit> massUnits = Arrays.asList(IngredientUnit.g, IngredientUnit.mg,
            IngredientUnit.kg, IngredientUnit.oz, IngredientUnit.lb);
    private static final List<IngredientUnit> volUnits = Arrays.asList(IngredientUnit.cup, IngredientUnit.L,
            IngredientUnit.mL, IngredientUnit.tbsp, IngredientUnit.tsp);

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

    public static unitType getUnitType(IngredientUnit unit) {
        if (massUnits.contains(unit)) {
            return unitType.mass;
        } else if (volUnits.contains(unit)) {
            return unitType.volume;
        } else {
            throw new RuntimeException("UNEXPECTED UNIT! DOES NOT EXIST IN UNIT TYPE LISTS");
        }
    }

    public static Ingredient normalizeAmountUnits(Ingredient input) {

        if (getUnitType(input.getMeasurementUnit()) == unitType.mass) {
            input.setAmount(convertIngredientUnit(input.getAmount(), input.getMeasurementUnit(), IngredientUnit.mg));
            input.setMeasurementUnit(IngredientUnit.mg);
        } else {
            assert (getUnitType(input.getMeasurementUnit()) == unitType.volume);
            input.setAmount(convertIngredientUnit(input.getAmount(), input.getMeasurementUnit(), IngredientUnit.mL));
            input.setMeasurementUnit(IngredientUnit.mL);
        }

        return input;

    }

}
