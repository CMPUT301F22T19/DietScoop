package com.example.dietscoop.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dietscoop.Data.Ingredient.IngredientCategory;
import com.example.dietscoop.Data.Ingredient.IngredientInRecipe;
import com.example.dietscoop.Data.Ingredient.IngredientUnit;
import com.example.dietscoop.R;


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
        AlertDialog alertDialogTemp;
        boolean isEditing;
        if (this.ingredientToBeChanged == null)
        {
            alertDialogTemp = buildAddDialog(builder, view);
            isEditing = false;
        }
        else {
            isEditing = true;
            alertDialogTemp = buildEditDialog(builder, view);
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

        }
        final AlertDialog alertDialog = alertDialogTemp;
        errorCheck(description,amount,alertDialog,isEditing);

        return alertDialogTemp;
    }

    private AlertDialog buildAddDialog(AlertDialog.Builder builder, View view) {
        return builder
                .setView(view)
                .setTitle("Add to Recipe")
                .setPositiveButton("Confirm", null)
                .setNegativeButton("Cancel", null)
                .create();
    }

    private AlertDialog buildEditDialog(AlertDialog.Builder builder, View view) {
        return builder
                .setView(view)
                .setTitle("Modify Ingredient")
                .setPositiveButton("Modify", null)
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onDeletePressed(ingredientToBeChanged);
                    }
                })
                .setNeutralButton("Cancel", null)
                .create();
    }

    private void errorCheck(TextView description, TextView amount, AlertDialog alertDialog, boolean isEditing) {
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        boolean isAllValid = true;
                        String descText = description.getText().toString();
                        if (descText.equals("")) {
                            description.setError("Please enter a description!");
                            isAllValid = false;
                        }
                        if (amount.getText().toString().equals("")) {
                            amount.setError("Please enter an amount!");
                            isAllValid = false;
                        }

                        if (isAllValid) {
                            if (isEditing) {
                                ingredientToBeChanged.setDescription(descText);
                                ingredientToBeChanged.setAmount(Double.parseDouble(amount.getText().toString()));
                                ingredientToBeChanged.setCategory(IngredientCategory.stringToCategory
                                        (categorySpinner.getSelectedItem().toString()));
                                ingredientToBeChanged.setMeasurementUnit(IngredientUnit.stringToUnit
                                        (unitSpinner.getSelectedItem().toString()));
                                listener.onOkPressedUpdate(ingredientToBeChanged, indexOfIngToBeUpdated);
                                alertDialog.dismiss();
                            } else {
                                IngredientCategory ingocat = IngredientCategory.stringToCategory(categorySpinner.getSelectedItem().toString());
                                IngredientUnit unitText = IngredientUnit.stringToUnit(unitSpinner.getSelectedItem().toString());
                                IngredientInRecipe newIngredientInRecipe = new IngredientInRecipe(descText, unitText,
                                        Double.parseDouble(amount.getText().toString()), ingocat);
                                listener.onOkPressed(newIngredientInRecipe);
                                alertDialog.dismiss();
                            }
                        }
                    }
                });
            }
        });
    }
}

