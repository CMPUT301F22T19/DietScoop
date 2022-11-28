package com.example.dietscoop.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dietscoop.Activities.RecyclerItemClickListener;
import com.example.dietscoop.Data.FoodItem;
import com.example.dietscoop.Data.Ingredient.Ingredient;
import com.example.dietscoop.Data.Ingredient.IngredientInMealDay;
import com.example.dietscoop.Data.Recipe.RecipeInMealDay;
import com.example.dietscoop.R;

import java.util.ArrayList;
//TODO: Make this view pretty please.
public class MealDayRecyclerAdapter extends RecyclerView.Adapter<MealDayRecyclerAdapter.MealHolder>{

    //Variables:
    Context context;
    ArrayList<FoodItem> meals;
    private RecyclerItemClickListener entryListener;

    //Static Class for the sake of inflating views and connecting to backend:
    public class MealHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView mealDescription;
        private final TextView mealQuantity;
        private final TextView mealUnit;

        public MealHolder(View view) {
            super(view);

            //View retrieval for each individual item in Recycler View:
            mealDescription = (TextView) view.findViewById(R.id.meal_in_mealday_description);
            mealQuantity = (TextView) view.findViewById(R.id.meal_in_mealday_quantity_or_servings);
            mealUnit = (TextView) view.findViewById(R.id.meal_in_day_unit);
            view.setOnClickListener(this); //Setting up the onClick Listener:
        }

        //Fetchers for views:
        public TextView getMealDescription() {
            return mealDescription;
        }

        public TextView getMealQuantity() {
            return mealQuantity;
        }

        public TextView getMealUnit() { return mealUnit; }

        @Override
        public void onClick(View view) {
            if (entryListener != null) {
                entryListener.onItemClick(view, getAdapterPosition());
            }
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
        String testingDescription = meals.get(position).getDescription();
        holder.getMealDescription().setText(meals.get(position).getDescription());

        String sample = meals.get(position).getType();
        String descTest = meals.get(position).getDescription();

        if (meals.get(position).getType().equals("Ingredient")) {
            String quantityUnit = ((Ingredient)meals.get(position)).getMeasurementUnit().toString();
            holder.getMealQuantity().setText(String.valueOf(((Ingredient)meals.get(position)).getAmount()) + " " + quantityUnit);
        } else if (meals.get(position).getType().equals("Recipe")) {
            Integer numOfServings = ((RecipeInMealDay)meals.get(position)).getDesiredNumOfServings();
            holder.getMealQuantity().setText(String.valueOf(numOfServings) + " Servings");
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void setEntryListener(RecyclerItemClickListener entryListener) {
        this.entryListener = entryListener;
    }
}
