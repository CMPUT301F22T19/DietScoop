package com.example.dietscoop.Data.Meal;


import com.example.dietscoop.Data.FoodItem;
import com.example.dietscoop.Data.Ingredient.IngredientInMealDay;
import com.example.dietscoop.Data.Recipe.RecipeInMealDay;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Class will work as a container for the Meals in the given mealday.
 *
 * 1. Class handles the fetching, adding and deletion of meals.
 * 2. Deletion of itself?
 * TODO: Add the removers for each of the following arraylists (Recipes and Ingredients).
 */
public class MealDay implements Serializable {

//    TODO: Uncomment this if going for the meal in mealday schema.
    LocalDate date;
    ArrayList<FoodItem> foodItems; //This array is adapted to be used in the RecyclerView for MealDay Editing.
    ArrayList<IngredientInMealDay> ingredientInMealDays;
    ArrayList<RecipeInMealDay> recipeInMealDays;
    private ArrayList<String> ingredientIDs;
    private ArrayList<String> recipeIDs;
    private String id;

    //Constructors: TODO: Incorporate more constructors for this class:
    public MealDay(LocalDate date) {
        foodItems = new ArrayList<>();
        ingredientIDs = new ArrayList<>();
        recipeIDs = new ArrayList<>();
        ingredientInMealDays = new ArrayList<>();
        recipeInMealDays = new ArrayList<>();
        this.date = date;
    }

    //Getters and Setters:
    public void deleteIngredientFromMealDay(IngredientInMealDay ingredient) {
        this.ingredientIDs.remove(ingredient.getId());
        this.ingredientInMealDays.remove(ingredient);
    }

    public void addIngredientInMealDay(IngredientInMealDay ingredientInMealDay) {
        this.ingredientIDs.add(ingredientInMealDay.getId());
        this.ingredientInMealDays.add(ingredientInMealDay);
        this.foodItems.add(ingredientInMealDay);
    }

    public void addRecipeInMealDay(RecipeInMealDay recipeInMealDay) {
        this.recipeIDs.add(recipeInMealDay.getId());
        this.recipeInMealDays.add(recipeInMealDay);
        this.foodItems.add(recipeInMealDay);
    }

    public void removeIngredientInMealDay(int indexToDelete) {
        IngredientInMealDay container = (IngredientInMealDay) this.foodItems.get(indexToDelete);
        int actualIndex = this.ingredientInMealDays.indexOf(container);
        this.ingredientInMealDays.remove(actualIndex);
        this.ingredientIDs.remove(container.getId());
        this.foodItems.remove(indexToDelete);
    }

    public void removeRecipeInMealDay(int indexToDelete) {
        RecipeInMealDay container = (RecipeInMealDay) this.foodItems.get(indexToDelete);
        int actualIndex = this.recipeInMealDays.indexOf(container);
        this.recipeInMealDays.remove(actualIndex);
        this.recipeIDs.remove(container.getId());
        this.foodItems.remove(indexToDelete);
    }

    public ArrayList<FoodItem> getFoodItems() {
        return this.foodItems;
    }

    public void setIngredientIDs(ArrayList<String> ingredientIDs) {
        this.ingredientIDs = ingredientIDs;
    }

    public void setRecipeIDs(ArrayList<String> recipeIDs) {
        this.recipeIDs = recipeIDs;
    }

    public void setIngredientInMealDays(ArrayList<IngredientInMealDay> ingredientInMealDays) {
        this.ingredientInMealDays = ingredientInMealDays;
        this.ingredientIDs.clear();
        for (IngredientInMealDay ing: ingredientInMealDays) {
            ingredientIDs.add(ing.getId());
        }
    }

    public void setRecipeInMealDays(ArrayList<RecipeInMealDay> recipeInMealDays) {
        this.recipeInMealDays = recipeInMealDays;
        this.recipeIDs.clear();
        for (RecipeInMealDay rec: recipeInMealDays) {
            recipeIDs.add(rec.getId());
        }
    }

    public ArrayList<String> getRecipeIDs() {
        return this.recipeIDs;
    }

    public ArrayList<String> getIngredientIDs() {return this.ingredientIDs;}

    public ArrayList<IngredientInMealDay> getIngredientInMealDays() {
        return ingredientInMealDays;
    }

    public ArrayList<RecipeInMealDay> getRecipeInMealDays() {
        return recipeInMealDays;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {this.date = date;}

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

}
