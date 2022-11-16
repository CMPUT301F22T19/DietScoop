package com.example.dietscoop.Fragments;

import static java.lang.String.valueOf;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.example.dietscoop.Data.Ingredient.IngredientCategory;
import com.example.dietscoop.Data.Ingredient.IngredientInStorage;
import com.example.dietscoop.Data.Ingredient.Location;
import com.example.dietscoop.R;
import com.example.dietscoop.Data.Recipe.NumericTypes;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;

public class IngredientAddFragment extends DialogFragment {
    private EditText description;
    private EditText amount;
    private Spinner category;
    private EditText day;
    private EditText month;
    private EditText year;
    private Spinner location;
    private EditText unit;
    private OnFragmentInteractionListener listener;
    private IngredientInStorage ingredientToBeChanged;
    private String locationString;
    private String categoryString;
    private LocalDate bestBeforeDateTemp;
    // For getting the string version of Calendar
    // Error handing
    public interface OnFragmentInteractionListener {
        void onOkPressed(IngredientInStorage newIngredientInStorage);
        void onOkPressedUpdate(IngredientInStorage updateIngredientInStorage);
        void onDeletePressed(IngredientInStorage deleteIngredientInStorage);
    }

    public IngredientAddFragment(IngredientInStorage newStorageIngredient) {
        this.ingredientToBeChanged = newStorageIngredient;
    }

