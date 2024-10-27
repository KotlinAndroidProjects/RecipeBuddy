package com.jaennova.recipebuddy.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.jaennova.recipebuddy.data.api.RetrofitClient
import com.jaennova.recipebuddy.data.model.AreaListResponse
import com.jaennova.recipebuddy.data.model.CategoryListResponse
import com.jaennova.recipebuddy.data.model.FilterResponse
import com.jaennova.recipebuddy.data.model.Meal
import com.jaennova.recipebuddy.data.model.MealSearchResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFoodViewModel : ViewModel() {
    private val apiService = RetrofitClient.instance
    private val _uiState = MutableStateFlow(SearchFoodUiState())
    val uiState: StateFlow<SearchFoodUiState> = _uiState.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        loadCategories()
        loadAreas()
        _uiState.value = _uiState.value.copy(isLoading = false)
    }

    private fun loadCategories() {
        apiService.getCategoriesList().enqueue(object : Callback<CategoryListResponse> {
            override fun onResponse(
                call: Call<CategoryListResponse>,
                response: Response<CategoryListResponse>
            ) {
                if (response.isSuccessful) {
                    // Aquí asumimos que la respuesta contiene una lista de categorías
                    val categories = response.body()?.categories ?: emptyList()
                    _uiState.value =
                        _uiState.value.copy(categories = categories.map { it.strCategory })
                } else {
                    handleError("Error al cargar categorías: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<CategoryListResponse>, t: Throwable) {
                handleError("Error de red: ${t.message}")
            }
        })
    }


    private fun loadAreas() {
        apiService.getAreasList().enqueue(object : Callback<AreaListResponse> {
            override fun onResponse(
                call: Call<AreaListResponse>,
                response: Response<AreaListResponse>
            ) {
                if (response.isSuccessful) {
                    val areas = response.body()?.meals?.map { it.strArea } ?: emptyList()
                    _uiState.value = _uiState.value.copy(areas = areas)
                } else {
                    handleError("Error al cargar áreas: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<AreaListResponse>, t: Throwable) {
                handleError("Error de red: ${t.message}")
            }
        })
    }

    fun onSearchQueryChange(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query, isLoading = true)
        if (query.isEmpty()) {
            _uiState.value = _uiState.value.copy(meals = emptyList(), isLoading = false)
            return
        }
        searchMeals(query)
    }

    private fun searchMeals(query: String) {
        apiService.searchMealByName(query).enqueue(object : Callback<MealSearchResponse> {
            override fun onResponse(
                call: Call<MealSearchResponse>,
                response: Response<MealSearchResponse>
            ) {
                if (response.isSuccessful) {
                    val meals = response.body()?.meals ?: emptyList()
                    _uiState.value =
                        _uiState.value.copy(meals = meals, isLoading = false, error = null)
                } else {
                    handleError("Error en la búsqueda: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MealSearchResponse>, t: Throwable) {
                handleError("Error de red: ${t.message}")
            }
        })
    }

    fun onCategorySelected(category: String?) {
        _uiState.value = _uiState.value.copy(selectedCategory = category, isLoading = true)
        if (category == null) {
            performSearch()
            return
        }
        filterMealsByCategory(category)
    }

    private fun filterMealsByCategory(category: String) {
        apiService.filterByCategory(category).enqueue(object : Callback<FilterResponse> {
            override fun onResponse(
                call: Call<FilterResponse>,
                response: Response<FilterResponse>
            ) {
                if (response.isSuccessful) {
                    val meals = response.body()?.meals?.map { filtered ->
                        Meal(
                            idMeal = filtered.idMeal,
                            strMeal = filtered.strMeal,
                            strMealThumb = filtered.strMealThumb,
                            strCategory = category,
                            strArea = null,
                            strInstructions = null,
                            strDrinkAlternate = null,
                            strTags = null,
                            strYoutube = null,
                            strIngredient1 = null,
                            strIngredient2 = null,
                            strIngredient3 = null,
                            strMeasure1 = null,
                            strMeasure2 = null,
                            strMeasure3 = null,
                            strSource = null,
                            dateModified = null
                        )
                    } ?: emptyList()
                    _uiState.value =
                        _uiState.value.copy(meals = meals, isLoading = false, error = null)
                } else {
                    handleError("Error al filtrar por categoría: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<FilterResponse>, t: Throwable) {
                handleError("Error de red: ${t.message}")
            }
        })
    }

    fun onAreaSelected(area: String?) {
        _uiState.value = _uiState.value.copy(selectedArea = area, isLoading = true)
        if (area == null) {
            performSearch()
            return
        }
        filterMealsByArea(area)
    }

    private fun filterMealsByArea(area: String) {
        apiService.filterByArea(area).enqueue(object : Callback<FilterResponse> {
            override fun onResponse(
                call: Call<FilterResponse>,
                response: Response<FilterResponse>
            ) {
                if (response.isSuccessful) {
                    val meals = response.body()?.meals?.map { filtered ->
                        Meal(
                            idMeal = filtered.idMeal,
                            strMeal = filtered.strMeal,
                            strMealThumb = filtered.strMealThumb,
                            strCategory = null,
                            strArea = area,
                            strInstructions = null,
                            strDrinkAlternate = null,
                            strTags = null,
                            strYoutube = null,
                            strIngredient1 = null,
                            strIngredient2 = null,
                            strIngredient3 = null,
                            strMeasure1 = null,
                            strMeasure2 = null,
                            strMeasure3 = null,
                            strSource = null,
                            dateModified = null
                        )
                    } ?: emptyList()
                    _uiState.value =
                        _uiState.value.copy(meals = meals, isLoading = false, error = null)
                } else {
                    handleError("Error al filtrar por área: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<FilterResponse>, t: Throwable) {
                handleError("Error de red: ${t.message}")
            }
        })
    }

    private fun handleError(message: String) {
        _uiState.value = _uiState.value.copy(error = message, isLoading = false)
    }

    private fun performSearch() {
        val query = _uiState.value.searchQuery
        if (query.isNotEmpty()) searchMeals(query)
        else _uiState.value = _uiState.value.copy(meals = emptyList(), isLoading = false)
    }
}
