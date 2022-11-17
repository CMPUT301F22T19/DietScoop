package com.example.dietscoop.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dietscoop.Data.Ingredient.Ingredient;
import com.example.dietscoop.Data.Ingredient.IngredientInRecipe;
import com.example.dietscoop.R;

import java.util.ArrayList;

/**
 * Class for adapting IngredientRecipe for view in recycler view.
 */
public class IngredientRecipeAdapter extends RecyclerView.Adapter<IngredientRecipeAdapter.ViewHolder> {

    Context context;
    ArrayList<IngredientInRecipe> ingredientsList;

    /**
     * Constructor for an IngredientRecipeAdapter.
     * @param context context in which adapter operates.
     * @param ingredientsList ArrayList of ingredients to be adapted.
     */
    public IngredientRecipeAdapter(Context context, ArrayList<IngredientInRecipe> ingredientsList) {
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
            holder.recipe_ingredient_name_tv.setText(String.valueOf(ingredient.getDescription()));
            holder.recipe_ingredient_amount_tv.setText(Double.toString(ingredient.getAmount()));
            holder.recipe_ingredient_unit_tv.setText(ingredient.getMeasurementUnit());
            holder.recipe_ingredient_category_tv.setText(ingredient.getCategoryName());

            Log.i("EXPECTED INGREDIENT NAME", String.valueOf(ingredient.getDescription()));
            Log.i("INGREDIENT NAME", holder.recipe_ingredient_name_tv.getText().toString());
            Log.i("INGREDIENT AMOUNT", holder.recipe_ingredient_amount_tv.getText().toString());
        }
    }

    @Override
    public int getItemCount() {
        return ingredientsList.size();
    }

    public void setItemClickListener(Context context) {
        this.context = context;
    }

    /**
     * View holder for ingredient in recipe adapter.
     */
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
