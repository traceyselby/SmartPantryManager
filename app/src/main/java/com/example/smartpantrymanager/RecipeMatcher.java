
package com.example.smartpantrymanager;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RecipeMatcher {

    public static List<Recipe> findMatchingRecipes(
            List<Recipe> recipes,
            List<Ingredient> pantryIngredients) {

        List<Recipe> matchingRecipes = new ArrayList<>();

        for (Recipe recipe : recipes) {
            if (canMakeRecipe(recipe, pantryIngredients)) {
                matchingRecipes.add(recipe);
            }
        }

        return matchingRecipes;
    }

    public static boolean canMakeRecipe(
            Recipe recipe,
            List<Ingredient> pantryIngredients) {

        if (recipe == null
                || recipe.getRequiredIngredients() == null
                || recipe.getRequiredIngredients().isEmpty()) {
            return false;
        }

        // Track quantities already allocated to requirements.
        Map<String, Double> available = new HashMap<>();

        for (Ingredient ingredient : pantryIngredients) {

            if (ingredient == null
                    || ingredient.getQuantity() <= 0
                    || !Double.isFinite(ingredient.getQuantity())
                    || isExpired(ingredient.getExpiryDate())) {
                continue;
            }

            String name = normalizeName(ingredient.getName());
            UnitInfo unit = normalizeUnit(ingredient.getUnit());

            if (name.isEmpty() || unit == null) {
                continue;
            }

            String key = name + "|" + unit.dimension;

            available.merge(
                    key,
                    ingredient.getQuantity() * unit.factor,
                    Double::sum
            );
        }

        for (RecipeIngredient required :
                recipe.getRequiredIngredients()) {

            if (required == null
                    || required.getQuantity() <= 0
                    || !Double.isFinite(required.getQuantity())) {
                return false;
            }

            String name = normalizeName(required.getName());
            UnitInfo unit = normalizeUnit(required.getUnit());

            if (name.isEmpty() || unit == null) {
                return false;
            }

            String key = name + "|" + unit.dimension;
            double needed = required.getQuantity() * unit.factor;
            double remaining = available.getOrDefault(key, 0.0);

            // Small tolerance for floating-point calculations.
            if (remaining + 1e-9 < needed) {
                return false;
            }

            // Deduct allocated quantity so it cannot be reused.
            available.put(key, remaining - needed);
        }

        return true;
    }

    private static String normalizeName(String name) {

        if (name == null) {
            return "";
        }

        String normalized = Normalizer.normalize(
                name.trim().toLowerCase(Locale.ROOT),
                Normalizer.Form.NFKC
        );

        normalized = normalized.replaceAll("\\s+", " ");

        // Handle common singular/plural ingredient names.
        switch (normalized) {
            case "egg":
                return "eggs";
            case "potato":
                return "potatoes";
            case "strawberry":
                return "strawberries";
            case "bananas":
                return "banana";
            case "tomatoes":
                return "tomato";
            default:
                return normalized;
        }
    }

    private static UnitInfo normalizeUnit(String unit) {

        if (unit == null) {
            return null;
        }

        String normalized = unit.trim().toLowerCase(Locale.ROOT);

        switch (normalized) {

            // Mass, normalized to grams
            case "g":
            case "gram":
            case "grams":
                return new UnitInfo("mass", 1.0);

            case "kg":
            case "kilogram":
            case "kilograms":
                return new UnitInfo("mass", 1000.0);

            // Volume, normalized to millilitres
            case "ml":
            case "millilitre":
            case "millilitres":
            case "milliliter":
            case "milliliters":
                return new UnitInfo("volume", 1.0);

            case "l":
            case "litre":
            case "litres":
            case "liter":
            case "liters":
                return new UnitInfo("volume", 1000.0);

            case "tsp":
            case "teaspoon":
            case "teaspoons":
                return new UnitInfo("volume", 5.0);

            case "tbsp":
            case "tablespoon":
            case "tablespoons":
                return new UnitInfo("volume", 15.0);

            case "cup":
            case "cups":
                return new UnitInfo("volume", 250.0);

            // Individual items
            case "piece":
            case "pieces":
            case "unit":
            case "units":
                return new UnitInfo("count", 1.0);

            default:
                return null;
        }
    }

    private static boolean isExpired(String expiryDate) {

        if (expiryDate == null || expiryDate.trim().isEmpty()) {
            return false;
        }

        try {
            LocalDate expiry = LocalDate.parse(expiryDate.trim());
            return expiry.isBefore(LocalDate.now());
        } catch (DateTimeParseException e) {
            // Do not count ingredients with invalid expiry dates.
            return true;
        }
    }

    private static class UnitInfo {

        final String dimension;
        final double factor;

        UnitInfo(String dimension, double factor) {
            this.dimension = dimension;
            this.factor = factor;
        }
    }
}
