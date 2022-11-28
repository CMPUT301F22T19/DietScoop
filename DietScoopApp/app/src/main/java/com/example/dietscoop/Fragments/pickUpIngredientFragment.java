package com.example.dietscoop.Fragments;

import androidx.fragment.app.DialogFragment;

import com.example.dietscoop.Data.Ingredient.IngredientInRecipe;
import com.example.dietscoop.Data.Ingredient.IngredientInStorage;

public class pickUpIngredientFragment extends DialogFragment {

    private IngredientInRecipe ingredient;

    private OnFragmentInteractionListener listener;

    public interface OnFragmentInteractionListener {
        void onAddPressed(IngredientInStorage ingredient);
    }

    public pickUpIngredientFragment(IngredientInRecipe ingredient) {
        this.ingredient = ingredient;
    }

}
