package com.example.dietscoop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;//for testing

public class MainActivity extends AppCompatActivity {

    Button temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        //testing code
//        ArrayList<IngredientInRecipe> listy = new ArrayList<IngredientInRecipe>();
//        listy.add(new IngredientInRecipe("chicken","kg",4,Category.meat));
//        listy.add(new IngredientInRecipe("butter","kg",4,Category.vegetable));
//        listy.add(new IngredientInRecipe("spice","kg",4,Category.fruit));
//        Recipe recipe = new Recipe("butTer chicken",125,4,timeUnit.minute,
//                recipeCategory.dinner,listy, "cook it nice");
//        recipeStorage = new RecipeStorage();
//        recipeStorage.addRecipeToStorage(recipe);


    }



    public void goToIngredientList(View view) {

//        //testing code
//        recipeStorage.removeRecipeFromStorage(recipeStorage.getRecipeStorage().get(0));

        Intent intent = new Intent(this, IngredientListActivity.class);
        startActivity(intent);
    }

    public void goToRecipeList(View view) {
        Intent intent = new Intent(this, RecipeListActivity.class);
        startActivity(intent);
    }
}