    public IngredientAddFragment() {
        ingredientToBeChanged = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context + " must implement OnFragmentInteractionListener");
        }
    }

    private static boolean isNumeric(String str, NumericTypes t) {
        try {
            if (t.equals(NumericTypes.integer)) {
                Integer.parseInt(str);
            } else if (t.equals(NumericTypes.decimal)) {
                Double.parseDouble(str);
            }
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_ingredient_storage_fragment, null);
        description = view.findViewById(R.id.edit_description_ingredient_storage);
        amount = view.findViewById(R.id.edit_amount_ingredient_storage);
        category = view.findViewById(R.id.edit_category_ingredient_storage);
        location = view.findViewById(R.id.edit_location_ingredient_storage);
        unit = view.findViewById(R.id.edit_unit_ingredient_storage);
        day = view.findViewById(R.id.edit_day_ingredient_storage);
        month = view.findViewById(R.id.edit_month_ingredient_storage);
        year = view.findViewById(R.id.edit_year_ingredient_storage);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        ArrayAdapter<CharSequence> categorySpinnerAdapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.IngredientInStorageCategory, android.R.layout.simple_spinner_item);
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(categorySpinnerAdapter);

        ArrayAdapter<CharSequence> locationSpinnerAdapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.IngredientInStorageLocation, android.R.layout.simple_spinner_item);
        locationSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location.setAdapter(locationSpinnerAdapter);

        IngredientInStorage newIngredient = new IngredientInStorage();

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String strCategory = parent.getItemAtPosition(position).toString();
                IngredientCategory ingredientCategory = IngredientCategory.stringToCategory(strCategory);
                newIngredient.setCategory(ingredientCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String strLocation = parent.getItemAtPosition(position).toString();
                Location location = Location.stringToLocation(strLocation);
                newIngredient.setLocation(location);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        final Calendar c = Calendar.getInstance();

        if (ingredientToBeChanged == null) {
            builder
                .setView(view)
                .setTitle("Add ingredient")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", (dialogInterface, i) -> {

                    String strDescription = description.getText().toString();
                    if (strDescription.length() == 0) {
                        strDescription = "No description available";
                    }

                    String strAmount = amount.getText().toString();
                    double doubleAmount;
                    if (!isNumeric(strAmount, NumericTypes.decimal)) {
                        doubleAmount = -1.0;
                    } else {
                        doubleAmount = Double.parseDouble(strAmount);
                    }

                    int yearI;
                    int monthI;
                    int dayI;

                    String strYear = year.getText().toString();
                    if (!isNumeric(strYear, NumericTypes.integer)) {
                        yearI = 1500;
                    } else {
                        yearI = Integer.parseInt(strYear);
                    }

                    String strMonth = month.getText().toString();
                    if (!isNumeric(strMonth, NumericTypes.integer)) {
                        monthI = 1;
                    } else {
                        monthI = Integer.parseInt(strMonth);
                    }

                    String strDay = day.getText().toString();
                    if (!isNumeric(strDay, NumericTypes.integer)) {
                        dayI = 1;
                    } else {
                        dayI = Integer.parseInt(strDay);
                    }

                    String strUnit = unit.getText().toString();
                    if (strUnit.length() == 0) {
                        strUnit = "units";
                    }

                    newIngredient.setDescription(strDescription);
                    newIngredient.setMeasurementUnit(strUnit);
                    newIngredient.setAmount(doubleAmount);
                    newIngredient.setBestBeforeDate(yearI, monthI, dayI);

                    listener.onOkPressed(newIngredient);
                });
        } else {
            description.setText(ingredientToBeChanged.getDescription());
            amount.setText(valueOf(ingredientToBeChanged.getAmount()));

            if (ingredientToBeChanged.getLocation().equals(Location.Pantry)) {
                locationString = "pantry";
            } else if (ingredientToBeChanged.getLocation().equals(Location.Freezer)) {
                locationString = "freezer";
            } else if (ingredientToBeChanged.getLocation().equals(Location.Fridge)) {
                locationString = "fridge";
            }

            bestBeforeDateTemp = ingredientToBeChanged.getBestBeforeDate();

            int dayOfMonth = bestBeforeDateTemp.getDayOfMonth();
            String dayOfMonthStr = valueOf(dayOfMonth);
            day.setText(dayOfMonthStr);
            int monthInt = bestBeforeDateTemp.getMonthValue();
            String monthStr = valueOf(monthInt);
            month.setText(monthStr);
            int yearInt = bestBeforeDateTemp.getYear();
            String yearStr = valueOf(yearInt);
            year.setText(yearStr);
            unit.setText(ingredientToBeChanged.getMeasurementUnit());

            builder
                .setView(view)
                .setTitle("Modify ingredient")
                .setNegativeButton("Cancel", null)
                    .setNeutralButton("Delete", (dialog, which) -> listener.onDeletePressed(ingredientToBeChanged))
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    if (description.getText().toString().length() == 0) {
                        ingredientToBeChanged.setDescription("No description available");
                    } else {
                        ingredientToBeChanged.setDescription(description.getText().toString());
                    }

                    int yearI;
                    int monthI;
                    int dayI;
                    if (!isNumeric(amount.getText().toString(), NumericTypes.decimal)) {
                        ingredientToBeChanged.setAmount(0.0);
                    } else {
                        ingredientToBeChanged.setAmount(Double.parseDouble(amount.getText().toString()));
                    }

                    if (!isNumeric(year.getText().toString(), NumericTypes.integer)) {
                        yearI = 2001;
                    } else {
                        yearI = Integer.parseInt(year.getText().toString());
                    }

                    if (!isNumeric(day.getText().toString(), NumericTypes.integer)) {
                        dayI = 19;
                    } else {
                        dayI = Integer.parseInt(day.getText().toString());
                    }

                    if (!isNumeric(month.getText().toString(), NumericTypes.integer)) {
                        monthI = 9;
                    } else {
                        monthI = Integer.parseInt(month.getText().toString());
                    }
                    ingredientToBeChanged.setBestBeforeDate(yearI, monthI, dayI);

                    if (unit.getText().toString().length() == 0) {
                        ingredientToBeChanged.setMeasurementUnit("kg");
                    } else {
                        ingredientToBeChanged.setMeasurementUnit(unit.getText().toString());
                    }
                    listener.onOkPressedUpdate(ingredientToBeChanged);
                });
        }
        return builder.create();
    }
}
