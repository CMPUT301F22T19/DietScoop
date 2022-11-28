package com.example.dietscoop.Data.Recipe;

import com.example.dietscoop.Data.FoodItem;
import com.example.dietscoop.Data.Ingredient.IngredientInRecipe;
import com.google.firebase.firestore.DocumentReference;

import org.checkerframework.checker.units.qual.A;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class representing a Recipe.
 * Extends FoodItem to obtain a category property.
 */
public class Recipe extends FoodItem implements Serializable {
    private String imageBitmap;
    private int prepTime;
    private int servings;
    private timeUnit prepUnitTime;
    private recipeCategory category;
    ArrayList<IngredientInRecipe> ingredientsList;
    private String instructions;
    private ArrayList<String> ingredientRefs;

    public int getInMinutes() {
        if (this.getPrepUnitTime().equals(timeUnit.hr)) {
            return prepTime * 60;
        } else if (this.getPrepUnitTime().equals(timeUnit.min)) {
            return prepTime;
        } else {return 0;}
    }

    /**
     * Constructor for Recipe object
     * @param description Description of recipe
     * @param prepTime Preparation time of recipe
     * @param servings Serving number of recipe
     * @param prepUnitTime Units used for preparation time (prepTime)
     * @param category Category of recipe
     * @param ingredientsList List of ingredients used in recipe
     * @param instructions Instructions for recipe preparation
     */
    public Recipe(String description, int prepTime, int servings, timeUnit prepUnitTime,
                  recipeCategory category, ArrayList<IngredientInRecipe> ingredientsList,
                  String instructions) {
        this.description = description;

        // prepTime is only for display. inMinutes is used to sort recipes with regards to prep time
        this.prepTime = prepTime;
        this.servings = servings;
        this.prepUnitTime = prepUnitTime;
        this.category = category;
        this.ingredientsList = ingredientsList;
        this.instructions = instructions;
        this.ingredientRefs = new ArrayList<String>();
    }

    public Recipe(String description) {
        this.description = description;
        this.ingredientRefs = new ArrayList<String>();
    }


    public String getImageBitmap()
    {
        return imageBitmap;
    }

    public void setImageBitmap(String imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    /**
     * Getter for recipe preparation time
     * @return prep time of recipe (int)
     */
    public int getPrepTime() {
        return prepTime;
    }

    /**
     * Setter for recipe preparation time
     * @param prepTime new prepTime of recipe
     */
    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    /**
     * Setter for recipe number of servings
     * @param servings new servings count of recipe
     */
    public void setNumOfServings(int servings) {
        this.servings = servings;
    }

    /**
     * Setter for preparation time (prepTime) units
     * @param prepUnitTime new prep time units
     */
    public void setPrepUnitTime(timeUnit prepUnitTime) {
        this.prepUnitTime = prepUnitTime;
    }

    /**
     * Getter for prep time units
     * @return Prep time units for recipe
     */
    public timeUnit getPrepUnitTime() {
        return prepUnitTime;
    }

    /**
     * Setter for recipe category
     * @param category new Category
     */
    public void setCategory(recipeCategory category) {
        this.category = category;
    }

    /**
     * Setter for recipe associated ingredients list
     * @param ingredientsList new ingredients list
     */
    public void setIngredientsList(ArrayList<IngredientInRecipe> ingredientsList) {
        this.ingredientsList = ingredientsList;
        this.ingredientRefs.clear();
        for (IngredientInRecipe ing: ingredientsList) {
            this.ingredientRefs.add(ing.getId());
        }
    }

    /**
     * Setter for recipe instructions
     * @param description new instructions for recipe
     */
    public void setInstructions(String description) {
        this.instructions = description;
    }

    /**
     * Getter for number of ingredients used in recipe
     * @return the size of the ingredients list
     */
    public int getNumberOfIngredients() {
        return ingredientsList.size();
    }

    /**
     * Getter for recipe instructions
     * @return the instructions of the current recipe
     */
    public String getInstructions() {
        return this.instructions;
    }

    /**
     * Getter for ArrayList of ingredients
     * @return the ArrayList containing the recipe ingredients
     */
    public ArrayList<IngredientInRecipe> getIngredients() {
        return this.ingredientsList;
    }

    /**
     * Getter for category of recipe
     * @return the category of the recipe
     */
    public recipeCategory getCategory() {
        return this.category;
    }

    /**
     * Getter for the recipe category in string form
     * @return String of category.
     */
    public String getCategoryName() {return this.category.name(); }

    /**
     * Getter for the number of servings for the recipe
     * @return the number of servings of the recipe
     */
    public int getNumOfServings() {
        return this.servings;
    }

    /**
     * Method to add ingredient to current ArrayList of ingredients
     * @param ingredient ingredient to be added to ingredients list
     */
    public void addIngredient(IngredientInRecipe ingredient){
        this.ingredientsList.add(ingredient);
    }

    /**
     * Method to remove ingredient from current ArrayList of ingredients
     * @param ingredient ingredient to be removed from ingredients list
     */
    public void removeIngredient(IngredientInRecipe ingredient){
        if (ingredient.getId()!=null) {
            this.removeIngredientID(ingredient.getId());
        }
        this.ingredientsList.remove(ingredient);
    }

    public void updateIngredient(IngredientInRecipe ingredient, int index) {
        this.ingredientsList.set(index, ingredient);
    }

    public void addIngredientRef(String docref) {
        this.ingredientRefs.add(docref);
    }

    public void removeIngredientID(String docref) {
        this.ingredientRefs.remove(docref);
    }

    public ArrayList<String> getIngredientRefs() {return this.ingredientRefs;}

    public void setIngredientRefs(ArrayList<String> refs) {this.ingredientRefs = refs;}

    @Override
    public String getType() {
        return "Recipe";
    }

}
