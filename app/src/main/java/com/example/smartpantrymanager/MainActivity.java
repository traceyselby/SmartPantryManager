
package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    // UI components
    private RecyclerView recyclerViewPantry;
    private Button btnAddIngredient;
    private Button btnSuggestedRecipes;
    private Button btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable edge-to-edge display
        EdgeToEdge.enable(this);

        // Load the Pantry List screen
        setContentView(R.layout.activity_main);

        // Adjust layout for system bars
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

        // Connect Java components to XML elements
        recyclerViewPantry = findViewById(
                R.id.recyclerViewPantry
        );

        btnAddIngredient = findViewById(
                R.id.btnAddIngredient
        );

        btnSuggestedRecipes = findViewById(
                R.id.btnSuggestedRecipes
        );

        btnSettings = findViewById(
                R.id.btnSettings
        );

        // Add Ingredient button
        btnAddIngredient.setOnClickListener(v -> {
            Toast.makeText(
                    MainActivity.this,
                    "Add Ingredient clicked",
                    Toast.LENGTH_SHORT
            ).show();
        });

        // Suggested Recipes button
        btnSuggestedRecipes.setOnClickListener(v -> {
            Toast.makeText(
                    MainActivity.this,
                    "Suggested Recipes clicked",
                    Toast.LENGTH_SHORT
            ).show();
        });

        // Settings button
        btnSettings.setOnClickListener(v -> {
            Toast.makeText(
                    MainActivity.this,
                    "Settings clicked",
                    Toast.LENGTH_SHORT
            ).show();
        });
    }
}
