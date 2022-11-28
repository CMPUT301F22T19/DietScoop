package com.example.dietscoop;

import com.example.dietscoop.Data.Comparators.RecipeComparator;
import com.example.dietscoop.Data.Recipe.Recipe;
import com.example.dietscoop.Data.Recipe.recipeCategory;
import com.example.dietscoop.Data.Recipe.timeUnit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RecipeComparatorTest {

    private RecipeComparator.byRecipeCategory categoryComparator() {
        return new RecipeComparator.byRecipeCategory();
    }

    private RecipeComparator.byRecipePrepTime prepTimeComparator() {
        return new RecipeComparator.byRecipePrepTime();
    }

    private RecipeComparator.byRecipeTitle titleComparator() {
        return new RecipeComparator.byRecipeTitle();
    }

    private RecipeComparator.byRecipeServingNumber servingComparator() {
        return new RecipeComparator.byRecipeServingNumber();
    }

    private Recipe emptyRecipe() {
        return new Recipe("_");
    }

    @Test
    public void testByCategory() {

        Recipe a = emptyRecipe();
        Recipe b = emptyRecipe();

        a.setCategory(recipeCategory.Appetizer);
        b.setCategory(recipeCategory.Holiday);

        assertTrue(categoryComparator().compare(a, b) < 0);
        assertTrue(categoryComparator().compare(b, a) > 0);
        assertEquals(0, categoryComparator().compare(a, a));

    }

    @Test
    public void testByPrepTime() {

        Recipe a = emptyRecipe();
        Recipe b = emptyRecipe();
        Recipe c = emptyRecipe();
        Recipe d = emptyRecipe();

        a.setPrepTime(40); a.setPrepUnitTime(timeUnit.min); // 40 mins
        b.setPrepTime(20); b.setPrepUnitTime(timeUnit.min); // 20 min
        c.setPrepTime(1); c.setPrepUnitTime(timeUnit.hr); // 1 hr
        d.setPrepTime(60); d.setPrepUnitTime(timeUnit.min); // 60 min

        assertTrue(prepTimeComparator().compare(a, b) > 0);
        assertTrue(prepTimeComparator().compare(a, c) < 0);
        assertTrue(prepTimeComparator().compare(c, b) > 0);
        assertEquals(0, prepTimeComparator().compare(c, d));

    }

    @Test
    public void testByTitle() {

        Recipe a = emptyRecipe();
        Recipe b = emptyRecipe();

        a.setDescription("AAA");
        b.setDescription("BBB");

        assertTrue(titleComparator().compare(a, b) < 0);
        assertTrue(titleComparator().compare(b, a) > 0);
        assertEquals(0, titleComparator().compare(a, a));

    }

    @Test
    public void testByServings() {

        Recipe a = emptyRecipe();
        Recipe b = emptyRecipe();

        a.setNumOfServings(99);
        b.setNumOfServings(100);

        assertTrue(servingComparator().compare(a, b) < 0);
        assertTrue(servingComparator().compare(b, a) > 0);
        assertEquals(0, servingComparator().compare(b, b));

    }



}
