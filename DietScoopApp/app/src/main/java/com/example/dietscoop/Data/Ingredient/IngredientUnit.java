package com.example.dietscoop.Data.Ingredient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public enum IngredientUnit {
    mL,
    L,
    tsp,
    tbsp,
    cup,
    lb,
    oz,
    mg,
    g,
    kg;

    public static IngredientUnit stringToUnit(String input) {
        input = input.toUpperCase(Locale.ROOT);

        switch(input) {
            case "L":
                return L;
            case "ML":
                return mL;
            case "CUP":
                return cup;
            case "TBSP":
                return tbsp;
            case "TSP":
                return tsp;
            case "LB":
                return lb;
            case "OZ":
                return oz;
            case "MG":
                return mg;
            case "G":
                return g;
            case "KG":
                return kg;
            default:
                 throw new RuntimeException("NO VALID UNIT GIVEN");
        }

    }
}
