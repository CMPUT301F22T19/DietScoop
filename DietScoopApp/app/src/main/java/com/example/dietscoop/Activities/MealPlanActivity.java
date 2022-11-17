package com.example.dietscoop.Activities;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dietscoop.Adapters.MealPlanRecyclerAdapter;
import com.example.dietscoop.Data.Meal.MealDay;
import com.example.dietscoop.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.ArrayList;

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
    MealPlanRecyclerAdapter mealPlanAdapter;
    ArrayList<MealDay> mealDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plan);

        //TESTING!!!!:

        //Fetch the views in the current activity:
        mealDayRecycler = (RecyclerView) findViewById(R.id.recycler_for_meal_plans);

        //Instancing our sample day:
        LocalDate now = LocalDate.now();
        MealDay testDay = new MealDay(LocalDate.now());

        mealDays = new ArrayList<>();

        mealDays.add(testDay);

        //Testing our adapter:
        mealPlanAdapter = new MealPlanRecyclerAdapter(this, mealDays);

        //Binding our adapter to our recycler view:
        mealDayRecycler.setAdapter(mealPlanAdapter);
    }

}
