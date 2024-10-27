package com.jaennova.recipebuddy.data.model

data class AreaItem(
    val strArea: String
)

data class AreaListResponse(
    val meals: List<AreaItem>?
)
