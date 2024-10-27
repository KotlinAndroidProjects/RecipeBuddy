package com.jaennova.recipebuddy.ui.viewmodel

import com.jaennova.recipebuddy.data.model.Meal

data class SearchFoodUiState(
    val searchQuery: String = "",
    val meals: List<Meal> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedCategory: String? = null,
    val selectedArea: String? = null,
    val categories: List<String> = emptyList(),
    val areas: List<String> = emptyList()
)