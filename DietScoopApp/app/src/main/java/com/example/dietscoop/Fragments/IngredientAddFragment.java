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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.example.dietscoop.Activities.MainActivity;
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
    private Spinner location;
    private EditText unit;
    private OnFragmentInteractionListener listener;
    private IngredientInStorage ingredientToBeChanged;
    private String locationString;
    private String categoryString;
    private LocalDate bestBeforeDateTemp;
    private Button selectDate;
    private TextView bestBeforeDate;
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
        selectDate = view.findViewById(R.id.select_bestbefore_button);
        bestBeforeDate = view.findViewById(R.id.bestBeforeDateAddIngredientToStorage);
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
                if(ingredientToBeChanged == null) {
                    newIngredient.setCategory(ingredientCategory);
                } else {
                    ingredientToBeChanged.setCategory(ingredientCategory);
                }
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
                if(ingredientToBeChanged == null) {
                    newIngredient.setLocation(location);
                } else {
                    ingredientToBeChanged.setLocation(location);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(IngredientAddFragment.this.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        if(ingredientToBeChanged == null){
                            newIngredient.setBestBeforeDate(year, month + 1, dayOfMonth);
                        } else {
                            ingredientToBeChanged.setBestBeforeDate(year, month + 1, dayOfMonth);
                        }
                        bestBeforeDate.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        if (ingredientToBeChanged != null) {
            LocalDate date = ingredientToBeChanged.getBestBeforeDate();
            int yearDate = date.getYear();
            int monthDate = date.getMonthValue();
            int dayDate = date.getDayOfMonth();
            bestBeforeDate.setText(yearDate + "-" + monthDate + "-" + dayDate);
            int categorySpinnerPosition = categorySpinnerAdapter.getPosition(ingredientToBeChanged.getCategoryName());
            category.setSelection(categorySpinnerPosition);
            int locationSpinnerPosition = locationSpinnerAdapter.getPosition(ingredientToBeChanged.getLocationName());
            location.setSelection(locationSpinnerPosition);
        }

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

                    String strUnit = unit.getText().toString();
                    if (strUnit.length() == 0) {
                        strUnit = "units";
                    }

                    newIngredient.setDescription(strDescription);
                    newIngredient.setMeasurementUnit(strUnit);
                    newIngredient.setAmount(doubleAmount);

                    listener.onOkPressed(newIngredient);
                });
        } else {
            description.setText(ingredientToBeChanged.getDescription());
            amount.setText(valueOf(ingredientToBeChanged.getAmount()));
            unit.setText(ingredientToBeChanged.getMeasurementUnit());

            if (ingredientToBeChanged.getLocation().equals(Location.Pantry)) {
                locationString = "pantry";
            } else if (ingredientToBeChanged.getLocation().equals(Location.Freezer)) {
                locationString = "freezer";
            } else if (ingredientToBeChanged.getLocation().equals(Location.Fridge)) {
                locationString = "fridge";
            }

            bestBeforeDateTemp = ingredientToBeChanged.getBestBeforeDate();

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

                    if (!isNumeric(amount.getText().toString(), NumericTypes.decimal)) {
                        ingredientToBeChanged.setAmount(0.0);
                    } else {
                        ingredientToBeChanged.setAmount(Double.parseDouble(amount.getText().toString()));
                    }

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
