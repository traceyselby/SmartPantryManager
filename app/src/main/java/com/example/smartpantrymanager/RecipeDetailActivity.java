
package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_detail);

        databaseHelper = new DatabaseHelper(this);

        TextView textName =
                findViewById(R.id.textDetailRecipeName);

        TextView textDescription =
                findViewById(R.id.textDetailDescription);

        TextView textTime =
                findViewById(R.id.textDetailTime);

        TextView textIngredients =
                findViewById(R.id.textDetailIngredients);

        TextView textInstructions =
                findViewById(R.id.textDetailInstructions);

        Button btnBack =
                findViewById(R.id.btnBackToRecipes);

        btnBack.setOnClickListener(view -> finish());

        int recipeId = getIntent().getIntExtra("recipe_id", -1);

        if (recipeId == -1) {
            Toast.makeText(
                    this,
                    "Invalid recipe",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
            return;
        }

        Recipe recipe = databaseHelper.getRecipeById(recipeId);

        if (recipe == null) {
            Toast.makeText(
                    this,
                    "Recipe not found",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
            return;
        }

        textName.setText(recipe.getName());

        textDescription.setText(recipe.getDescription());

        textTime.setText(
                "Preparation time: "
                        + recipe.getPreparationTimeMinutes()
                        + " minutes"
        );

        StringBuilder ingredientText = new StringBuilder();

        for (RecipeIngredient ingredient :
                recipe.getRequiredIngredients()) {

            ingredientText
                    .append("• ")
                    .append(ingredient.getQuantity())
                    .append(" ")
                    .append(ingredient.getUnit())
                    .append(" ")
                    .append(ingredient.getName())
                    .append("\n");
        }

        textIngredients.setText(ingredientText.toString().trim());

        textInstructions.setText(recipe.getInstructions());
    }
}
