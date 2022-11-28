package com.example.dietscoop;

import com.example.dietscoop.Data.Ingredient.Ingredient;
import com.example.dietscoop.Data.Ingredient.IngredientInRecipe;
import com.example.dietscoop.Data.Ingredient.IngredientInStorage;
import com.example.dietscoop.Data.Ingredient.IngredientUnit;
import com.example.dietscoop.Data.Ingredient.UnitConverter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UnitConverterTest {

    @Test
    public void testConvert() {

        IngredientUnit from = IngredientUnit.kg;
        IngredientUnit tog = IngredientUnit.g;
        IngredientUnit tomL = IngredientUnit.mL;

        double result1 = UnitConverter.convertIngredientUnit(1, from, tog);
        assertEquals(result1, 1000.0);

        assertThrows(RuntimeException.class, () -> UnitConverter.convertIngredientUnit(1, from, tomL));
    }

    @Test
    public void testGetUnitType() {

        IngredientUnit unit1 = IngredientUnit.kg;
        IngredientUnit unit2 = IngredientUnit.mL;

        assertEquals(UnitConverter.getUnitType(unit1), UnitConverter.unitType.mass);
        assertEquals(UnitConverter.getUnitType(unit2), UnitConverter.unitType.volume);

    }

    @Test
    public void testNormalize() {

        IngredientInStorage ingA = new IngredientInStorage();

        ingA.setMeasurementUnit(IngredientUnit.g);
        ingA.setAmount(10);

        IngredientInStorage newIngA = (IngredientInStorage) UnitConverter.normalizeAmountUnits(ingA);
        assertEquals(newIngA.getAmount(), 10000.0);
        assertEquals(newIngA.getMeasurementUnit(), IngredientUnit.mg);

        IngredientInStorage ingB = new IngredientInStorage();

        ingB.setMeasurementUnit(IngredientUnit.L);
        ingB.setAmount(1);

        IngredientInStorage newIngB = (IngredientInStorage) UnitConverter.normalizeAmountUnits(ingB);

        assertEquals(newIngB.getAmount(), 1000.0);
        assertEquals(newIngB.getMeasurementUnit(), IngredientUnit.mL);

    }
}
