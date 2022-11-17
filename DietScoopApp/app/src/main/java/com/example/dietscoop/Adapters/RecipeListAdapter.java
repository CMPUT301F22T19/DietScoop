package com.example.dietscoop.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dietscoop.R;
import com.example.dietscoop.Data.Recipe.Recipe;
import com.example.dietscoop.Activities.RecyclerItemClickListener;

import java.util.ArrayList;

/**
 * Class to adapt an ArrayList of recipes for a recycler view.
 */
public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Recipe> dataList;
    private RecyclerItemClickListener listener;

    public RecipeListAdapter(Context context, ArrayList<Recipe> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public void setItemClickListener(RecyclerItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (dataList != null && dataList.size() > 0) {
            Recipe recipe = dataList.get(position);
            holder.categoryTV.setText(recipe.getCategoryName());
            holder.servingsTV.setText(recipe.getNumOfServings() + " servings");
            holder.prepTimeTV.setText(recipe.getPrepTime() + " " + recipe.getPrepUnitTime().toString() + "s" );
            holder.titleTV.setText(String.valueOf(recipe.getDescription()).toUpperCase());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_content, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public int getItemCount() {
        return dataList.size();
    }

    /**
     * ViewHolder for Recipes for recycler list use
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleTV, prepTimeTV, servingsTV, categoryTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTV = itemView.findViewById(R.id.list_recipe_title);
            prepTimeTV = itemView.findViewById(R.id.list_prep_time);
            servingsTV = itemView.findViewById(R.id.list_num_servings);
            categoryTV = itemView.findViewById(R.id.list_recipe_category);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.onItemClick(view, getAdapterPosition());
            }
        }
    }
}
