package com.example.dietscoop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class IngredientListActivity extends AppCompatActivity {

    FoodStorage foodStorage;
    IngredientStorageAdapter ingredientStorageAdapter;
    ListView ingredientListView;
    ArrayList<IngredientInStorage> ingros;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_list);
        ingredientListView = findViewById(R.id.ingredient_list);

        ingros = new ArrayList<IngredientInStorage>();
        ingredientStorageAdapter = new IngredientStorageAdapter(this, ingros);
        ingredientListView.setAdapter(ingredientStorageAdapter);
        db = FirebaseFirestore.getInstance();
        String TAG = "test";
        CollectionReference coll = db.collection("IngredientStorage");
        coll.whereNotEqualTo("amount", 0)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.getId() != null) {
                                Log.d(TAG, doc.getId() + " => " + doc.getData());
                                ingros.add(new IngredientInStorage(doc.getId(), (String)doc.getData()
                                        .get("unit"), ((Long)doc.getData().get("amount")).intValue(),
                                        ((Long)doc.getData().get("year")).intValue(),
                                        ((Long)doc.getData().get("month")).intValue(), ((Long)doc.getData().get("day")).intValue(),
                                        Location.stringToLocation(doc.getData().get("location").toString()),
                                        Category.stringToCategory(doc.getData().get("category").toString())));
                            }
                        }
                        ingredientStorageAdapter.notifyDataSetChanged();

                    }
                });
//        //TODO: always returns list of size 0; never enters onComplete method

//        foodStorage = new FoodStorage();
//        ingredientStorageAdapter = new IngredientStorageAdapter(this, foodStorage.getIngredientStorage());
//        ingredientListView.setAdapter(ingredientStorageAdapter);
        //foodStorage.addIngredientSnapshotListener(ingredientStorageAdapter);
        //foodStorage.getIngredientStorageFromDatabase(ingredientStorageAdapter);

//        ingredientStorageAdapter.notifyDataSetChanged();



    }
}