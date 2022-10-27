package com.example.dietscoop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * This class acts as a container for the IngredientInStorage Objects retrieved from Database.
 * Also wraps the behaviour of setting the main ListView activity for the
 */
public class IngredientStorageAdapter extends ArrayAdapter<IngredientInStorage> {

    private Context context;
    private ArrayList<IngredientInStorage> dataList;

    public IngredientStorageAdapter(Context context, ArrayList<IngredientInStorage> dataList) {
        super(context,0,dataList);
        this.context = context;
        this.dataList = dataList;
    }

    /**
     *
     * @param position index of current food item in list
     * @param convertView
     * @param parent
     * @return View object for each food item
     */
    @NonNull
    @Override
    public View getView (int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.custom_ingredient_content, parent, false);
        }

        TextView descriptionText= view.findViewById(R.id.description_text);
        TextView countText = view.findViewById(R.id.count_text);
        TextView unitText = view.findViewById((R.id.unit_text));


        IngredientInStorage currentIngredientInStorage = dataList.get(position);

        descriptionText.setText(currentIngredientInStorage.getDescription());
        countText.setText(String.valueOf(currentIngredientInStorage.getAmount()));
        unitText.setText(String.valueOf(currentIngredientInStorage.getMeasurementUnit()));

        // display in YYYY-MM-DD format in main activity
//        if (currentFood.getMonth() < 10) {
//            month = "0" + String.valueOf(currentFood.getMonth());
//        } else { month = String.valueOf(currentFood.getMonth());}
//        if (currentFood.getDay() < 10) {
//            day = "0" + String.valueOf(currentFood.getDay());
//        } else { day = String.valueOf(currentFood.getDay());}

//        dateText.setText("Expiry: " + String.valueOf(currentFood.getYear()) + "-"
//                + month + "-" + day);
        return view;
    }
}
