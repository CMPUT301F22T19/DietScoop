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
import com.example.dietscoop.Data.Meal.MealDay;
import com.example.dietscoop.R;

import java.util.ArrayList;

public class MealDayRecyclerAdapter extends RecyclerView.Adapter<MealDayRecyclerAdapter.MealHolder>{

    //Variables:
    Context context;
    ArrayList<FoodItem> mealDays;

    //Static Class for the sake of inflating views and connecting to backend:
    public static class MealHolder extends RecyclerView.ViewHolder {

        private final TextView mealDayDate;
        private final Button modifyMealDay;
        private final Button deleteMealDay;

        public MealHolder(View view) {
            super(view);

            //View retrieval for each individual item in Recycler View:
            mealDayDate = (TextView) view.findViewById(R.id.mealplan_for_date);
            modifyMealDay = (Button) view.findViewById(R.id.view_mealplan_for_date_button);
            deleteMealDay = (Button) view.findViewById(R.id.delete_mealplan_for_date_button);

            //TODO: 1. Set Up OnClickListener for the modify button.
        }

        //Fetchers for views:
        public TextView getMealDayDateTextView() {
            return mealDayDate;
        }

        public Button getMealDayModifyButton() {
            return modifyMealDay;
        }
    }

    /**
     * WARNING: THIS IS A TEST CONSTRUCTOR, PLEASE CHANGE AFTER TESTING IS DONE.
     * @param mealDays
     */
    public MealDayRecyclerAdapter(Context context, ArrayList<FoodItem> mealDays) {
        this.context = context;
        this.mealDays = mealDays;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MealDayRecyclerAdapter.MealHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(context)
                .inflate(R.layout.meal_day_display, viewGroup, false);

        return new MealDayRecyclerAdapter.MealHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealHolder holder, int position) {
        holder.getMealDayDateTextView().setText();
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MealPlanRecyclerAdapter.MealHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element:
        viewHolder.getMealDayDateTextView().setText(mealDays.get(position).getDate().toString());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mealDays.size();
    }

}
