package com.example.dietscoop.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.ExifInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.dietscoop.Activities.ViewRecipeActivity;
import com.example.dietscoop.Data.Ingredient.IngredientCategory;
import com.example.dietscoop.Data.Ingredient.IngredientInRecipe;
import com.example.dietscoop.Data.Ingredient.IngredientInStorage;
import com.example.dietscoop.R;

import java.util.List;

public class AddIngredientToRecipeFragment extends DialogFragment {
    private AddIngredientToRecipeFragment.OnFragmentInteractionListener listener;


    public interface OnFragmentInteractionListener {
        void onOkPressed(IngredientInRecipe newIngredientInRecipe);
//        void onOkPressedUpdate(IngredientInStorage updateIngredientInStorage);
//        void onDeletePressed(IngredientInStorage deleteIngredientInStorage);
    }
    Spinner categorySpinner;
    EditText description;
    EditText amount;
    EditText unit;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AddIngredientToRecipeFragment.OnFragmentInteractionListener) {
            listener = (AddIngredientToRecipeFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context + " must implement OnFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_ingredient_to_recipe, null);

        categorySpinner = (Spinner) view.findViewById(R.id.categorySpinner);
        categorySpinner.setAdapter(new ArrayAdapter<IngredientCategory>(this.getActivity(),
                android.R.layout.simple_spinner_dropdown_item, IngredientCategory.values()));

        description = view.findViewById(R.id.editTextDescription);
        amount = view.findViewById(R.id.editTextAmount);
        unit = view.findViewById(R.id.editTextUnit);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder
                .setView(view)
                .setTitle("End ME")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String descText = description.getText().toString();
                        IngredientCategory ingocat =  IngredientCategory.stringToCategory(categorySpinner.getSelectedItem().toString());
                        String unitText = unit.getText().toString();
                        int amountFloat = Integer.parseInt(amount.getText().toString());
                       IngredientInRecipe newIngredientInRecipe = new IngredientInRecipe(descText, unitText,
                               amountFloat, ingocat);
                        listener.onOkPressed(newIngredientInRecipe);

                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
    }

}