package com.example.dietscoop.Data.Ingredient;

public enum volume {
    mL,
    L,
    tsp,
    tbsp,
    cup;

    static final private double CUP2ML = 236.588;
    static final private double TBSP2ML = 14.7868;
    static final private double TSP2ML = 4.92892;
    static final private double L2ML = 1000;

    public double cupTomL(double input) {
        return (input * CUP2ML);
    }

    public double mLToCup(double input) {
        return (input / CUP2ML);
    }


}
