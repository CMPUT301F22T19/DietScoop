package com.example.dietscoop.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dietscoop.Data.Ingredient.IngredientInRecipe;
import com.example.dietscoop.Data.Recipe.recipeCategory;
import com.example.dietscoop.Data.Recipe.timeUnit;
import com.example.dietscoop.Fragments.AddIngredientToRecipeFragment;
import com.example.dietscoop.Adapters.IngredientRecipeAdapter;
import com.example.dietscoop.R;
import com.example.dietscoop.Data.Recipe.Recipe;
import com.example.dietscoop.Database.RecipeStorage;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.UUID;

/**
 * This class handles the creation of the
 * recipe view activity.
 *
 * It handles the
 * logic instantiation for the events that
 * go on in this activity.
 */
public class ViewRecipeActivity extends AppCompatActivity implements AddIngredientToRecipeFragment.OnFragmentInteractionListener, RecyclerItemClickListener {
    private static final int CAMERA_PERMISSION = 211;
    public static final int CAMERA_REQUEST = 212;
    TextView categoryLabel;
    TextView numServLabel;
    TextView prepTimeLabel;
    EditText prepTime, numServings, instructions, name;
    RecyclerView ingredientsView;
    RecipeStorage storage;
    IngredientRecipeAdapter adapter;
    Recipe currentRecipe;
    Button editInstructions;

    Button backButton;
    Button deleteButton;
    Button cancelButton;
    Button addButton;
    Button addByCameraRecipe;
    Button addByCameraRoll;
    Spinner prepTimeUnitSpinner, categorySpinner;
    boolean adding;
    ArrayList<IngredientInRecipe> tempIngListForUI;
    ArrayList<IngredientInRecipe> tempIngListUpdate;
    ArrayList<IngredientInRecipe> tempIngListAdd;
    ArrayList<IngredientInRecipe> tempIngListDelete;
    ArrayAdapter<CharSequence> prepUnitSpinnerAdapter;
    ArrayAdapter<CharSequence> categorySpinnerAdapter;
    private Uri photoURI;
    private ImageView thisImageRecipe;
    private String thisRecipeBase64;

    ActionBar topBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        setupActionBar();

        Intent intent = getIntent();

        //Fetching the serialized recipe:
        adding = intent.getBooleanExtra("ADDING", false);
        if (adding) {
            currentRecipe = new Recipe("");
            currentRecipe.setIngredientsList(new ArrayList<>());
            currentRecipe.setId(UUID.randomUUID().toString());

        } else {
            currentRecipe = (Recipe) intent.getSerializableExtra("RECIPE");
        }

        initialize();
        updateTextViews();

