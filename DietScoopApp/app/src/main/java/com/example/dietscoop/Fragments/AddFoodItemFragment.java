package com.example.dietscoop.Fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.DialogFragment;

import com.example.dietscoop.Data.FoodItem;
import com.example.dietscoop.R;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This dialog fragment will manage the selection of FoodItems to add to our meal day.
 */
public class AddFoodItemFragment extends DialogFragment {

    Spinner foodItemSpinner;
    ArrayList<String> spinnerNames;
    ArrayList<FoodItem> foodItems;
    EditText quantityInput;
    View dialogView;

    public <T extends FoodItem> AddFoodItemFragment(ArrayList<T> foodItems) {
        this.foodItems = (ArrayList<FoodItem>) foodItems;
    }

    public Dialog onCreateDialog(Bundle savedInstance) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        dialogView = inflater.inflate(R.layout.add_food_item_fragment, null);

        initializeViews();
        populateSpinner();

        ArrayAdapter<String> stringSpinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spinnerNames);
        stringSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        foodItemSpinner.setAdapter(stringSpinnerAdapter);

        //Inflating the fragment: ***
        builder.setView(dialogView)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //TODO: Implement the acceptance of a meal day.
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //TODO: Implement the cancellation of a meal day.
                    }
                });

        return builder.create();

    }

    public void initializeViews() {
        quantityInput = (EditText) dialogView.findViewById(R.id.insert_quantity_food_item);
        foodItemSpinner = (Spinner) dialogView.findViewById(R.id.recycler_for_food_items);
    }

    /**
     * Populates ArrayList with a list of strings of the different food items:
     */
    public void populateSpinner() {
        spinnerNames = new ArrayList<>();
        Iterator<FoodItem> foodItemIterator = foodItems.iterator();
        while(foodItemIterator.hasNext()) {
            spinnerNames.add(foodItemIterator.next().getDescription());
        }
    }

}
