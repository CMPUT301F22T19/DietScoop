package com.example.dietscoop;

import android.content.Context;
import android.content.pm.LabeledIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Recipe> dataList;

    public RecipeListAdapter(Context context, ArrayList<Recipe> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (dataList != null && dataList.size() > 0) {
            Recipe recipe = dataList.get(position);
            holder.categoryTV.setText(recipe.getCategoryName());
            holder.servingsTV.setText(String.valueOf(recipe.getNumOfServings()));
            holder.prepTimeTV.setText(String.valueOf(recipe.getPrepTime()));
            holder.titleTV.setText(String.valueOf(recipe.getDescription()));
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTV, prepTimeTV, servingsTV, categoryTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTV = itemView.findViewById(R.id.list_recipe_title);
            prepTimeTV = itemView.findViewById(R.id.list_prep_time);
            servingsTV = itemView.findViewById(R.id.list_num_servings);
            categoryTV = itemView.findViewById(R.id.list_recipe_category);
        }
    }
}
