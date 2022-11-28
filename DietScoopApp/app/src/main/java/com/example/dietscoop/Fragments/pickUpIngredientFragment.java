package com.example.dietscoop.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.dietscoop.Data.Ingredient.Ingredient;
import com.example.dietscoop.Data.Ingredient.IngredientCategory;
import com.example.dietscoop.Data.Ingredient.IngredientInRecipe;
import com.example.dietscoop.Data.Ingredient.IngredientInStorage;
import com.example.dietscoop.Data.Ingredient.IngredientUnit;
import com.example.dietscoop.Data.Ingredient.Location;
import com.example.dietscoop.Data.Ingredient.UnitConverter;
import com.example.dietscoop.R;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.util.Calendar;

public class pickUpIngredientFragment extends DialogFragment {

    private IngredientInRecipe ingredient;

    private OnFragmentInteractionListener listener;

    private EditText amountText;
    private Spinner unitSpinner, locationSpinner;
    private Button selectDateButton;
    private TextView bestBeforeDateTV;

    int selectedYear, selectedMonth, selectedDay;

    public interface OnFragmentInteractionListener {
        void onAddPressed(IngredientInStorage ingredient);
    }

    public pickUpIngredientFragment(IngredientInRecipe ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context + " must implement OnFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_pickup_shopping_item, null);

        bestBeforeDateTV = view.findViewById(R.id.pickup_date_text);
        selectDateButton = view.findViewById(R.id.pickup_select_date);
        amountText = view.findViewById(R.id.pickup_amount);
        unitSpinner = view.findViewById(R.id.pickup_unit);
        locationSpinner = view.findViewById(R.id.pickup_location);

        unitSpinner.setAdapter(new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_spinner_dropdown_item, IngredientUnit.values()));

        locationSpinner.setAdapter(new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_spinner_dropdown_item, Location.values()));

        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView selectedView = (TextView)view;
                IngredientUnit selected = IngredientUnit.stringToUnit(selectedView.getText().toString());

                if (UnitConverter.getUnitType(selected) != UnitConverter.getUnitType(ingredient.getMeasurementUnit())) {
                    selectedView.setError("Unit type does not match!");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(pickUpIngredientFragment.this.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        selectedDay = dayOfMonth;
                        selectedMonth = month + 1;
                        selectedYear = year;

                        bestBeforeDateTV.setText(selectedYear + "-" + selectedMonth + "-" + selectedDay);
                    }
                }, currentYear, currentMonth, currentDayOfMonth);
                datePickerDialog.show();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        return builder
                .setView(view)
                .setTitle("Pickup Ingredient")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String description = ingredient.getDescription();
                        IngredientCategory ingredientCategory = ingredient.getCategory();

                        IngredientUnit unit = IngredientUnit.stringToUnit(unitSpinner.getSelectedItem().toString());
                        Location location = Location.stringToLocation(locationSpinner.getSelectedItem().toString());
                        Double amount = Double.valueOf(amountText.getText().toString());

                        IngredientInStorage newIngredient = new IngredientInStorage(description, unit, amount,
                                selectedYear, selectedMonth, selectedDay, location, ingredientCategory);

                        listener.onAddPressed(newIngredient);
                    }
                }).setNegativeButton("Cancel", null)
                .create();


    }
}
