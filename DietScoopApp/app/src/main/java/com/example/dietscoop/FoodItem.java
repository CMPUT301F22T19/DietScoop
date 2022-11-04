package com.example.dietscoop;

import java.io.Serializable;

public abstract class FoodItem implements Serializable {
    protected String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
