package com.example.dietscoop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class IngredientRecipeAdapter extends RecyclerView.Adapter<IngredientRecipeAdapter.ViewHolder> {

    Context context;
    ArrayList<Ingredient> ingredientsList;

    public IngredientRecipeAdapter(Context context, ArrayList<Ingredient> ingredientsList) {
        this.context = context;
        this.ingredientsList = ingredientsList;
    }

    @NonNull
    @Override
    public IngredientRecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_ingredients_recipe_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientRecipeAdapter.ViewHolder holder, int position) {
        if (ingredientsList != null && ingredientsList.size() > 0) {
            Ingredient ingredient = ingredientsList.get(position);
            holder.recipe_ingredient_name_tv.setText(ingredient.getDescription());
            holder.recipe_ingredient_amount_tv.setText(Integer.toString(ingredient.getAmount()));
            holder.recipe_ingredient_unit_tv.setText(ingredient.getMeasurementUnit());
            holder.recipe_ingredient_category_tv.setText(ingredient.getCategoryName());
        }
    }

    @Override
    public int getItemCount() {
        return ingredientsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView recipe_ingredient_name_tv,recipe_ingredient_amount_tv,recipe_ingredient_unit_tv,recipe_ingredient_category_tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            recipe_ingredient_name_tv = itemView.findViewById(R.id.recipe_ingredient_name_tv);
            recipe_ingredient_amount_tv = itemView.findViewById(R.id.recipe_ingredient_amount_tv);
            recipe_ingredient_unit_tv = itemView.findViewById(R.id.recipe_ingredient_unit_tv);
            recipe_ingredient_category_tv = itemView.findViewById(R.id.recipe_ingredient_category_tv);

        }
    }
}
