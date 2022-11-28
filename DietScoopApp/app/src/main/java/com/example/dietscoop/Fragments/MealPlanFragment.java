package com.example.dietscoop.Fragments;

import android.animation.TimeAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dietscoop.Activities.MainActivity;
import com.example.dietscoop.Activities.MealPlanActivity;
import com.example.dietscoop.Activities.RecyclerItemClickListener;
import com.example.dietscoop.Activities.swipeToDeleteCallBack;
import com.example.dietscoop.Adapters.MealPlanRecyclerAdapter;
import com.example.dietscoop.Data.Meal.MealDay;
import com.example.dietscoop.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MealPlanFragment extends Fragment implements RecyclerItemClickListener {

    View fragmentView;
    Boolean existsMealPlan = false;

    RecyclerView mealDayRecycler;
    MealPlanRecyclerAdapter mealPlanAdapter;
    swipeToDeleteCallBack testingSwipeDelete;

    ArrayList<MealDay> mealDays;

    Bundle message; //Holder for information from other activies.

    ActionBar topBar;

    /**
     * Constructor handles the creation of a mealplan clone for the user
     * to edit.
     * @param mealPlanSent Bundle
     */
    public MealPlanFragment(ArrayList<MealDay> mealPlanSent) {
        super(R.layout.meal_plan_fragment);
        this.mealDays = mealPlanSent;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        fragmentView = view;

        initializeViews();
        ((MealPlanActivity)getActivity()).listDates();

        setBinderForSwipeDelete();

        //Setting up the Listener:
        mealPlanAdapter.setEntryListener(this);
        ((MealPlanActivity)getActivity()).mealPlanStorage.addMealPlanSnapshotListener(mealPlanAdapter);
        ((MealPlanActivity)getActivity()).mealPlanStorage.getMealPlanFromDB();

        //For the recycler: //TODO: Need to fix the setOnClickListener.
        mealDayRecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MealPlanActivity) getActivity()).changeToMealDayEdit(mealDayRecycler.getChildLayoutPosition(view));
            }
        });
    }

    public void initializeViews() {
        mealDayRecycler = (RecyclerView) fragmentView.findViewById(R.id.recycler_for_meal_plans);
        mealPlanAdapter = new MealPlanRecyclerAdapter(getActivity(), mealDays);
        mealDayRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mealDayRecycler.setAdapter(mealPlanAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new swipeToDeleteCallBack(mealPlanAdapter));
        itemTouchHelper.attachToRecyclerView(mealDayRecycler);

        addMealDayButton = (FloatingActionButton) fragmentView.findViewById(R.id.add_mealday_button);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.mealPlanAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {
        //Send over information to the MealDayFragment to edit an existing day:
        ((MealPlanActivity)getActivity()).changeToMealDayEdit(position);
    }

    public void setBinderForSwipeDelete() {
        deleteMealDay deleteBinder = mealDayIndexToDel -> ((MealPlanActivity)getActivity()).mealDayDelete(mealDayIndexToDel);
        this.mealPlanAdapter.setDeletionSignature(deleteBinder);
    }

}
