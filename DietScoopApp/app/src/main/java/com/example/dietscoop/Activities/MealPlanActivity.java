package com.example.dietscoop.Activities;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dietscoop.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * This activity will handle the MealPlanner and will go through instantiating a new
 * MealPlan Object to contain the meals per day for each day.
 *
 * 1. Need a means to add mealdays.
 * 2. Needs a means to generate fragments.
 * 3. Need to store everything back in this activity and send any updates to the firestore database.
 */
public class MealPlanActivity extends AppCompatActivity {

    FloatingActionButton addMealDay;
    RecyclerView mealDayRecycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plan);

    }

}
