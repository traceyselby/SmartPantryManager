package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPantry;
    private IngredientAdapter ingredientAdapter;
    private DatabaseHelper databaseHelper;

    private Button btnAddIngredient;
    private Button btnSuggestedRecipes;
    private Button btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {
                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars()
                    );
                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );
                    return insets;
                }
        );

        databaseHelper = new DatabaseHelper(this);

        recyclerViewPantry = findViewById(R.id.recyclerViewPantry);

        recyclerViewPantry.setLayoutManager(
                new LinearLayoutManager(this)
        );

        btnAddIngredient = findViewById(R.id.btnAddIngredient);
        btnSuggestedRecipes = findViewById(R.id.btnSuggestedRecipes);
        btnSettings = findViewById(R.id.btnSettings);

        // Open the Add Ingredient screen
        btnAddIngredient.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    AddIngredientActivity.class
            );
            startActivity(intent);
        });

        btnSuggestedRecipes.setOnClickListener(view -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    SuggestedRecipesActivity.class
            );

            startActivity(intent);
        });

        btnSettings.setOnClickListener(view -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    SettingsActivity.class
            );

            startActivity(intent);
        });


        loadPantryIngredients();
    }


    private void loadPantryIngredients() {

        List<Ingredient> ingredients =
                databaseHelper.getAllIngredients();

        ingredientAdapter = new IngredientAdapter(
                ingredients,
                new IngredientAdapter.OnIngredientActionListener() {

                    @Override
                    public void onEdit(Ingredient ingredient) {

                        Intent intent = new Intent(
                                MainActivity.this,
                                AddIngredientActivity.class
                        );

                        intent.putExtra(
                                "ingredient_id",
                                ingredient.getId()
                        );

                        intent.putExtra(
                                "ingredient_name",
                                ingredient.getName()
                        );

                        intent.putExtra(
                                "ingredient_quantity",
                                ingredient.getQuantity()
                        );

                        intent.putExtra(
                                "ingredient_unit",
                                ingredient.getUnit()
                        );

                        intent.putExtra(
                                "ingredient_expiry",
                                ingredient.getExpiryDate()
                        );

                        startActivity(intent);
                    }

                    @Override
                    public void onDelete(Ingredient ingredient) {

                        new androidx.appcompat.app.AlertDialog.Builder(
                                MainActivity.this
                        )
                                .setTitle("Delete Ingredient")
                                .setMessage(
                                        "Are you sure you want to delete "
                                                + ingredient.getName() + "?"
                                )
                                .setPositiveButton("Delete", (dialog, which) -> {

                                    int rowsDeleted =
                                            databaseHelper.deleteIngredient(
                                                    ingredient.getId()
                                            );

                                    if (rowsDeleted > 0) {
                                        Toast.makeText(
                                                MainActivity.this,
                                                "Ingredient deleted",
                                                Toast.LENGTH_SHORT
                                        ).show();

                                        loadPantryIngredients();
                                    } else {
                                        Toast.makeText(
                                                MainActivity.this,
                                                "Could not delete ingredient",
                                                Toast.LENGTH_SHORT
                                        ).show();
                                    }
                                })
                                .setNegativeButton("Cancel", null)
                                .show();
                    }
                }
        );

        recyclerViewPantry.setAdapter(ingredientAdapter);
    }


    @Override
    protected void onResume() {
        super.onResume();

        // Refresh the list when returning from Add Ingredient
        if (databaseHelper != null && recyclerViewPantry != null) {
            loadPantryIngredients();
        }
    }

    @Override
    protected void onDestroy() {
        if (databaseHelper != null) {
            databaseHelper.close();
        }
        super.onDestroy();
    }
}
