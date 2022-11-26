package com.example.dietscoop.Fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.dietscoop.Data.FoodItem;
import com.example.dietscoop.Data.Ingredient.Ingredient;
import com.example.dietscoop.Data.Recipe.Recipe;
import com.example.dietscoop.R;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This dialog fragment will manage the selection of FoodItems to add to our meal day.
 */
public class AddFoodItemFragment extends DialogFragment implements AdapterView.OnItemSelectedListener{

    //Testing:
    Fragment context;

    Spinner foodItemSpinner;

    ArrayList<String> spinnerNames;
    ArrayList<FoodItem> foodItems;

    TextView quantityView;
    EditText quantityInput;
    View dialogView;

    int spinnerSelectNum;
    FoodItem editMeal;
    String currentMealType;

    Boolean editing;
    int indexToEdit; //Will handle what day to overwrite;

    public <T extends FoodItem> AddFoodItemFragment(Fragment context,ArrayList<T> foodItems) {
        this.context = context;
        this.foodItems = (ArrayList<FoodItem>) foodItems;
        this.editing = false;
    }

    public <T extends FoodItem> AddFoodItemFragment(Fragment context,ArrayList<T> foodItems, int indexToEdit) {
        this.context = context;
        this.foodItems = (ArrayList<FoodItem>) foodItems;
        this.editing = true;
        this.indexToEdit = indexToEdit;
    }

//    public <T extends FoodItem> AddFoodItemFragment(ArrayList<T> foodItems, FoodItem editItem) {
//        this.foodItems = (ArrayList<FoodItem>) foodItems;
//        this.editMeal = editItem;
//
//        if (editItem instanceof IngredientInStorage) {
//
//        }
//    }

    public Dialog onCreateDialog(Bundle savedInstance) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();

        dialogView = inflater.inflate(R.layout.add_food_item_fragment, null);

        initializeViews();
        populateSpinner();

        if (editing) {
            setEditViewTexts();
        }

        ArrayAdapter<String> stringSpinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spinnerNames);
        stringSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        foodItemSpinner.setAdapter(stringSpinnerAdapter);
        foodItemSpinner.setOnItemSelectedListener(this);

        if (editing) {

            //TODO: Add control logic to depict the kind of builder layout invoked:
            builder.setView(dialogView)
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Checking for the item type:
                            Integer scale = Integer.parseInt(quantityInput.getText().toString());
                            MealDayFragment testParent = (MealDayFragment)getParentFragment();
                            ((MealDayFragment)context).editMeal(spinnerSelectNum, scale, indexToEdit);
                        }
                    })
                    .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ((MealDayFragment)context).deleteMeal(indexToEdit);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Do nothing.
                        }
                    });
            return builder.create();

        } else { //Not editing:

            //TODO: Add control logic to depict the kind of builder layout invoked:
            builder.setView(dialogView)
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Checking for the item type:
                            Integer scale = Integer.parseInt(quantityInput.getText().toString());
                            MealDayFragment testParent = (MealDayFragment)getParentFragment();
                            ((MealDayFragment)context).addMeal(spinnerSelectNum, scale);
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
    }

    private void setEditViewTexts() {

    }

    public void initializeViews() {
        quantityView = (TextView) dialogView.findViewById(R.id.quantity_view);
        quantityView.setText(""); //Defaulting the measuring type to empty.

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


    //TODO: Here you can change the getText format to edit the view depending on the foodItem type.
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //Fetching the current selected description:
        if (foodItems.get(i).getType() == "Ingredient") {
            Ingredient foodItem = (Ingredient) foodItems.get(i);
            quantityView.setText(foodItem.getMeasurementUnit().toString());
        } else {
            Recipe foodItem = (Recipe) foodItems.get(i);
            quantityView.setText("Servings");
        }
        spinnerSelectNum = i;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //Do nothing.
    }
}
