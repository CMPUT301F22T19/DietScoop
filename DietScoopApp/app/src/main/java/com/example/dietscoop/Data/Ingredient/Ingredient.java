package com.example.dietscoop.Data.Ingredient;

import com.example.dietscoop.Data.FoodItem;

import java.io.Serializable;

/**
     * Ingredient abstract class to be inherited by ingredient in recipe and ingredient in storage
     */
    public abstract class Ingredient extends FoodItem implements Serializable {
        private Double amount;
        private String measurementUnit;
        private IngredientCategory category;


        /**
         * Constructor for Ingredient
         *  @param description     Description of ingredient
         * @param measurementUnit Measurement unit of ingredient
         * @param amount          Amount of ingredient
         * @param category        Category of ingredient.
         */
        public Ingredient(String description, String measurementUnit, Double amount, IngredientCategory category) {
            this.description = description;
            this.amount = amount;
            this.measurementUnit = measurementUnit;
            this.category = category;

        }

    public Ingredient() {

    }

    /**
         * getter for ingredient amount
         *
         * @return ingredient amount
         */
        public double getAmount() {
            return this.amount;
        }

        /**
         * setter for ingredient amount
         *
         * @param amount value to set
         */
        public void setAmount(double amount) {
            this.amount = amount;
        }

        /**
         * getter for ingredient measurement unit
         *
         * @return measurement unit of ingredient
         */
        public String getMeasurementUnit() {
            return this.measurementUnit;
        }

        /**
         * setter for ingredient measurement unit
         *
         * @param measurementUnit value to set.
         */
        public void setMeasurementUnit(String measurementUnit) {
            this.measurementUnit = measurementUnit;
        }

        public String getCountWithMeasurement(){
            return String.valueOf(this.getAmount()) + " " + this.getMeasurementUnit();
        }

        /**
         * getter for ingredient category
         *
         * @return ingredient category
         */
        public IngredientCategory getCategory() {
            return this.category;
        }

        public void setCategory(IngredientCategory ingredientCategory){
            this.category = ingredientCategory;
        }

        /**
         * getter for ingredient category name
         *
         * @return Stringified ingredient category
         */
        public String getCategoryName() {
            return this.category.name();
        }

        @Override
        public String getType() {
            return "Ingredient";
        }

    }