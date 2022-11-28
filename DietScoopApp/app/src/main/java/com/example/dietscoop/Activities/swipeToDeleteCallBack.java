package com.example.dietscoop.Activities;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dietscoop.Adapters.MealPlanRecyclerAdapter;

public class swipeToDeleteCallBack extends ItemTouchHelper.SimpleCallback {
    private MealPlanRecyclerAdapter myAdapter;
    private Drawable icon;
    private final ColorDrawable backgroundOfDelete = new ColorDrawable((Color.RED)); //TODO: Change to make swipe animation diff color.

    public swipeToDeleteCallBack(MealPlanRecyclerAdapter myAdapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT); //setting the swipe directions.
        this.myAdapter = myAdapter;
    }


    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) { //Code for passing in the view to handle the deletion.
        int position = viewHolder.getAdapterPosition();
        myAdapter.swipeToDelete(position); //TODO: implement this function and bind it to a delete mealday database method.
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX,
                dY, actionState, isCurrentlyActive);
        View itemView = viewHolder.itemView;
        int backgroundCornerOffset = 20;

        if (dX > 0) { // Swiping to the right
            backgroundOfDelete.setBounds(itemView.getLeft(), itemView.getTop(),
                    itemView.getLeft() + ((int) dX) + backgroundCornerOffset,
                    itemView.getBottom());

        } else if (dX < 0) { // Swiping to the left
            backgroundOfDelete.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                    itemView.getTop(), itemView.getRight(), itemView.getBottom());
        } else { // view is unSwiped
            backgroundOfDelete.setBounds(0, 0, 0, 0);
        }
        backgroundOfDelete.draw(c);
    }
}
