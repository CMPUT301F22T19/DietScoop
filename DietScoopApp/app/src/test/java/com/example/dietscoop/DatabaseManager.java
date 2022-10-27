package com.example.dietscoop;

import org.junit.jupiter.api.Test;

public class DatabaseManager {
    /**
     * Note, this function mainly servers to work as a staging for
     * what kind of methods our program needs. It does not need a body.
     */
    @Test
    public void testSetupIngredientSnapshotListener() {
        /**
         * Debate the functionality of this function.
         */
    }

    @Test
    public void testRefreshDatabase() {
        /**
         * Update the live feed in the firestore database.
         */
    }

    @Test
    public void testPullUserInfo() {
        /**
         * Function will act as a hub to join the user information
         * and save it locally for recipe, storage, and meal-planning.
         *
         * Function calls RetrieveUserFoodStorage()
         *
         * Function calls RetrieveUserRecipeList()
         *
         */
    }

    @Test
    public void testRetrieveUserFoodStorage() {
        /**
         * Method will explicitly return the ingredient list pulled from DataBase.
         */
    }

    /**
     * This Function cascades:
     * ****************************************************************************************+
     */
    @Test
    public void testRetrieveUserRecipes() {
        /**
         * Method will explicitly return the list of Recipes from Database.
         * This method will also need to parse the information (Converting to IngredientInRecipe and
         * then to Recipe)
         * before appending to the list and sending it back to the user.
         */
    }

    @Test
    public void testParseRecipe() {
        /**
         * Method will parse the Recipe and return a proper recipe value.
         * This method will also extend a call to parseIngredientInRecipe() for
         * every ingredient represented in the list.
         */
    }

    @Test
    public void testParseIngredientInRecipe() {
        /**
         * Function will parse the ingredient and return a proper IngredientInRecipe object.
         */
    }
    /**
     * ****************************************************************************************+
     */
}
