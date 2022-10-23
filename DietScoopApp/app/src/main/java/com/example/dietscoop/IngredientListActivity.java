package com.example.dietscoop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class IngredientListActivity extends AppCompatActivity {

    FoodStorage foodStorage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_list);

        foodStorage = new FoodStorage();

    }
}