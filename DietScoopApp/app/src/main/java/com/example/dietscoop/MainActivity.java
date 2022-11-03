package com.example.dietscoop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    Button temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_recipe);
    }

    public void goToIngredientList(View view) {
        Intent intent = new Intent(this, IngredientListActivity.class);
        startActivity(intent);
    }

    public void goToRecipeList(View view) {
        Intent intent = new Intent(this, RecipeListActivity.class);
        startActivity(intent);
    }
}