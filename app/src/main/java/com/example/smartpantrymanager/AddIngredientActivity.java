
package com.example.smartpantrymanager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;
import java.util.Locale;

public class AddIngredientActivity extends AppCompatActivity {

    private EditText editIngredientName;
    private EditText editQuantity;
    private EditText editExpiryDate;
    private Spinner spinnerUnit;
    private TextView textFormTitle;

    private Button btnSaveIngredient;
    private Button btnCancel;

    private DatabaseHelper databaseHelper;

    private int ingredientId = -1;
    private boolean isEditMode = false;

    private final String[] units = {
            "pieces", "g", "kg", "ml", "L",
            "cups", "tbsp", "tsp"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_ingredient);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(android.R.id.content),
                (v, insets) -> {
                    Insets bars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars()
                    );

                    v.setPadding(
                            bars.left, bars.top,
                            bars.right, bars.bottom
                    );

                    return insets;
                }
        );

        databaseHelper = new DatabaseHelper(this);

        editIngredientName = findViewById(R.id.editIngredientName);
        editQuantity = findViewById(R.id.editQuantity);
        editExpiryDate = findViewById(R.id.editExpiryDate);
        spinnerUnit = findViewById(R.id.spinnerUnit);
        textFormTitle = findViewById(R.id.textFormTitle);

        btnSaveIngredient = findViewById(R.id.btnSaveIngredient);
        btnCancel = findViewById(R.id.btnCancel);

        setupUnitSpinner();
        checkEditMode();

        editExpiryDate.setOnClickListener(v -> showDatePicker());

        btnSaveIngredient.setOnClickListener(v -> saveIngredient());

        btnCancel.setOnClickListener(v -> finish());
    }

    private void setupUnitSpinner() {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                units
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinnerUnit.setAdapter(adapter);
    }

    private void checkEditMode() {

        if (!getIntent().hasExtra("ingredient_id")) {
            return;
        }

        ingredientId = getIntent().getIntExtra(
                "ingredient_id", -1
        );

        if (ingredientId == -1) {
            return;
        }

        isEditMode = true;

        textFormTitle.setText("Edit Ingredient");
        btnSaveIngredient.setText("Update Ingredient");

        String name = getIntent().getStringExtra(
                "ingredient_name"
        );

        double quantity = getIntent().getDoubleExtra(
                "ingredient_quantity", 0
        );

        String unit = getIntent().getStringExtra(
                "ingredient_unit"
        );

        String expiryDate = getIntent().getStringExtra(
                "ingredient_expiry"
        );

        editIngredientName.setText(name);
        editQuantity.setText(String.valueOf(quantity));

        if (expiryDate != null) {
            editExpiryDate.setText(expiryDate);
        }

        for (int i = 0; i < units.length; i++) {
            if (units[i].equals(unit)) {
                spinnerUnit.setSelection(i);
                break;
            }
        }
    }

    private void showDatePicker() {

        Calendar calendar = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {

                    String date = String.format(
                            Locale.US,
                            "%04d-%02d-%02d",
                            year,
                            month + 1,
                            dayOfMonth
                    );

                    editExpiryDate.setText(date);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        dialog.show();
    }

    private void saveIngredient() {

        String name = editIngredientName.getText()
                .toString().trim();

        String quantityText = editQuantity.getText()
                .toString().trim();

        String unit = spinnerUnit.getSelectedItem().toString();

        String expiryDate = editExpiryDate.getText()
                .toString().trim();

        if (name.isEmpty()) {
            editIngredientName.setError(
                    "Ingredient name is required"
            );
            editIngredientName.requestFocus();
            return;
        }


        if (name.length() > 100) {
            editIngredientName.setError(
                    "Ingredient name cannot exceed 100 characters"
            );
            editIngredientName.requestFocus();
            return;
        }


        if (quantityText.isEmpty()) {
            editQuantity.setError(
                    "Quantity is required"
            );
            editQuantity.requestFocus();
            return;
        }

        double quantity;

        try {
            quantity = Double.parseDouble(quantityText);
        } catch (NumberFormatException e) {
            editQuantity.setError("Enter a valid quantity");
            return;
        }

        if (!Double.isFinite(quantity) || quantity <= 0) {
            editQuantity.setError(
                    "Quantity must be greater than zero"
            );
            return;
        }

        if (expiryDate.isEmpty()) {
            expiryDate = null;
        }

        if (isEditMode) {

            Ingredient ingredient = new Ingredient(
                    ingredientId,
                    name,
                    quantity,
                    unit,
                    expiryDate
            );

            int rowsUpdated =
                    databaseHelper.updateIngredient(ingredient);

            if (rowsUpdated > 0) {
                Toast.makeText(
                        this,
                        "Ingredient updated successfully!",
                        Toast.LENGTH_SHORT
                ).show();

                finish();
            } else {
                Toast.makeText(
                        this,
                        "Failed to update ingredient",
                        Toast.LENGTH_SHORT
                ).show();
            }

        } else {

            Ingredient ingredient = new Ingredient(
                    name,
                    quantity,
                    unit,
                    expiryDate
            );

            long result = databaseHelper.addIngredient(ingredient);

            if (result != -1) {
                Toast.makeText(
                        this,
                        "Ingredient saved successfully!",
                        Toast.LENGTH_SHORT
                ).show();

                finish();
            } else {
                Toast.makeText(
                        this,
                        "Failed to save ingredient",
                        Toast.LENGTH_SHORT
                ).show();
            }
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
