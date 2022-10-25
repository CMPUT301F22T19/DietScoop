package com.example.dietscoop;

import java.util.ArrayList;

public class FoodStorage {
    private ArrayList<IngredientInStorage> storage;
    Database db;

    public FoodStorage() {
        db = new Database();
        storage = new ArrayList<IngredientInStorage>();
    }

    public void addIngredientToStorage(IngredientInStorage ingredientInStorage) {
        db.addIngredientToStorage(ingredientInStorage);
    }

    public void removeIngredientFromStorage(IngredientInStorage ingredientInStorage) {
        db.removeIngredientFromStorage(ingredientInStorage);
    }

    public ArrayList<IngredientInStorage> getIngredientStorage() {
        return storage;
    }

    public void getIngredientStorageFromDatabase(IngredientStorageAdapter adapter) {
        storage = db.getIngredientStorage(adapter);
    }
}
