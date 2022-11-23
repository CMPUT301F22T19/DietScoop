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
import com.example.dietscoop.Data.Ingredient.Ingredient;
import com.example.dietscoop.Data.Ingredient.IngredientCategory;
import com.example.dietscoop.Data.Ingredient.IngredientInRecipe;
import com.example.dietscoop.Data.Ingredient.IngredientInStorage;
import com.example.dietscoop.Data.Ingredient.IngredientUnit;
import com.example.dietscoop.Database.RecipeStorage;
import com.example.dietscoop.R;

import java.util.List;

public class AddIngredientToRecipeFragment extends DialogFragment {
    private AddIngredientToRecipeFragment.OnFragmentInteractionListener listener;
    private IngredientInRecipe ingredientToBeChanged;
    private int indexOfIngToBeUpdated;


    public AddIngredientToRecipeFragment(IngredientInRecipe newRecipeIngredient, int position) {
        this.ingredientToBeChanged = newRecipeIngredient;
        this.indexOfIngToBeUpdated = position;
    }

    public AddIngredientToRecipeFragment() {
        this.ingredientToBeChanged = null;
    }

    public interface OnFragmentInteractionListener {
        void onOkPressed(IngredientInRecipe newIngredientInRecipe);
        void onOkPressedUpdate(IngredientInRecipe updateIngredientInRecipe, int indexOfIngToBeUpdated);
        void onDeletePressed(IngredientInRecipe deleteIngredientInRecipe);
    }

    Spinner categorySpinner;
    EditText description;
    EditText amount;
    Spinner unitSpinner;

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
        unitSpinner = view.findViewById(R.id.unitSpinner);
        unitSpinner.setAdapter(new ArrayAdapter<IngredientUnit>(this.getActivity(),
                android.R.layout.simple_spinner_dropdown_item, IngredientUnit.values()));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (this.ingredientToBeChanged == null)
        {
            return builder
                    .setView(view)
                    .setTitle("Add")
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            String descText = description.getText().toString();
                            IngredientCategory ingocat =  IngredientCategory.stringToCategory(categorySpinner.getSelectedItem().toString());
                            IngredientUnit unitText = IngredientUnit.stringToUnit(unitSpinner.getSelectedItem().toString());
                            Double amountDouble = Double.parseDouble(amount.getText().toString());
                            IngredientInRecipe newIngredientInRecipe = new IngredientInRecipe(descText, unitText,
                                    amountDouble, ingocat);
                            listener.onOkPressed(newIngredientInRecipe);

                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .create();
        }
        else {

            description.setText(this.ingredientToBeChanged.getDescription());
            this.ingredientToBeChanged.getCategory();
            IngredientCategory catValues[] = IngredientCategory.values();
//          Populate spinner
            for(int i = 0; i < catValues.length; ++i)
            {
                if(this.ingredientToBeChanged.getCategory() == catValues[i])
                {
                    categorySpinner.setSelection(i);
                    break;
                }
            }

            IngredientUnit unitValues[] = IngredientUnit.values();

            for(int i = 0; i < unitValues.length; ++i)
            {
                if(this.ingredientToBeChanged.getMeasurementUnit() == unitValues[i])
                {
                    unitSpinner.setSelection(i);
                    break;
                }
            }

            amount.setText(Double.toString(this.ingredientToBeChanged.getAmount()));

            return builder
                    .setView(view)
                    .setTitle("Modify")
                    .setPositiveButton("Modify", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            String descText = description.getText().toString();
                            Double amountDouble = Double.parseDouble(amount.getText().toString());

                            ingredientToBeChanged.setMeasurementUnit(IngredientUnit.stringToUnit
                                    (unitSpinner.getSelectedItem().toString()));
                            ingredientToBeChanged.setDescription(descText);
                            ingredientToBeChanged.setCategory(IngredientCategory.stringToCategory
                                            (categorySpinner.getSelectedItem().toString()));
                            ingredientToBeChanged.setAmount(amountDouble);
                            Log.i("ing. Test", ingredientToBeChanged.getId());
                            listener.onOkPressedUpdate(ingredientToBeChanged, indexOfIngToBeUpdated);

                        }
                    })
                    .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            listener.onDeletePressed(ingredientToBeChanged);
                        }
                    })
                    .setNeutralButton("Cancel", null)
                    .create();
        }
    }
}