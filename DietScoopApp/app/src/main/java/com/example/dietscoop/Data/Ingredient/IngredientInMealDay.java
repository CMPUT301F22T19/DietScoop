package com.example.dietscoop.Data.Ingredient;

public class IngredientInMealDay extends Ingredient {
    private Double scalingFactor;
    private IngredientInStorage parentIngredient;
    private String mealdayID;
    //TODO: WARNING MARCOS FIDDLED HERE:
    private String parentIngredientID; //This will be used to always hold a reference to the parent to fetch updates.

    public IngredientInMealDay(String description, IngredientUnit measurementUnit, double amount, IngredientCategory category, String parentIngredientID) {
        super(description, measurementUnit, amount, category);
        this.parentIngredientID = parentIngredientID;
    }

    public IngredientInMealDay(IngredientInStorage i) {
        super(i.getDescription(), i.getMeasurementUnit(), i.getAmount(), i.getCategory());
        this.parentIngredient = i;
        this.parentIngredientID = i.getId();
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
