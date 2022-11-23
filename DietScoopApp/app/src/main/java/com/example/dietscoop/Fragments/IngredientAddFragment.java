package com.example.dietscoop.Fragments;

import static java.lang.String.valueOf;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.example.dietscoop.Activities.MainActivity;
import com.example.dietscoop.Data.Ingredient.IngredientCategory;
import com.example.dietscoop.Data.Ingredient.IngredientInStorage;
import com.example.dietscoop.Data.Ingredient.IngredientUnit;
import com.example.dietscoop.Data.Ingredient.Location;
import com.example.dietscoop.R;
import com.example.dietscoop.Data.Recipe.NumericTypes;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;

public class IngredientAddFragment extends DialogFragment {
    private static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    private EditText description;
    private EditText amount;
    private Spinner category;
    private Spinner location;
    private Spinner unit;
    private OnFragmentInteractionListener listener;
    private IngredientInStorage ingredientToBeChanged;
    private String locationString;
    private String categoryString;
    private LocalDate bestBeforeDateTemp;
    private Button selectDate;
    private TextView bestBeforeDate;
    private Button addByCameraRoll;
    private Button addByCamera;

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
        addByCameraRoll = view.findViewById(R.id.add_ingredient_photo_camera_roll);
        addByCamera = view.findViewById(R.id.add_ingredient_photo_camera);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        ArrayAdapter<CharSequence> categorySpinnerAdapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.IngredientInStorageCategory, android.R.layout.simple_spinner_item);
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(categorySpinnerAdapter);

        ArrayAdapter<CharSequence> locationSpinnerAdapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.IngredientInStorageLocation, android.R.layout.simple_spinner_item);
        locationSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location.setAdapter(locationSpinnerAdapter);

        ArrayAdapter<CharSequence> unitSpinnerAdapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.IngredientInStorageUnit, android.R.layout.simple_spinner_item);
        unitSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        unit.setAdapter(unitSpinnerAdapter);

        IngredientInStorage newIngredient = new IngredientInStorage();

        addByCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askCameraPermission();
            }
        });

        addByCameraRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

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

        unit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String unitString = adapterView.getItemAtPosition(position).toString();
                IngredientUnit unit = IngredientUnit.stringToUnit(unitString);
                if(ingredientToBeChanged == null) {
                    newIngredient.setMeasurementUnit(unit);
                } else {
                    ingredientToBeChanged.setMeasurementUnit(unit);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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

                    newIngredient.setDescription(strDescription);
                    newIngredient.setAmount(doubleAmount);

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

                    listener.onOkPressedUpdate(ingredientToBeChanged);
                });
        }
        return builder.create();
    }

    private void askCameraPermission() {
        if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) this.getContext(),
                    new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        } else {
            openDeviceBuiltInCamera();
        }
    }

    private void openDeviceBuiltInCamera() {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERM_CODE) {
            if (grantResults.length > 0 && grantResults[0] > PackageManager.PERMISSION_GRANTED) {
                openDeviceBuiltInCamera();
            }
        } else {

        }
    }
}
