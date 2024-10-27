package com.jaennova.recipebuddy.data.model

data class Meal(
    val idMeal: String,
    val strMeal: String?,
    val strDrinkAlternate: String?,
    val strCategory: String?,
    val strArea: String?,
    val strInstructions: String?,
    val strMealThumb: String?,
    val strTags: String?,
    val strYoutube: String?,
    val strIngredient1: String?,
    val strIngredient2: String?,
    val strIngredient3: String?,
    // Agrega hasta el ingrediente 20 si es necesario
    val strMeasure1: String?,
    val strMeasure2: String?,
    val strMeasure3: String?,
    // Agrega hasta el measure 20 si es necesario
    val strSource: String?,
    val dateModified: String?
)