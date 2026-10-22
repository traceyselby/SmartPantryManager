# Smart Pantry Manager

**Mobile App Development 700 — Practical Assignment**

**Application:** Smart Pantry Manager  
**Programming Language:** Java  
**Platform:** Android  
**Database:** SQLite  
**Development Environment:** Android Studio

## 1. Project Overview

Smart Pantry Manager is an Android application designed to help users manage ingredients they already have at home and reduce food waste.

The application allows users to maintain a digital pantry and discover recipes that can be prepared using only the available ingredients.

Unlike recipe applications that suggest meals requiring additional groceries, Smart Pantry Manager applies a strict ingredient-matching rule: **a recipe is suggested only when the pantry contains every required ingredient in sufficient quantity.**

## 2. Application Features

### 2.1 Pantry Management
- Add new pantry ingredients.
- View saved ingredients in a RecyclerView.
- Edit existing ingredient details.
- Delete ingredients with confirmation.
- Record ingredient names, quantities, measurement units and optional expiry dates.
- Store pantry information persistently using SQLite.

### 2.2 Ingredient Validation
- Ingredient names cannot be empty or exceed 100 characters.
- Quantities must be valid, finite, positive numbers.
- Measurement units are selected from predefined options.
- Expiry dates can be selected using an Android DatePickerDialog.

### 2.3 Suggested Recipes
- Includes 20 predefined recipes stored in SQLite.
- Displays recipes that can be prepared using available pantry ingredients.
- Excludes recipes with missing ingredients or insufficient quantities.
- Accounts for expired pantry ingredients.
- Supports compatible measurement-unit conversions.
- Displays the number of matching recipes.

### 2.4 Recipe Details
- Displays the selected recipe's name and description.
- Shows preparation time.
- Lists required ingredients and quantities.
- Provides cooking instructions.

### 2.5 Settings
- Displays pantry ingredient and recipe counts.
- Allows users to clear their pantry after confirmation.
- Provides basic application information.

## 3. Application Screens

The application contains five main screens:

1. **Pantry List:** Displays available ingredients and provides navigation to other screens.
2. **Add/Edit Ingredient:** Allows users to create or update pantry items.
3. **Suggested Recipes:** Displays recipes that meet the strict-matching requirements.
4. **Recipe Detail:** Shows ingredients, preparation time and cooking instructions.
5. **Settings:** Provides pantry statistics and pantry-management functionality.

## 4. Technologies Used

| Technology | Purpose |
|---|---|
| Java | Application development and business logic |
| Android SDK | Android application functionality |
| Android Studio | Development and debugging |
| SQLite | Persistent local data storage |
| SQLiteOpenHelper | Database creation, upgrades and access |
| RecyclerView | Displaying pantry ingredients and recipes |
| XML | User-interface layouts |
| Gradle | Building and managing the Android project |
| Git and GitHub | Version control |

## 5. Database Design

The application uses a local SQLite database named `SmartPantry.db`.

### Ingredients Table

Stores the user's pantry ingredients, including:

- Ingredient ID
- Ingredient name
- Quantity
- Measurement unit
- Optional expiry date

### Recipes Table

Stores recipe information, including:

- Recipe ID
- Recipe name
- Description
- Cooking instructions
- Preparation time

### Recipe Ingredients Table

Stores the ingredients required by each recipe, including:

- Record ID
- Recipe ID
- Ingredient name
- Required quantity
- Measurement unit

The database is populated with 20 predefined recipes.

## 6. Strict Recipe-Matching Logic

Strict recipe matching is the application's primary business rule.

A recipe is suggested only when **all** of its ingredient requirements can be satisfied by the user's pantry.

The matching algorithm:

1. Retrieves the user's saved pantry ingredients.
2. Retrieves available recipes and their required ingredients.
3. Normalises ingredient names for comparison.
4. Checks whether each required ingredient exists.
5. Converts compatible measurement units where necessary.
6. Checks whether the available quantity is sufficient.
7. Excludes expired pantry ingredients.
8. Displays the recipe only when every requirement is satisfied.

For example, if a recipe requires 200 ml of milk but the pantry contains only 100 ml, that recipe is not suggested.

Compatible measurement units include grams and kilograms, millilitres and litres, and supported cooking-volume units.

## 7. How to Build and Run

### Requirements

- Android Studio
- Android SDK
- Java-compatible Android development environment
- Android emulator or compatible Android device

### Instructions

1. Clone the GitHub repository:

   ```bash
   git clone https://github.com/traceyselby/SmartPantryManager.git
   ```

2. Open the project folder in Android Studio.
3. Allow Gradle to synchronise the project.
4. Start an Android emulator or connect an Android device.
5. Build and run the application.

Alternatively, build a debug APK from the project root using:

```bash
./gradlew :app:assembleDebug
```

The generated APK is located at:

```text
app/build/outputs/apk/debug/app-debug.apk
```

## 8. Testing

The application has been tested through Android emulator interaction and Gradle build checks.

### Functional Tests

- Adding, editing and deleting pantry ingredients.
- Saving ingredients persistently.
- Navigating between application screens.
- Viewing recipe details.
- Clearing pantry ingredients with confirmation.
- Checking recipe matches against available ingredients.

### Strict-Matching Tests

The following scenarios were tested:

- Empty pantry.
- Missing required ingredients.
- Insufficient ingredient quantity.
- Compatible unit conversion from litres to millilitres.
- Case-insensitive ingredient matching.
- Expired ingredients.

### Build and Code Quality

The following commands completed successfully:

```bash
./gradlew :app:assembleDebug
./gradlew :app:lintDebug
```

Android Lint may still report non-blocking warnings.

## 9. Project Structure

```text
SmartPantryManager/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/example/smartpantrymanager/
│   │       │   ├── MainActivity.java
│   │       │   ├── AddIngredientActivity.java
│   │       │   ├── SuggestedRecipesActivity.java
│   │       │   ├── RecipeDetailActivity.java
│   │       │   ├── SettingsActivity.java
│   │       │   ├── DatabaseHelper.java
│   │       │   ├── RecipeMatcher.java
│   │       │   ├── RecipeSeeder.java
│   │       │   ├── Ingredient.java
│   │       │   ├── Recipe.java
│   │       │   └── RecipeIngredient.java
│   │       ├── res/layout/
│   │       └── AndroidManifest.xml
│   └── build.gradle
├── gradle/
├── gradlew
└── README.md
```

The project also contains RecyclerView adapter classes for displaying ingredients and recipes.

## 10. Future Improvements

Possible future enhancements include:

- Ingredient expiry notifications.
- Barcode scanning.
- More predefined recipes.
- Recipe filtering by category.
- Improved accessibility and localisation.
- Cloud synchronisation and backup.

## 11. Conclusion

Smart Pantry Manager demonstrates Android application development using Java, XML layouts, RecyclerView, SQLite database persistence and structured business logic.

Its main objective is to help users reduce food waste by recommending recipes that can be prepared entirely from ingredients already available in their pantry.