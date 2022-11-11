package com.example.dietscoop.Data.Ingredient;

import com.example.dietscoop.Data.FoodItem;

import java.io.Serializable;

/**
     * Ingredient abstract class to be inherited by ingredient and ingredient in storage
     */
    public abstract class Ingredient extends FoodItem implements Serializable {
        private double amount;
        private String measurementUnit;
        private IngredientCategory category;


        /**
         * Constructor for Ingredient
         *
         * @param description     Description of ingredient
         * @param measurementUnit Measurement unit of ingredient
         * @param amount          Amount of ingredient
         * @param category        Category of ingredient.
         */
        public Ingredient(String description, String measurementUnit, double amount, IngredientCategory category) {
            this.description = description;
            this.amount = amount;
            this.measurementUnit = measurementUnit;
            this.category = category;

        }

        /**
         * getter for ingredient amount
         *
         * @return ingredient amount
         */
        public double getAmount() {
            return amount;
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
            return measurementUnit;
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
            return category;
        }

        /**
         * getter for ingredient category name
         *
         * @return Stringified ingredient category
         */
        public String getCategoryName() {
            return category.name();
        }

    }