        this.thisRecipeBase64 = currentRecipe.getImageBitmap();

    }

    /**
     * Initializes the activity, with UI element references, as well as some listener functions
     */
    private void initialize() {

        categoryLabel = findViewById(R.id.recipe_label_category);
        numServLabel = findViewById(R.id.recipe_label_no_of_servings);
        prepTimeLabel = findViewById(R.id.recipe_label_prep_time);

        prepTime = findViewById(R.id.recipe_prep_time);
        numServings = findViewById(R.id.recipe_no_of_servings);
        instructions = findViewById(R.id.recipe_instructions);
        ingredientsView = findViewById(R.id.recipe_recycler_view);
        name = findViewById(R.id.recipe_title);

        addButton = findViewById(R.id.recipe_add_ingredient_button);
        addButton.setOnClickListener(view -> addIngredient());

        backButton = findViewById(R.id.recipe_back_button);
        backButton.setOnClickListener(view -> confirmRecipe());

        deleteButton = findViewById(R.id.recipe_delete_button);
        deleteButton.setOnClickListener(view -> deleteThisRecipe());

        cancelButton = findViewById(R.id.recipe_cancel_button);
        cancelButton.setOnClickListener(unused -> cancel());


        prepTimeUnitSpinner = findViewById(R.id.recipe_prep_time_unit_spinner);
        categorySpinner = findViewById(R.id.recipe_category_spinner);

        addByCameraRecipe = findViewById(R.id.add_recipe_photo_by_camera);
        addByCameraRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askCameraPermission();
            }
        });

        addByCameraRoll = findViewById(R.id.add_recipe_photo_by_camera_roll);
        addByCameraRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraRollOpener();
            }
        });

        thisImageRecipe = findViewById(R.id.foodImage);

        ArrayAdapter<CharSequence> prepUnitSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.prepUnitTime, android.R.layout.simple_spinner_item);
        prepUnitSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prepTimeUnitSpinner.setAdapter(prepUnitSpinnerAdapter);

        prepTimeUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String unitTime = parent.getItemAtPosition(position).toString();
                currentRecipe.setPrepUnitTime(timeUnit.stringToTimeUnit(unitTime));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<CharSequence> categorySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categorySpinnerAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String categoire = parent.getItemAtPosition(position).toString();
                currentRecipe.setCategory(recipeCategory.stringToRecipeCategory(categoire));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        if (adding) {
            prepTimeUnitSpinner.setSelection(0);
        } else {
            int spinnerUnitTimePosition = prepUnitSpinnerAdapter.getPosition(currentRecipe.getPrepUnitTime().toString());
            prepTimeUnitSpinner.setSelection(spinnerUnitTimePosition);
        }



        if(adding) {
            categorySpinner.setPrompt("Select");
        } else {
            int spinnerCategoryPosition = categorySpinnerAdapter.getPosition(currentRecipe.getCategoryName());
            categorySpinner.setSelection(spinnerCategoryPosition);
        }

        storage = new RecipeStorage();
        tempIngListForUI = new ArrayList<>();
        tempIngListForUI.addAll(currentRecipe.getIngredients());
        tempIngListUpdate = new ArrayList<>();
        tempIngListUpdate.addAll(currentRecipe.getIngredients());
        tempIngListAdd = new ArrayList<>();
        tempIngListDelete = new ArrayList<>();

        adapter = new IngredientRecipeAdapter(this,
                tempIngListForUI);

        Log.i("DESCRIPTION IN INIT", currentRecipe.getDescription());

        ingredientsView.setAdapter(adapter);
        ingredientsView.setHasFixedSize(false);
        ingredientsView.setLayoutManager(new LinearLayoutManager(this));

        storage.addIngredientsInRecipesSnapshotListener(currentRecipe, adapter);

        adapter.setItemClickListener(this);
    }

    /**
     * Populates the UI elements with relevant data
     */
    private void updateTextViews() {
        if (!adding) {
            prepTime.setText(String.valueOf(currentRecipe.getPrepTime()));
            numServings.setText(String.valueOf(currentRecipe.getNumOfServings()));
            instructions.setText(currentRecipe.getInstructions());
            name.setText(String.valueOf(currentRecipe.getDescription()));
        }
        if(currentRecipe.getImageBitmap() != null)
        {
            byte[] test = Base64.getDecoder().decode(currentRecipe.getImageBitmap());
            Bitmap bitmap = BitmapFactory.decodeByteArray(test, 0, test.length);
            thisImageRecipe.setImageBitmap(bitmap);

            thisImageRecipe.getLayoutParams().height = 1000;
            thisImageRecipe.getLayoutParams().width = 1000;
        }



    }

    /**
     * Updates the instructions for the recipe
     * @param text user-entered instructions
     */
    public void updateInstructions(String text) {
        //Changing the text for instructions and the recipe.
        instructions.setText(text);
        currentRecipe.setInstructions(text);

    }

    /**
     * Opens fragment that allows user to add an ingredient to the current recipe being viewed
     */
    private void addIngredient() {
        new AddIngredientToRecipeFragment().show(getSupportFragmentManager(), "ADD_Ingredient");

    }

    /**
     * Validates user input to avoid crashing the app if the user enters invalid input. Prompts user
     * regarding invalid input.
     * @return true if user entered valid input, false otherwise
     */
    private boolean errorCheck() {
        boolean isAllValid = true;
        String recipeNumOfServings = numServings.getText().toString();
        if (recipeNumOfServings.equals("")){
            numServLabel.setError("Please enter the number of servings!");
            isAllValid = false;
        } else {
            currentRecipe.setNumOfServings(Integer.valueOf(recipeNumOfServings));
        }

        String recipePrepTime = prepTime.getText().toString();
        if (recipePrepTime.equals("")) {
            prepTimeLabel.setError("Please enter a prep time!");
            isAllValid = false;
        } else {
            currentRecipe.setPrepTime(Integer.valueOf(recipePrepTime));
        }

        String description = name.getText().toString();
        if (description.equals("")) {
            name.setError("Please enter a name!");
            isAllValid = false;
        } else {
            currentRecipe.setDescription(description);
        }

        if(currentRecipe.getCategory()==null) {
            isAllValid = false;
            categoryLabel.setError("Please choose category!");
        }

        return isAllValid;


    }

    /**
     * Updates the recipe and commits the data to the database
     */
    private void confirmRecipe() {
        if(!errorCheck()){
            return;
        }

        currentRecipe.setImageBitmap(thisRecipeBase64);
        String instructions = this.instructions.getText().toString();
        currentRecipe.setInstructions(instructions);
        for (IngredientInRecipe ingToAdd: tempIngListAdd) {
            //currentRecipe.addIngredientRef(ingToAdd.getId());
            storage.addIngredientToIngredientsInRecipesCollection(ingToAdd);
        }
        for (IngredientInRecipe ingToUpdate: tempIngListUpdate) {
            storage.updateIngredientInIngredientsInRecipesCollection(ingToUpdate);
        }
        for (IngredientInRecipe ingToDelete: tempIngListDelete) {
            storage.removeIngredientFromIngredientsInRecipesCollection(ingToDelete);
        }
        tempIngListUpdate.addAll(tempIngListAdd);
        currentRecipe.setIngredientsList(tempIngListUpdate);
        if (adding) {
            storage.addRecipeToStorage(currentRecipe);
        } else {
            storage.updateRecipeInStorage(currentRecipe);
        }
        goBack();
    }

    /**
     *  Nothing is changed in the current recipe.
     */
    private void cancel() {
        goBack();
    }

    /**
     * Navigates back to page listing all recipes.
     */
    private void goBack() {
        Intent intent = new Intent(this, RecipeListActivity.class);
        startActivity(intent);
    }

    /**
     * Recipe currently being viewed is deleted from the database, and the user is returned to their recipe list page
     */
    private void deleteThisRecipe() {
        storage.removeRecipeFromStorage(currentRecipe);
        goBack();
    }


    /**
     * Called when user adds an ingredient to a recipe
     * @param newIngredientInRecipe new ingredient to be added to recipe
     */
    @Override
    public void onOkPressed(IngredientInRecipe newIngredientInRecipe) {
        newIngredientInRecipe.setRecipeID(currentRecipe.getId());
        newIngredientInRecipe.setId(UUID.randomUUID().toString());
        tempIngListForUI.add(newIngredientInRecipe);
        tempIngListAdd.add(newIngredientInRecipe);
        adapter.notifyDataSetChanged();
    }

    /**
     * Called when user updates details of an existing ingredient in a recipe. Stages the ingredient to be updated
     * @param updateIngredientInRecipe ingredient to be updated
     * @param index index of ingredient to be updated
     */
    @Override
    public void onOkPressedUpdate(IngredientInRecipe updateIngredientInRecipe, int index) {
        if (tempIngListUpdate.contains(updateIngredientInRecipe)) {
            tempIngListUpdate.set(index, updateIngredientInRecipe);
        } else {
            tempIngListAdd.set(index, updateIngredientInRecipe);
        }
        tempIngListForUI.set(index,updateIngredientInRecipe);
        adapter.notifyDataSetChanged();
    }

    /**
     * This method stages an ingredient to be deleted from the recipe.
     * @param deleteIngredientInRecipe
     */
    @Override
    public void onDeletePressed(IngredientInRecipe deleteIngredientInRecipe) {
        tempIngListUpdate.remove(deleteIngredientInRecipe);
        tempIngListAdd.remove(deleteIngredientInRecipe);
        tempIngListForUI.remove(deleteIngredientInRecipe);
        tempIngListDelete.add(deleteIngredientInRecipe);
        adapter.notifyDataSetChanged();
    }

    /**
     * Called when the user clicks an ingredient in a recipe. Opens a fragment to allow user to update the ingredient
     * @param view current view
     * @param position index of ingredient clicked on
     */
    @Override
    public void onItemClick(View view, int position) {
        IngredientInRecipe ingredient = tempIngListForUI.get(position);;
        new AddIngredientToRecipeFragment(ingredient, position).show(getSupportFragmentManager(), "MODIFY_Ingredient");
    }

    private void askCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) this,
                    new String[] {Manifest.permission.CAMERA}, CAMERA_PERMISSION);
        } else {
            openDeviceBuiltInCamera();
        }
    }

    private void openDeviceBuiltInCamera() {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, CAMERA_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data.getData() == null) {
            Log.i("end my suffering", "pain");
        }

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            photoURI = data.getData();
            try {
                Bitmap thisIngredientBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoURI);
                ByteArrayOutputStream outputStreamBitmap = new ByteArrayOutputStream();
                thisIngredientBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStreamBitmap);
                byte[] byteArrayBitmap = outputStreamBitmap.toByteArray();
                thisRecipeBase64 = Base64.getEncoder().encodeToString(byteArrayBitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Picasso.get().load(photoURI).into(thisImageRecipe);
            thisImageRecipe.setImageBitmap(null);
        } else {
            Bitmap thisIngredientPhotoCamera = (Bitmap) data.getExtras().get("data");
            thisImageRecipe.setImageBitmap(thisIngredientPhotoCamera);
            ByteArrayOutputStream outputStreamBitmap = new ByteArrayOutputStream();
            thisIngredientPhotoCamera.compress(Bitmap.CompressFormat.JPEG, 100, outputStreamBitmap);
            byte[] byteArrayBitmap = outputStreamBitmap.toByteArray();
            thisRecipeBase64 = Base64.getEncoder().encodeToString(byteArrayBitmap);
            Log.i("stringBitmap", thisRecipeBase64);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] > PackageManager.PERMISSION_GRANTED) {
                openDeviceBuiltInCamera();
            }
        } else {
        }
    }

    private void cameraRollOpener() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    /**
     * Sets up top bar without logout and add button
     */
    private void setupActionBar() {

        topBar = getSupportActionBar();
        topBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        topBar.setDisplayShowCustomEnabled(true);
        topBar.setCustomView(R.layout.top_bar_no_logout_no_add);

        View topBarView = topBar.getCustomView();
    }
}