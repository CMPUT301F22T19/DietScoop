package com.example.dietscoop.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.dietscoop.Data.Meal.MealDay;
import com.example.dietscoop.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * MealPlanRecyclerAdapter loads in any given MealDays to actually process into the RecyclerView.
 */
public class MealPlanRecyclerAdapter extends RecyclerView.Adapter<MealPlanRecyclerAdapter.DayHolder> {

    //Variables:
    private ArrayList<MealDay> mealDays;

    //Static Class for the sake of inflating views and connecting to backend:
    public static class DayHolder extends RecyclerView.ViewHolder {

        private final TextView mealDayDate;
        private final Button modifyMealDay;

        public DayHolder(View view) {
            super(view);

            //View retrieval for each individual item in Recycler View:
            mealDayDate = (TextView) view.findViewById(R.id.mealplan_for_date);
            modifyMealDay = (Button) view.findViewById(R.id.modify_mealplan_for_date_button);

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
     * WARNING: THIS IS A TEST CONSTUCTOR, PLEASE CHANGE AFTER TESTING IS DONE.
     * @param mealDays
     */
    public MealPlanRecyclerAdapter(ArrayList<MealDay> mealDays) {
        this.mealDays = mealDays;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DayHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.meal_day_display, viewGroup, false);

        return new DayHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(DayHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getMealDayDateTextView().setText(mealDays.get(position).getDate().toString());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mealDays.size();
    }
}
