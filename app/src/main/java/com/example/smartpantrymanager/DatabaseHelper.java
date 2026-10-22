
package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database configuration
    private static final String DATABASE_NAME = "SmartPantry.db";
    private static final int DATABASE_VERSION = 2;

    // Ingredient table
    private static final String TABLE_INGREDIENTS = "ingredients";
    private static final String TABLE_RECIPES = "recipes";
    private static final String TABLE_RECIPE_INGREDIENTS = "recipe_ingredients";

    // Column names
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_QUANTITY = "quantity";
    private static final String COL_UNIT = "unit";
    private static final String COL_EXPIRY = "expiry_date";

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create database tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String createIngredientsTable =
                "CREATE TABLE " + TABLE_INGREDIENTS + " (" +
                        COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_NAME + " TEXT NOT NULL, " +
                        COL_QUANTITY + " REAL NOT NULL, " +
                        COL_UNIT + " TEXT NOT NULL, " +
                        COL_EXPIRY + " TEXT)";

        db.execSQL(createIngredientsTable);

        createRecipeTables(db);
        seedRecipes(db);
    }


    private void createRecipeTables(SQLiteDatabase db) {

        String createRecipesTable =
                "CREATE TABLE IF NOT EXISTS " + TABLE_RECIPES + " (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "name TEXT NOT NULL, " +
                        "description TEXT, " +
                        "instructions TEXT NOT NULL, " +
                        "preparation_time INTEGER NOT NULL)";

        db.execSQL(createRecipesTable);

        String createRecipeIngredientsTable =
                "CREATE TABLE IF NOT EXISTS " +
                        TABLE_RECIPE_INGREDIENTS + " (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "recipe_id INTEGER NOT NULL, " +
                        "name TEXT NOT NULL, " +
                        "quantity REAL NOT NULL, " +
                        "unit TEXT NOT NULL, " +
                        "FOREIGN KEY(recipe_id) REFERENCES recipes(id))";

        db.execSQL(createRecipeIngredientsTable);
    }


    // Handle future database upgrades


    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion, int newVersion) {

        if (oldVersion < 2) {
            createRecipeTables(db);
            seedRecipes(db);
        }
    }



    // CREATE: Add a new ingredient
    public long addIngredient(Ingredient ingredient) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NAME, ingredient.getName());
        values.put(COL_QUANTITY, ingredient.getQuantity());
        values.put(COL_UNIT, ingredient.getUnit());
        values.put(COL_EXPIRY, ingredient.getExpiryDate());

        return db.insert(TABLE_INGREDIENTS, null, values);
    }

    // READ: Retrieve all ingredients
    public List<Ingredient> getAllIngredients() {

        List<Ingredient> ingredients = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_INGREDIENTS,
                null,
                null,
                null,
                null,
                null,
                COL_NAME + " ASC"
        );

        try {
            while (cursor.moveToNext()) {

                int id = cursor.getInt(
                        cursor.getColumnIndexOrThrow(COL_ID)
                );

                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow(COL_NAME)
                );

                double quantity = cursor.getDouble(
                        cursor.getColumnIndexOrThrow(COL_QUANTITY)
                );

                String unit = cursor.getString(
                        cursor.getColumnIndexOrThrow(COL_UNIT)
                );

                String expiryDate = cursor.getString(
                        cursor.getColumnIndexOrThrow(COL_EXPIRY)
                );

                ingredients.add(new Ingredient(
                        id, name, quantity, unit, expiryDate
                ));
            }
        } finally {
            cursor.close();
        }

        return ingredients;
    }

    // UPDATE: Edit an existing ingredient
    public int updateIngredient(Ingredient ingredient) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NAME, ingredient.getName());
        values.put(COL_QUANTITY, ingredient.getQuantity());
        values.put(COL_UNIT, ingredient.getUnit());
        values.put(COL_EXPIRY, ingredient.getExpiryDate());

        return db.update(
                TABLE_INGREDIENTS,
                values,
                COL_ID + " = ?",
                new String[]{
                        String.valueOf(ingredient.getId())
                }
        );
    }

    // DELETE: Remove an ingredient
    public int deleteIngredient(int ingredientId) {

        SQLiteDatabase db = getWritableDatabase();

        return db.delete(
                TABLE_INGREDIENTS,
                COL_ID + " = ?",
                new String[]{
                        String.valueOf(ingredientId)
                }
        );
    }


    private void seedRecipes(SQLiteDatabase db) {

        // Prevent inserting duplicate recipes
        try (Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + TABLE_RECIPES,
                null)) {

            if (cursor.moveToFirst() && cursor.getInt(0) > 0) {
                return;
            }
        }

        List<Recipe> recipes = RecipeSeeder.getDefaultRecipes();

        for (Recipe recipe : recipes) {

            ContentValues recipeValues = new ContentValues();

            recipeValues.put("name", recipe.getName());
            recipeValues.put("description", recipe.getDescription());
            recipeValues.put("instructions", recipe.getInstructions());
            recipeValues.put(
                    "preparation_time",
                    recipe.getPreparationTimeMinutes()
            );

            long recipeId = db.insertOrThrow(
                    TABLE_RECIPES,
                    null,
                    recipeValues
            );

            for (RecipeIngredient ingredient :
                    recipe.getRequiredIngredients()) {

                ContentValues ingredientValues = new ContentValues();

                ingredientValues.put("recipe_id", recipeId);
                ingredientValues.put("name", ingredient.getName());
                ingredientValues.put("quantity", ingredient.getQuantity());
                ingredientValues.put("unit", ingredient.getUnit());

                db.insertOrThrow(
                        TABLE_RECIPE_INGREDIENTS,
                        null,
                        ingredientValues
                );
            }
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        if (!db.isReadOnly() && db.getVersion() >= 2) {
            db.beginTransaction();
            try {
                seedRecipes(db);
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }
    }

    public List<Recipe> getAllRecipes() {

        List<Recipe> recipes = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        try (Cursor recipeCursor = db.query(
                TABLE_RECIPES,
                null,
                null,
                null,
                null,
                null,
                "name ASC"
        )) {

            while (recipeCursor.moveToNext()) {

                int id = recipeCursor.getInt(
                        recipeCursor.getColumnIndexOrThrow("id")
                );

                String name = recipeCursor.getString(
                        recipeCursor.getColumnIndexOrThrow("name")
                );

                String description = recipeCursor.getString(
                        recipeCursor.getColumnIndexOrThrow("description")
                );

                String instructions = recipeCursor.getString(
                        recipeCursor.getColumnIndexOrThrow("instructions")
                );

                int preparationTime = recipeCursor.getInt(
                        recipeCursor.getColumnIndexOrThrow("preparation_time")
                );

                Recipe recipe = new Recipe(
                        id,
                        name,
                        description,
                        instructions,
                        preparationTime
                );

                try (Cursor ingredientCursor = db.query(
                        TABLE_RECIPE_INGREDIENTS,
                        null,
                        "recipe_id = ?",
                        new String[]{String.valueOf(id)},
                        null,
                        null,
                        "id ASC"
                )) {

                    while (ingredientCursor.moveToNext()) {

                        String ingredientName = ingredientCursor.getString(
                                ingredientCursor.getColumnIndexOrThrow("name")
                        );

                        double quantity = ingredientCursor.getDouble(
                                ingredientCursor.getColumnIndexOrThrow("quantity")
                        );

                        String unit = ingredientCursor.getString(
                                ingredientCursor.getColumnIndexOrThrow("unit")
                        );

                        recipe.addRequiredIngredient(
                                new RecipeIngredient(
                                        ingredientName,
                                        quantity,
                                        unit
                                )
                        );
                    }
                }

                recipes.add(recipe);
            }
        }

        return recipes;
    }

    public Recipe getRecipeById(int recipeId) {

        SQLiteDatabase db = getReadableDatabase();
        Recipe recipe = null;

        try (Cursor cursor = db.query(
                TABLE_RECIPES,
                null,
                "id = ?",
                new String[]{String.valueOf(recipeId)},
                null,
                null,
                null
        )) {

            if (cursor.moveToFirst()) {

                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow("name")
                );

                String description = cursor.getString(
                        cursor.getColumnIndexOrThrow("description")
                );

                String instructions = cursor.getString(
                        cursor.getColumnIndexOrThrow("instructions")
                );

                int preparationTime = cursor.getInt(
                        cursor.getColumnIndexOrThrow("preparation_time")
                );

                recipe = new Recipe(
                        recipeId,
                        name,
                        description,
                        instructions,
                        preparationTime
                );
            }
        }

        if (recipe == null) {
            return null;
        }

        try (Cursor cursor = db.query(
                TABLE_RECIPE_INGREDIENTS,
                null,
                "recipe_id = ?",
                new String[]{String.valueOf(recipeId)},
                null,
                null,
                "id ASC"
        )) {

            while (cursor.moveToNext()) {

                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow("name")
                );

                double quantity = cursor.getDouble(
                        cursor.getColumnIndexOrThrow("quantity")
                );

                String unit = cursor.getString(
                        cursor.getColumnIndexOrThrow("unit")
                );

                recipe.addRequiredIngredient(
                        new RecipeIngredient(name, quantity, unit)
                );
            }
        }

        return recipe;
    }
}
