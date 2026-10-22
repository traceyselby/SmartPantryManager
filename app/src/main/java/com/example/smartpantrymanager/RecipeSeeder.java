
package com.example.smartpantrymanager;

import java.util.ArrayList;
import java.util.List;

public class RecipeSeeder {

    public static List<Recipe> getDefaultRecipes() {

        List<Recipe> recipes = new ArrayList<>();

        Recipe r;

        // 1. Scrambled Eggs
        r = new Recipe(1, "Scrambled Eggs",
                "Quick and creamy scrambled eggs.",
                "1. Beat eggs with milk.\n2. Melt butter in a pan.\n3. Cook eggs gently while stirring.",
                10);
        r.addRequiredIngredient(new RecipeIngredient("eggs", 2, "pieces"));
        r.addRequiredIngredient(new RecipeIngredient("milk", 30, "ml"));
        r.addRequiredIngredient(new RecipeIngredient("butter", 10, "g"));
        recipes.add(r);

        // 2. French Toast
        r = new Recipe(2, "French Toast",
                "Golden breakfast toast.",
                "1. Beat eggs and milk.\n2. Dip bread into mixture.\n3. Fry in butter until golden.",
                15);
        r.addRequiredIngredient(new RecipeIngredient("bread", 2, "pieces"));
        r.addRequiredIngredient(new RecipeIngredient("eggs", 1, "pieces"));
        r.addRequiredIngredient(new RecipeIngredient("milk", 50, "ml"));
        r.addRequiredIngredient(new RecipeIngredient("butter", 10, "g"));
        recipes.add(r);

        // 3. Pancakes
        r = new Recipe(3, "Pancakes",
                "Simple homemade pancakes.",
                "1. Mix flour, milk and eggs.\n2. Heat butter in a pan.\n3. Cook batter on both sides.",
                20);
        r.addRequiredIngredient(new RecipeIngredient("flour", 150, "g"));
        r.addRequiredIngredient(new RecipeIngredient("milk", 200, "ml"));
        r.addRequiredIngredient(new RecipeIngredient("eggs", 1, "pieces"));
        r.addRequiredIngredient(new RecipeIngredient("butter", 20, "g"));
        recipes.add(r);

        // 4. Cheese Omelette
        r = new Recipe(4, "Cheese Omelette",
                "Fluffy omelette with melted cheese.",
                "1. Beat eggs.\n2. Melt butter in a pan.\n3. Add eggs and cheese.\n4. Fold and serve.",
                10);
        r.addRequiredIngredient(new RecipeIngredient("eggs", 2, "pieces"));
        r.addRequiredIngredient(new RecipeIngredient("cheese", 40, "g"));
        r.addRequiredIngredient(new RecipeIngredient("butter", 10, "g"));
        recipes.add(r);

        // 5. Grilled Cheese Sandwich
        r = new Recipe(5, "Grilled Cheese Sandwich",
                "Crispy sandwich with melted cheese.",
                "1. Place cheese between bread slices.\n2. Butter the outside.\n3. Toast until golden.",
                10);
        r.addRequiredIngredient(new RecipeIngredient("bread", 2, "pieces"));
        r.addRequiredIngredient(new RecipeIngredient("cheese", 50, "g"));
        r.addRequiredIngredient(new RecipeIngredient("butter", 10, "g"));
        recipes.add(r);

        // 6. Peanut Butter Toast
        r = new Recipe(6, "Peanut Butter Toast",
                "Quick and filling snack.",
                "1. Toast bread.\n2. Spread peanut butter.\n3. Serve.",
                5);
        r.addRequiredIngredient(new RecipeIngredient("bread", 2, "pieces"));
        r.addRequiredIngredient(new RecipeIngredient("peanut butter", 30, "g"));
        recipes.add(r);

        // 7. Banana Smoothie
        r = new Recipe(7, "Banana Smoothie",
                "Creamy banana drink.",
                "1. Peel banana.\n2. Blend with milk.\n3. Serve chilled.",
                5);
        r.addRequiredIngredient(new RecipeIngredient("banana", 1, "pieces"));
        r.addRequiredIngredient(new RecipeIngredient("milk", 250, "ml"));
        recipes.add(r);

        // 8. Strawberry Smoothie
        r = new Recipe(8, "Strawberry Smoothie",
                "Refreshing fruit smoothie.",
                "1. Wash strawberries.\n2. Blend with milk and yoghurt.\n3. Serve.",
                5);
        r.addRequiredIngredient(new RecipeIngredient("strawberries", 150, "g"));
        r.addRequiredIngredient(new RecipeIngredient("milk", 150, "ml"));
        r.addRequiredIngredient(new RecipeIngredient("yoghurt", 100, "g"));
        recipes.add(r);

        // 9. Oatmeal
        r = new Recipe(9, "Oatmeal",
                "Warm breakfast oats.",
                "1. Combine oats and milk.\n2. Heat while stirring.\n3. Serve warm.",
                10);
        r.addRequiredIngredient(new RecipeIngredient("oats", 60, "g"));
        r.addRequiredIngredient(new RecipeIngredient("milk", 250, "ml"));
        recipes.add(r);

        // 10. Banana Oatmeal
        r = new Recipe(10, "Banana Oatmeal",
                "Oats topped with banana.",
                "1. Cook oats in milk.\n2. Slice banana.\n3. Add banana and serve.",
                10);
        r.addRequiredIngredient(new RecipeIngredient("oats", 60, "g"));
        r.addRequiredIngredient(new RecipeIngredient("milk", 250, "ml"));
        r.addRequiredIngredient(new RecipeIngredient("banana", 1, "pieces"));
        recipes.add(r);

        // 11. Tomato Pasta
        r = new Recipe(11, "Tomato Pasta",
                "Easy pasta with tomato sauce.",
                "1. Boil pasta.\n2. Heat tomato sauce with olive oil.\n3. Combine and serve.",
                20);
        r.addRequiredIngredient(new RecipeIngredient("pasta", 100, "g"));
        r.addRequiredIngredient(new RecipeIngredient("tomato sauce", 150, "g"));
        r.addRequiredIngredient(new RecipeIngredient("olive oil", 15, "ml"));
        recipes.add(r);

        // 12. Buttered Pasta
        r = new Recipe(12, "Buttered Pasta",
                "Simple buttery pasta.",
                "1. Boil pasta.\n2. Drain.\n3. Mix with melted butter.",
                15);
        r.addRequiredIngredient(new RecipeIngredient("pasta", 100, "g"));
        r.addRequiredIngredient(new RecipeIngredient("butter", 20, "g"));
        recipes.add(r);

        // 13. Cheese Pasta
        r = new Recipe(13, "Cheese Pasta",
                "Creamy cheese pasta.",
                "1. Boil pasta.\n2. Heat milk and cheese.\n3. Combine with pasta.",
                20);
        r.addRequiredIngredient(new RecipeIngredient("pasta", 100, "g"));
        r.addRequiredIngredient(new RecipeIngredient("cheese", 60, "g"));
        r.addRequiredIngredient(new RecipeIngredient("milk", 100, "ml"));
        recipes.add(r);

        // 14. Egg Fried Rice
        r = new Recipe(14, "Egg Fried Rice",
                "Quick rice and egg meal.",
                "1. Cook rice and cool slightly.\n2. Scramble eggs in oil.\n3. Add rice and stir-fry.",
                20);
        r.addRequiredIngredient(new RecipeIngredient("rice", 100, "g"));
        r.addRequiredIngredient(new RecipeIngredient("eggs", 2, "pieces"));
        r.addRequiredIngredient(new RecipeIngredient("vegetable oil", 15, "ml"));
        recipes.add(r);

        // 15. Garlic Bread
        r = new Recipe(15, "Garlic Bread",
                "Buttery garlic toast.",
                "1. Mix butter and garlic.\n2. Spread on bread.\n3. Toast until golden.",
                10);
        r.addRequiredIngredient(new RecipeIngredient("bread", 2, "pieces"));
        r.addRequiredIngredient(new RecipeIngredient("butter", 20, "g"));
        r.addRequiredIngredient(new RecipeIngredient("garlic", 2, "pieces"));
        recipes.add(r);

        // 16. Mashed Potatoes
        r = new Recipe(16, "Mashed Potatoes",
                "Smooth and creamy potatoes.",
                "1. Peel and boil potatoes.\n2. Mash with milk and butter.\n3. Serve.",
                25);
        r.addRequiredIngredient(new RecipeIngredient("potatoes", 3, "pieces"));
        r.addRequiredIngredient(new RecipeIngredient("milk", 100, "ml"));
        r.addRequiredIngredient(new RecipeIngredient("butter", 20, "g"));
        recipes.add(r);

        // 17. Baked Potato
        r = new Recipe(17, "Baked Potato",
                "Simple oven-baked potato.",
                "1. Wash potato.\n2. Rub with olive oil.\n3. Bake until tender.",
                45);
        r.addRequiredIngredient(new RecipeIngredient("potatoes", 1, "pieces"));
        r.addRequiredIngredient(new RecipeIngredient("olive oil", 10, "ml"));
        recipes.add(r);

        // 18. Tuna Sandwich
        r = new Recipe(18, "Tuna Sandwich",
                "Easy tuna sandwich.",
                "1. Mix tuna with mayonnaise.\n2. Spread on bread.\n3. Serve.",
                10);
        r.addRequiredIngredient(new RecipeIngredient("bread", 2, "pieces"));
        r.addRequiredIngredient(new RecipeIngredient("tuna", 100, "g"));
        r.addRequiredIngredient(new RecipeIngredient("mayonnaise", 20, "g"));
        recipes.add(r);

        // 19. Egg Sandwich
        r = new Recipe(19, "Egg Sandwich",
                "Simple boiled egg sandwich.",
                "1. Boil eggs.\n2. Slice eggs.\n3. Place between bread slices.",
                15);
        r.addRequiredIngredient(new RecipeIngredient("bread", 2, "pieces"));
        r.addRequiredIngredient(new RecipeIngredient("eggs", 2, "pieces"));
        recipes.add(r);

        // 20. Banana Peanut Butter Toast
        r = new Recipe(20, "Banana Peanut Butter Toast",
                "Sweet and filling breakfast.",
                "1. Toast bread.\n2. Spread peanut butter.\n3. Top with banana slices.",
                5);
        r.addRequiredIngredient(new RecipeIngredient("bread", 2, "pieces"));
        r.addRequiredIngredient(new RecipeIngredient("peanut butter", 30, "g"));
        r.addRequiredIngredient(new RecipeIngredient("banana", 1, "pieces"));
        recipes.add(r);

        return recipes;
    }
}
