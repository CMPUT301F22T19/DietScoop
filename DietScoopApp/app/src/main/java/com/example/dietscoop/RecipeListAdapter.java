package com.example.dietscoop;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class RecipeListAdapter extends ArrayAdapter<Recipe> {
    private Context context;
    private ArrayList<Recipe> dataList;

    public RecipeListAdapter(Context context, ArrayList<Recipe> dataList) {
        super(context,0,dataList);
        this.context = context;
        this.dataList = dataList;
    }

}
