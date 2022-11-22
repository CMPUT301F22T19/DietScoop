package com.example.dietscoop.Data.Ingredient;

public enum weight {
    lb,
    oz,
    mg,
    g,
    kg;

    static final private double KG2MG = 1000000;
    static final private double G2MG = 1000;
    static final private double OZ2MG = 28349.5;
    static final private double LB2MG = 453592;

}
