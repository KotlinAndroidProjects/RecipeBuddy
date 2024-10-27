package com.jaennova.recipebuddy.data.model

data class AreaItem(
    val strArea: String
)

data class AreaList(
    val meals: List<AreaItem>?
)
