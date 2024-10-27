package com.jaennova.recipebuddy.data.model

data class IngredientItem(
    val idIngredient: String?,
    val strIngredient: String,
    val strDescription: String?,
    val strType: String?
)

data class IngredientList(
    val meals: List<IngredientItem>?
)
