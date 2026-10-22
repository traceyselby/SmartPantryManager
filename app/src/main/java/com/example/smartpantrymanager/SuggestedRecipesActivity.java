
package com.example.smartpantrymanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import android.content.Intent;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerViewRecipes;
    private TextView textRecipeCount;
    private TextView textNoRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_suggested_recipes);

        databaseHelper = new DatabaseHelper(this);

        recyclerViewRecipes = findViewById(R.id.recyclerViewRecipes);
        textRecipeCount = findViewById(R.id.textRecipeCount);
        textNoRecipes = findViewById(R.id.textNoRecipes);

        Button btnBackToPantry =
                findViewById(R.id.btnBackToPantry);

        recyclerViewRecipes.setLayoutManager(
                new LinearLayoutManager(this)
        );

        btnBackToPantry.setOnClickListener(view -> finish());

        loadSuggestedRecipes();
    }

    private void loadSuggestedRecipes() {

        // Get all saved recipes.
        List<Recipe> allRecipes =
                databaseHelper.getAllRecipes();

        // Get ingredients currently in the pantry.
        List<Ingredient> pantryIngredients =
                databaseHelper.getAllIngredients();

        // Only keep recipes that can be fully prepared.
        List<Recipe> matchingRecipes =
                RecipeMatcher.findMatchingRecipes(
                        allRecipes,
                        pantryIngredients
                );

        RecipeAdapter adapter = new RecipeAdapter(
                matchingRecipes,
                recipe -> {
                    Intent intent = new Intent(
                            SuggestedRecipesActivity.this,
                            RecipeDetailActivity.class
                    );

                    intent.putExtra("recipe_id", recipe.getId());

                    startActivity(intent);
                }
        );

        recyclerViewRecipes.setAdapter(adapter);

        int count = matchingRecipes.size();

        textRecipeCount.setText(
                count == 1
                        ? "1 recipe available"
                        : count + " recipes available"
        );

        if (matchingRecipes.isEmpty()) {
            textNoRecipes.setVisibility(View.VISIBLE);
        } else {
            textNoRecipes.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (databaseHelper != null) {
            loadSuggestedRecipes();
        }
    }
}
