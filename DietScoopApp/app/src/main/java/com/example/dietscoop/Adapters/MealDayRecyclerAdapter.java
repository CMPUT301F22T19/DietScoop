package com.example.dietscoop.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dietscoop.Data.FoodItem;
import com.example.dietscoop.Data.Ingredient.Ingredient;
import com.example.dietscoop.Data.Meal.MealDay;
import com.example.dietscoop.Data.Recipe.Recipe;
import com.example.dietscoop.R;

import java.util.ArrayList;

public class MealDayRecyclerAdapter extends RecyclerView.Adapter<MealDayRecyclerAdapter.MealHolder>{

    //Variables:
    Context context;
    ArrayList<FoodItem> meals;

    //Static Class for the sake of inflating views and connecting to backend:
    public static class MealHolder extends RecyclerView.ViewHolder {

        private final TextView mealDescription;
        private final TextView mealQuantity;
        private final Button mealDelete;

        public MealHolder(View view) {
            super(view);

            //View retrieval for each individual item in Recycler View:
            mealDescription = (TextView) view.findViewById(R.id.meal_in_mealday_description);
            mealQuantity = (TextView) view.findViewById(R.id.meal_in_mealday_quantity_or_servings);
            mealDelete = (Button) view.findViewById(R.id.meal_in_mealday_delete_button);

            //TODO: 1. Set Up OnClickListener for the delete button.
        }

        //Fetchers for views:
        public TextView getMealDescription() {
            return mealDescription;
        }

        public TextView getMealQuantity() {
            return mealQuantity;
        }

        public Button getMealDelete() {
            return mealDelete;
        }
    }

    /**
     * Gabagool
     * @param meals
     */
    public MealDayRecyclerAdapter(Context context, ArrayList<FoodItem> meals) {
        this.context = context;
        this.meals = meals;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MealDayRecyclerAdapter.MealHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(context)
                .inflate(R.layout.meal_display, viewGroup, false);

        return new MealDayRecyclerAdapter.MealHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealHolder holder, int position) {
        //Binds the specified information based on the arraylist container specified.
        holder.getMealDescription().setText(meals.get(position).getDescription());

        String sample = meals.get(position).getType();
        String descTest = meals.get(position).getDescription();

        if (meals.get(position).getType().equals("Ingredient")) {
            holder.getMealQuantity().setText(String.valueOf(((Ingredient)meals.get(position)).getAmount()));
        } else if (meals.get(position).getType().equals("Recipe")) {
            holder.getMealQuantity().setText(String.valueOf(((Recipe)meals.get(position)).getNumberOfIngredients()));

        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return meals.size();
    }

}
