package com.example.dietscoop.Fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.dietscoop.Data.FoodItem;
import com.example.dietscoop.Data.Ingredient.Ingredient;
import com.example.dietscoop.Data.Ingredient.IngredientInStorage;
import com.example.dietscoop.Data.Ingredient.IngredientUnit;
import com.example.dietscoop.Data.Recipe.Recipe;
import com.example.dietscoop.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;

/**
 * This dialog fragment will manage the selection of FoodItems to add to our meal day.
 */
public class AddFoodItemFragment extends DialogFragment implements AdapterView.OnItemSelectedListener{
    Fragment context;

    Spinner foodItemSpinner;
    Spinner unitSelectSpinner;

    ArrayList<String> spinnerNames;
    ArrayList<String> unitNames;
    ArrayList<FoodItem> foodItems;

    TextView quantityView;
    EditText quantityInput;
    View dialogView;

    int defaultUnitIndex;
    int spinnerSelectNum;
    String currentMealType;
    IngredientUnit mealUnit; //This will be sent back to the mealday.

    Boolean editing;
    int indexToEdit; //Will handle what day to overwrite;

    public <T extends FoodItem> AddFoodItemFragment(Fragment context,ArrayList<T> foodItems) { //For adding a new foodItem.
        this.context = context;
        this.foodItems = (ArrayList<FoodItem>) foodItems;
        this.editing = false;
    }

    public <T extends FoodItem> AddFoodItemFragment(Fragment context,ArrayList<T> foodItems, int indexToEdit) { //For editing an existing foodItem.
        this.context = context;
        this.foodItems = (ArrayList<FoodItem>) foodItems;
        this.editing = true;
        this.indexToEdit = indexToEdit;
    }

    public Dialog onCreateDialog(Bundle savedInstance) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();

        dialogView = inflater.inflate(R.layout.add_food_item_fragment, null);

        initializeViews();
        populateSpinner();
        populateUnitSpinner();

        ArrayAdapter<String> stringUnitAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, unitNames);
        ArrayAdapter<String> stringSpinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spinnerNames);
        stringSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stringUnitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        foodItemSpinner.setAdapter(stringSpinnerAdapter);
        foodItemSpinner.setOnItemSelectedListener(this);
        unitSelectSpinner.setAdapter(stringUnitAdapter);

        if (editing) {

            builder.setView(dialogView)
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Checking for the item type:
                            Double scale = Double.parseDouble(quantityInput.getText().toString());
                            MealDayFragment testParent = (MealDayFragment)getParentFragment();
                            ((MealDayFragment)context).editMeal(spinnerSelectNum, scale, indexToEdit, mealUnit);
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

            //OnClick Spinner for the selection of unit types:
            unitSelectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (currentMealType.equals("Recipe")) {

                        unitSelectSpinner.setSelection(10); //Always defaulting to servings when the user uses a recipe.

                    } else if (currentMealType.equals("Ingredient")) {

                        //If user selects servings for an ingredient, prevent it:
                        if (i == 10) {
                            mealUnit = IngredientUnit.stringToUnit(unitNames.get(defaultUnitIndex));
                            unitSelectSpinner.setSelection(defaultUnitIndex);
                            return;
                        }

                        mealUnit = IngredientUnit.stringToUnit(unitNames.get(i));
                        unitSelectSpinner.setSelection(i);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    //Do nothing.
                }
            });

            setEditViewTexts();
            return builder.create();

        } else { //Not editing:

            builder.setView(dialogView)
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Checking for the item type:
                            Double scale = Double.parseDouble(quantityInput.getText().toString());
                            MealDayFragment testParent = (MealDayFragment)getParentFragment();
                            ((MealDayFragment)context).addMeal(spinnerSelectNum, scale, mealUnit);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //TODO: Implement the cancellation of a meal day.
                        }
                    });

            unitSelectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    //TODO: Finish the implementation of the spinner for the selection of unit. -> needs to passback to the program and database.
                    if (currentMealType.equals("Recipe")) {

                        unitSelectSpinner.setSelection(10); //Always defaulting to servings when the user uses a recipe.

                    } else if (currentMealType.equals("Ingredient")) {

                        //If user selects servings for an ingredient, prevent it:
                        if (i == 10) {
                            mealUnit = IngredientUnit.stringToUnit(unitNames.get(defaultUnitIndex));
                            unitSelectSpinner.setSelection(defaultUnitIndex);
                            return;
                        }

                        mealUnit = IngredientUnit.stringToUnit(unitNames.get(i));
                        unitSelectSpinner.setSelection(i);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    //Do nothing.
                }
            });

            return builder.create();

        }
    }

    private void setEditViewTexts() {
        int selection = ((MealDayFragment)context).getSelectedFoodItemIndex(this.indexToEdit); //Fetches the current foodItem(meal) selected in the editable mealday.
        foodItemSpinner.setSelection(selection, true);
        if (this.foodItems.get(selection).getDescription() == "Ingredient") {
            IngredientInStorage currentIngredient = (IngredientInStorage) this.foodItems.get(selection);
            int counter = 0;
            for (String i : unitNames) {
                if (currentIngredient.getMeasurementUnit().toString() == i) {
                    defaultUnitIndex = counter;
                    unitSelectSpinner.setSelection(defaultUnitIndex);
                }
                counter++;
            }
        } else {
            unitSelectSpinner.setSelection(10); //Will set the servings selection.
        }
    }

    public void initializeViews() {
        quantityView = (TextView) dialogView.findViewById(R.id.quantity_view);
        quantityView.setText(""); //Defaulting the measuring type to empty.
        quantityInput = (EditText) dialogView.findViewById(R.id.insert_quantity_food_item);
        foodItemSpinner = (Spinner) dialogView.findViewById(R.id.recycler_for_food_items);
        unitSelectSpinner = (Spinner) dialogView.findViewById(R.id.select_measurement_unit);
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

    public void populateUnitSpinner() {
        String[] units = {"ml", "l", "tsp", "tbsp", "cup", "lb", "oz", "mg", "g", "kg", "servings"};
        unitNames = new ArrayList(Arrays.asList(units));
    }

    //TODO: Here you can change the getText format to edit the view depending on the foodItem type.
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //Fetching the current selected description:

        if (foodItems.get(i).getType() == "Ingredient") {
            currentMealType = "Ingredient";
            Ingredient foodItem = (Ingredient) foodItems.get(i);
            quantityView.setText("");
            int index = unitNames.indexOf(foodItem.getMeasurementUnit().toString().toLowerCase(Locale.ROOT));
            unitSelectSpinner.setSelection(index);
            unitSelectSpinner.setVisibility(View.VISIBLE);
        } else {
            currentMealType = "Recipe";
            Recipe foodItem = (Recipe) foodItems.get(i);
            quantityView.setText("Servings");
            unitSelectSpinner.setSelection(10);
            unitSelectSpinner.setVisibility(View.GONE);
        }
        spinnerSelectNum = i;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //Do nothing.
    }
}
