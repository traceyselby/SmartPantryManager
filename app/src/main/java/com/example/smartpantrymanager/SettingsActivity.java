
package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    private TextView textTotalIngredients;
    private TextView textTotalRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        databaseHelper = new DatabaseHelper(this);

        textTotalIngredients =
                findViewById(R.id.textTotalIngredients);

        textTotalRecipes =
                findViewById(R.id.textTotalRecipes);

        Button btnClearPantry =
                findViewById(R.id.btnClearPantry);

        Button btnBack =
                findViewById(R.id.btnBackFromSettings);

        btnClearPantry.setOnClickListener(view ->
                showClearPantryConfirmation()
        );

        btnBack.setOnClickListener(view -> finish());

        updateStatistics();
    }

    private void updateStatistics() {

        int ingredientCount =
                databaseHelper.getAllIngredients().size();

        int recipeCount =
                databaseHelper.getAllRecipes().size();

        textTotalIngredients.setText(
                "Total pantry ingredients: " + ingredientCount
        );

        textTotalRecipes.setText(
                "Recipes in database: " + recipeCount
        );
    }

    private void showClearPantryConfirmation() {

        new AlertDialog.Builder(this)
                .setTitle("Clear Pantry")
                .setMessage(
                        "Are you sure you want to delete all pantry ingredients? "
                                + "This action cannot be undone."
                )
                .setPositiveButton("Delete All", (dialog, which) -> {

                    databaseHelper.clearAllIngredients();

                    updateStatistics();

                    Toast.makeText(
                            this,
                            "Pantry cleared successfully",
                            Toast.LENGTH_SHORT
                    ).show();
                })
                .setNegativeButton("Cancel", (dialog, which) ->
                        dialog.dismiss()
                )
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (databaseHelper != null) {
            updateStatistics();
        }
    }
}
