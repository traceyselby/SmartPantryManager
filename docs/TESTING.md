# Smart Pantry Manager — Testing Documentation

**Module:** Mobile App Development 700  
**Application:** Smart Pantry Manager  
**Platform:** Android  
**Language:** Java  
**Database:** SQLite

## 1. Testing Objectives

The purpose of testing is to verify that Smart Pantry Manager functions correctly and satisfies the assignment requirements.

Testing focuses on:

- Pantry ingredient management.
- Input validation.
- SQLite data persistence.
- Strict recipe matching.
- Measurement-unit conversion.
- Expiry-date handling.
- Navigation between application screens.
- Application build and Android compatibility.

## 2. Functional Test Cases

| Test ID | Test Description | Expected Result | Status |
|---|---|---|---|
| FT-01 | Open the application | Pantry Home screen displays | Passed |
| FT-02 | Navigate to Add Ingredient | Ingredient form opens | Passed |
| FT-03 | Add a valid ingredient | Ingredient is saved and displayed | Passed |
| FT-04 | Edit an existing ingredient | Updated details are saved | Passed |
| FT-05 | Delete an ingredient | Confirmation dialog appears and ingredient can be deleted | Passed |
| FT-06 | Open Suggested Recipes | Matching recipe list displays | Passed |
| FT-07 | Select a suggested recipe | Recipe details display | Passed |
| FT-08 | Open Settings | Settings screen displays | Passed |
| FT-09 | Clear pantry | Confirmation dialog appears and pantry can be cleared | Passed |
| FT-10 | Select an expiry date | DatePicker opens and displays selected date | Passed |

## 3. Strict Recipe-Matching Tests

Strict matching is the most important business rule in the application.

| Test ID | Test Scenario | Expected Result | Status |
|---|---|---|---|
| RM-01 | Pantry contains no ingredients | No recipes suggested | Passed |
| RM-02 | Required ingredient is missing | Recipe excluded | Passed |
| RM-03 | Pantry quantity is insufficient | Recipe excluded | Passed |
| RM-04 | Pantry contains milk in litres and recipe requires millilitres | Compatible units converted correctly | Passed |
| RM-05 | Ingredient name uses uppercase letters | Case-insensitive matching works | Passed |
| RM-06 | Required pantry ingredient has expired | Recipe excluded | Passed |

## 4. Input Validation Tests

| Test ID | Test Scenario | Expected Result | Status |
|---|---|---|---|
| IV-01 | Empty ingredient name | Validation error | Passed |
| IV-02 | Empty quantity | Validation error | Passed |
| IV-03 | Quantity of zero | Validation error | Passed |
| IV-04 | Negative quantity | Validation error | Passed |
| IV-05 | Non-numeric quantity | Validation error | Passed |
| IV-06 | Valid decimal quantity | Ingredient accepted | Passed |
| IV-07 | Ingredient name longer than 100 characters | Validation error | Passed |

## 5. Persistence Tests

| Test ID | Test Scenario | Expected Result | Status |
|---|---|---|---|
| DB-01 | Add an ingredient and reopen the app | Ingredient remains saved | Passed |
| DB-02 | Edit an ingredient and reopen the app | Updated values remain saved | Passed |
| DB-03 | Delete an ingredient and reopen the app | Deleted ingredient remains absent | Passed |

## 6. Build and Static Analysis

| Test | Command | Result |
|---|---|---|
| Debug APK compilation | `./gradlew :app:assembleDebug` | Passed |
| Android Lint | `./gradlew :app:lintDebug` | Passed |
| Expiry-date accessibility checks | Lint warning search | No matching warnings |

Android Lint completed without blocking errors. Some non-blocking warnings may remain.

## 7. Testing Limitations

Testing was performed during development using an Android emulator.

The documentation does not establish compatibility across every Android device, screen size or operating-system version.

Additional device testing and automated regression tests would improve confidence in future releases.


## 8. Conclusion

Smart Pantry Manager successfully passed the recorded
functional, strict recipe-matching, input-validation
and SQLite persistence tests.

Testing confirmed that:

- Pantry ingredients can be added, edited and deleted.
- Ingredient data persists after restarting the application.
- Invalid ingredient names and quantities are rejected.
- Recipes are suggested only when all required ingredients
  are available in sufficient quantities.
- Compatible measurement units are handled correctly.
- Expired ingredients are excluded from recipe matching.
- Application navigation and the expiry-date picker work.
- Gradle compilation and Android Lint complete successfully.

All 7 input-validation tests and all 3 SQLite persistence
tests passed.

The application meets the tested functional requirements
and is ready for final documentation and submission review.
