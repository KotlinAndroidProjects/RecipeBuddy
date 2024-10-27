package com.jaennova.recipebuddy.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jaennova.recipebuddy.data.api.RetrofitClient
import com.jaennova.recipebuddy.data.model.Category
import com.jaennova.recipebuddy.data.model.CategoryListResponse
import com.jaennova.recipebuddy.data.model.FilterResponse
import com.jaennova.recipebuddy.data.model.FilteredMeal
import com.jaennova.recipebuddy.data.model.Meal
import com.jaennova.recipebuddy.data.model.RandomMealResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val apiService = RetrofitClient.instance

    private val _categories = MutableLiveData<UIState<List<Category>>>()
    val categories: LiveData<UIState<List<Category>>> = _categories

    private val _categoryMeals = MutableLiveData<UIState<List<FilteredMeal>>>()
    val categoryMeals: LiveData<UIState<List<FilteredMeal>>> = _categoryMeals

    private val _randomMeal = MutableLiveData<UIState<Meal>>()
    val randomMeal: LiveData<UIState<Meal>> = _randomMeal

    fun loadCategories() {
        _categories.value = UIState.Loading

        apiService.getAllCategories().enqueue(object : Callback<CategoryListResponse> {
            override fun onResponse(
                call: Call<CategoryListResponse>,
                response: Response<CategoryListResponse>
            ) {
                if (response.isSuccessful) {
                    val categories = response.body()?.categories
                    if (categories != null) {
                        _categories.value = UIState.Success(categories)
                    } else {
                        _categories.value = UIState.Error("No se encontraron categorías")
                    }
                } else {
                    _categories.value = UIState.Error("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<CategoryListResponse>, t: Throwable) {
                _categories.value = UIState.Error("Error de red: ${t.message}")
            }
        })
    }


    // Función para cargar las comidas de una categoría
    fun loadMealsByCategory(category: String) {
        _categoryMeals.value = UIState.Loading

        apiService.filterByCategory(category).enqueue(object : Callback<FilterResponse> {
            override fun onResponse(
                call: Call<FilterResponse>,
                response: Response<FilterResponse>
            ) {
                if (response.isSuccessful) {
                    val meals = response.body()?.meals
                    if (meals != null) {
                        _categoryMeals.value = UIState.Success(meals)
                    } else {
                        _categoryMeals.value =
                            UIState.Error("No se encontraron comidas en esta categoría")
                    }
                } else {
                    _categoryMeals.value = UIState.Error("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<FilterResponse>, t: Throwable) {
                _categoryMeals.value = UIState.Error("Error de red: ${t.message}")
            }
        })
    }

    // Función para cargar una comida aleatoria
    fun loadRandomMeal() {
        _randomMeal.value = UIState.Loading

        apiService.getRandomMeal().enqueue(object : Callback<RandomMealResponse> {
            override fun onResponse(
                call: Call<RandomMealResponse>,
                response: Response<RandomMealResponse>
            ) {
                if (response.isSuccessful) {
                    val meals = response.body()?.meals
                    if (!meals.isNullOrEmpty()) {
                        _randomMeal.value = UIState.Success(meals[0])
                    } else {
                        _randomMeal.value = UIState.Error("No se encontró una comida aleatoria")
                    }
                } else {
                    _randomMeal.value = UIState.Error("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<RandomMealResponse>, t: Throwable) {
                _randomMeal.value = UIState.Error("Error de red: ${t.message}")
            }
        })
    }

    // Función para refrescar todos los datos
    fun refreshAllData() {
        loadCategories()
        loadRandomMeal()
    }
}