package com.jaennova.recipebuddy.data.model

data class MealResponse(
    val meals: List<Meal>
)

data class MealSearchResponse(
    val meals: List<Meal>?
)

data class MealByLetterResponse(
    val meals: List<Meal>?
)

data class RandomMealResponse(
    val meals: List<Meal>?
)

data class FilteredMeal(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
)

data class FilterResponse(
    val meals: List<FilteredMeal>?
)

data class MealDetailResponse(
    val meals: List<Meal>?
)
