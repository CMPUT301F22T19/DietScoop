package com.example.dietscoop;

import java.util.ArrayList;

public class FoodStorage {
    ArrayList<IngredientInStorage> storage;
    Database db;

    public FoodStorage() {
        db = new Database();
        storage = db.getIngredientList();
    }

    public void addIngredientToStorage(IngredientInStorage ingredientInStorage) {
        db.addIngredientToStorage(ingredientInStorage);
    }
}
