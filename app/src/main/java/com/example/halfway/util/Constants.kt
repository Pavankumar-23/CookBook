package com.example.halfway.util


class Constants {

    companion object {

        //Network Constants
        const val BASE_URL = "https://api.spoonacular.com/"
        const val IMAGE_BASE_URL = "https://spoonacular.com/cdn/ingredients_250x250/"
        const val RECIPE_IMAGE_BASE_URL = "https://spoonacular.com/recipeImages/"
        const val CONNECTION_TIMEOUT: Long = 40 // 10 seconds
        const val READ_TIMEOUT: Long = 10 // 2 seconds
        const val WRITE_TIMEOUT: Long = 10

        //Database Constants
        const val DATABASE_NAME = "facts_database"
        const val RECIPE_TABLE_NAME = "recipe_info"

        //API KEY
        const val API_KEY = "368af4ea1f5b408ba18da1d7cbd1c864"

        //API Query Keys
        const val QUERY_NUMBER = "number"
        const val QUERY_ADD_RECIPE_NUTRITION = "addRecipeNutrition"
        const val QUERY_FILL_INGREDIENTS = "fillIngredients"
        const val QUERY_DIET = "diet"
        const val QUERY_TYPE = "type"
        const val QUERY_PAGE_OFFSET = "offset"

        //Default Preference
        const val DEFAULT_RECIPES_COUNT = "100"
        const val DEFAULT_MEAL_TYPE = "main course"
        const val DEFAULT_DIET_TYPE = "gluten free"
        const val DEFAULT_PAGE_INDEX: Int = 1

        //Preferences
        const val PREFERENCES_NAME = "recipe_preferences"
        const val PREFERENCES_MEAL_TYPE = "mealType"
        const val PREFERENCES_MEAL_TYPE_ID = "mealTypeId"
        const val PREFERENCES_DIET_TYPE = "dietType"
        const val PREFERENCES_DIET_TYPE_ID = "dietTypeId"
    }
}