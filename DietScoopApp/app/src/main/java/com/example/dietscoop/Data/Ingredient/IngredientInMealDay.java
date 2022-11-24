package com.example.dietscoop.Data.Ingredient;

public class IngredientInMealDay extends Ingredient {
    private Double scalingFactor;
    private IngredientInStorage parentIngredient;
    private String mealdayID;

    public IngredientInMealDay(String description, String measurementUnit, double amount, IngredientCategory category) {
        super(description, measurementUnit, amount, category);
    }

    public IngredientInMealDay(IngredientInStorage i) {
        super(i.getDescription(), i.getMeasurementUnit(), i.getAmount(), i.getCategory());
        this.parentIngredient = i;
    }

    public void setScalingFactor(Double scalingFactor) {
        this.scalingFactor = scalingFactor;
    }

    public Double getScalingFactor() {
        return this.scalingFactor;
    }

    public void setParentIngredient(IngredientInStorage parentIngredient) {
        this.parentIngredient = parentIngredient;
    }

    public IngredientInStorage getParentIngredient() {
        return this.parentIngredient;
    }

    public String getMealdayID() {
        return mealdayID;
    }

    public void setMealdayID(String mealdayID) {
        this.mealdayID = mealdayID;
    }
}
