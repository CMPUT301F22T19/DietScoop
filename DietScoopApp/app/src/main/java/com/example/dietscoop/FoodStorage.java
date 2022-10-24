package com.example.dietscoop;

import java.util.ArrayList;

public class FoodStorage {
    private ArrayList<IngredientInStorage> storage;
    Database db;

    public FoodStorage() {
        db = new Database();
        storage = db.getIngredientStorage();
    }

    public void addIngredientToStorage(IngredientInStorage ingredientInStorage) {
        db.addIngredientToStorage(ingredientInStorage);
    }

    public ArrayList<IngredientInStorage> getIngredientStorage() {
        return storage;
    }
}
