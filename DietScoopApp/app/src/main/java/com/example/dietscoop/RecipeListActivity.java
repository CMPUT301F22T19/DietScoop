package com.example.dietscoop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class RecipeListActivity extends AppCompatActivity {
    ListView recipeListView;
    ArrayList<Recipe> recipes;
    RecipeListAdapter recipeListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        recipeListView = findViewById(R.id.recipe_list);


        recipeListAdapter = new RecipeListAdapter(this, recipes);
        recipeListView.setAdapter(recipeListAdapter);


    }
}