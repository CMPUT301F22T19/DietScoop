package com.example.dietscoop.Data.Ingredient;

public class IngredientInMealDay extends Ingredient {
    private Double scalingFactor;

    public IngredientInMealDay(String description, String measurementUnit, double amount, IngredientCategory category) {
        super(description, measurementUnit, amount, category);
    }

    public IngredientInMealDay(IngredientInStorage i) {
        super(i.getDescription(), i.getMeasurementUnit(), i.getAmount(), i.getCategory());
    }

    public void setScalingFactor(Double scalingFactor) {
        this.scalingFactor = scalingFactor;
    }

    public Double getScalingFactor() {
        return this.scalingFactor;
    }
}
