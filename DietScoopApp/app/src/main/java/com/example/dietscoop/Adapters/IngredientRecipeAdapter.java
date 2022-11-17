package com.example.dietscoop.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dietscoop.Activities.RecyclerItemClickListener;
import com.example.dietscoop.Data.Ingredient.Ingredient;
import com.example.dietscoop.Data.Ingredient.IngredientInRecipe;
import com.example.dietscoop.R;

import java.util.ArrayList;

/**
 * Class for adapting IngredientRecipe for view in recycler view.
 */
public class IngredientRecipeAdapter extends RecyclerView.Adapter<IngredientRecipeAdapter.ViewHolder> {

    private Context context;
    private ArrayList<IngredientInRecipe> ingredientsList;
    private RecyclerItemClickListener listener;

    /**
     * Constructor for an IngredientRecipeAdapter.
     * @param context context in which adapter operates.
     * @param ingredientsList ArrayList of ingredients to be adapted.
     */
    public IngredientRecipeAdapter(Context context, ArrayList<IngredientInRecipe> ingredientsList) {
        this.context = context;
        this.ingredientsList = ingredientsList;
    }

    public void setItemClickListener(RecyclerItemClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_ingredients_recipe_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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


    /**
     * View holder for ingredient in recipe adapter.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView recipe_ingredient_name_tv,recipe_ingredient_amount_tv,recipe_ingredient_unit_tv,recipe_ingredient_category_tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            recipe_ingredient_name_tv = itemView.findViewById(R.id.recipe_ingredient_name_tv);
            recipe_ingredient_amount_tv = itemView.findViewById(R.id.recipe_ingredient_amount_tv);
            recipe_ingredient_unit_tv = itemView.findViewById(R.id.recipe_ingredient_unit_tv);
            recipe_ingredient_category_tv = itemView.findViewById(R.id.recipe_ingredient_category_tv);

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
