package com.example.dietscoop;

import java.util.Comparator;

public class IngredientComparator {

    public class byName implements Comparator<Ingredient> {
        @Override
        public int compare(Ingredient t1, Ingredient t2) {
            return t1.getDescription().compareTo(t2.getDescription());
        }
    }

    public class byPrepTime implements  Comparator<Ingredient> {

        @Override
        public int compare(Ingredient t1, Ingredient t2) {
            return 0;
        }
    }
}
