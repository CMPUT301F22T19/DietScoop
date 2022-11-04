package com.example.dietscoop;

import static java.lang.String.valueOf;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import java.time.LocalDate;

enum TypeIsNumeric {
    integer,
    decimal
}

public class IngredientAddFragment extends DialogFragment {
    private EditText description;
    private EditText amount;
    private EditText category;
    private EditText day;
    private EditText month;
    private EditText year;
    private EditText location;
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
    }

    public IngredientAddFragment(IngredientInStorage newStorageIngredient) {
        ingredientToBeChanged = newStorageIngredient;
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

    private static boolean isNumeric(String str, TypeIsNumeric t) {
        try {
            if (t.equals(TypeIsNumeric.integer)) {
                Integer.parseInt(str);
            } else if (t.equals(TypeIsNumeric.decimal)) {
                Double.parseDouble(str);
            }
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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

        if (ingredientToBeChanged == null) {
            builder
                .setView(view)
                .setTitle("Add ingredient")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String strDescription = description.getText().toString();
                        if (strDescription.length() == 0) {
                            strDescription = "No description available";
                        }

                        String strAmount = amount.getText().toString();
                        double doubleAmount;
                        if (!isNumeric(strAmount, TypeIsNumeric.decimal)) {
                            doubleAmount = -1.0;
                        } else {
                            doubleAmount = Double.parseDouble(strAmount);
                        }

                        int yearI;
                        int monthI;
                        int dayI;

                        String strYear = year.getText().toString();
                        if (!isNumeric(strYear, TypeIsNumeric.integer)) {
                            yearI = 1500;
                        } else {
                            yearI = Integer.valueOf(strYear);
                        }

                        String strMonth = month.getText().toString();
                        if (!isNumeric(strMonth, TypeIsNumeric.integer)) {
                            monthI = 1;
                        } else {
                            monthI = Integer.valueOf(strMonth);
                        }

                        String strDay = day.getText().toString();
                        if (!isNumeric(strDay, TypeIsNumeric.integer)) {
                            dayI = 1;
                        } else {
                            dayI = Integer.valueOf(strDay);
                        }


                        String strCategory = category.getText().toString().toLowerCase();
                        if (!strCategory.equals("vegetable") && !strCategory.equals("meat") && !strCategory.equals("fruit")) {
                            strCategory = "vegetable";
                        }

                        String strLocation = location.getText().toString().toLowerCase();
                        if (!strLocation.equalsIgnoreCase("pantry")
                                && !strLocation.equalsIgnoreCase("freezer")
                                && !strLocation.equalsIgnoreCase("freezer")) {
                            strLocation = "freezer";
                        }

                        String strUnit = unit.getText().toString();
                        if (strUnit.length() == 0) {
                            strUnit = "units";
                        }

                        Location location = Location.stringToLocation(strLocation);
                        Category ingredientCategory = Category.stringToCategory(strCategory);
                        listener.onOkPressed(new IngredientInStorage(strDescription, strUnit,
                                doubleAmount, yearI, monthI,
                                dayI, location, ingredientCategory));
                    }
                });
        } else {
            description.setText(ingredientToBeChanged.getDescription());
            amount.setText(valueOf(ingredientToBeChanged.getAmount()));

            if (ingredientToBeChanged.getLocation().equals(Location.pantry)) {
                locationString = "pantry";
            } else if (ingredientToBeChanged.getLocation().equals(Location.freezer)) {
                locationString = "freezer";
            } else if (ingredientToBeChanged.getLocation().equals(Location.fridge)) {
                locationString = "fridge";
            }
            location.setText(locationString);

            bestBeforeDateTemp = ingredientToBeChanged.getBestBeforeDate();

            if (ingredientToBeChanged.getCategory().equals(Category.vegetable)) {
                categoryString = "vegetable";
            } else if (ingredientToBeChanged.getCategory().equals(Category.meat)) {
                categoryString = "meat";
            } else if (ingredientToBeChanged.getCategory().equals(Category.fruit)) {
                categoryString = "fruit";
            }
            category.setText(categoryString);

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
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (description.getText().toString().length() == 0) {
                            ingredientToBeChanged.setDescription("No description available");
                        } else {
                            ingredientToBeChanged.setDescription(description.getText().toString());
                        }

                        int yearI;
                        int monthI;
                        int dayI;
                        if (!isNumeric(amount.getText().toString(), TypeIsNumeric.decimal)) {
                            ingredientToBeChanged.setAmount(0.0);
                        } else {
                            ingredientToBeChanged.setAmount(Double.parseDouble(amount.getText().toString()));
                        }

                        if (!isNumeric(year.getText().toString(), TypeIsNumeric.integer)) {
                            yearI = 2001;
                        } else {
                            yearI = Integer.parseInt(year.getText().toString());
                        }

                        if (!isNumeric(day.getText().toString(), TypeIsNumeric.integer)) {
                            dayI = 19;
                        } else {
                            dayI = Integer.parseInt(day.getText().toString());
                        }

                        if (!isNumeric(month.getText().toString(), TypeIsNumeric.integer)) {
                            monthI = 9;
                        } else {
                            monthI = Integer.parseInt(month.getText().toString());
                        }
                        ingredientToBeChanged.setBestBeforeDate(yearI, monthI, dayI);

                        if (!category.getText().toString().equals("vegetable") || !category.getText().toString().equals("meat") || !category.getText().toString().equals("fruit")) {
                            ingredientToBeChanged.setCategory(Category.stringToCategory("vegetable"));
                        } else {
                            ingredientToBeChanged.setCategory(Category.stringToCategory(category.getText().toString()));
                        }

                        if (!location.getText().toString().equalsIgnoreCase("pantry")
                                || !location.getText().toString().equalsIgnoreCase("freezer")
                                || !location.getText().toString().equalsIgnoreCase("fridge")) {
                            ingredientToBeChanged.setLocation(Location.stringToLocation("freezer"));
                        } else {
                            ingredientToBeChanged.setLocation(Location.stringToLocation(location.getText().toString().toLowerCase()));
                        }

                        if (unit.getText().toString().length() == 0) {
                            ingredientToBeChanged.setMeasurementUnit("kg");
                        } else {
                            ingredientToBeChanged.setMeasurementUnit(unit.getText().toString());
                        }
                    }
                });
        }
        return builder.create();
    }
}
