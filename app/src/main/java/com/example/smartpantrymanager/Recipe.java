
package com.example.smartpantrymanager;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

    private int id;
    private String name;
    private String description;
    private String instructions;
    private int preparationTimeMinutes;

    private List<RecipeIngredient> requiredIngredients;

    public Recipe(int id, String name, String description,
                  String instructions, int preparationTimeMinutes) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.instructions = instructions;
        this.preparationTimeMinutes = preparationTimeMinutes;
        this.requiredIngredients = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getInstructions() {
        return instructions;
    }

    public int getPreparationTimeMinutes() {
        return preparationTimeMinutes;
    }

    public List<RecipeIngredient> getRequiredIngredients() {
        return requiredIngredients;
    }

    public void addRequiredIngredient(RecipeIngredient ingredient) {
        requiredIngredients.add(ingredient);
    }
}
