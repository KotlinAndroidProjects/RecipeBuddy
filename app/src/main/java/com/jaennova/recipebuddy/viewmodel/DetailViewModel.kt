package com.jaennova.recipebuddy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jaennova.recipebuddy.data.api.RetrofitClient
import com.jaennova.recipebuddy.data.model.Meal
import com.jaennova.recipebuddy.data.model.MealDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val apiService = RetrofitClient.instance

    private val _meal = MutableLiveData<UIState<Meal>>()
    val meal: LiveData<UIState<Meal>> = _meal

    fun loadMealDetail(mealId: String) {
        _meal.value = UIState.Loading

        apiService.lookupMealById(mealId).enqueue(object : Callback<MealDetailResponse> {
            override fun onResponse(
                call: Call<MealDetailResponse>,
                response: Response<MealDetailResponse>
            ) {
                if (response.isSuccessful) {
                    val meals = response.body()?.meals
                    if (!meals.isNullOrEmpty()) {
                        _meal.value = UIState.Success(meals[0])
                    } else {
                        _meal.value = UIState.Error("No se encontró la receta")
                    }
                } else {
                    _meal.value = UIState.Error("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MealDetailResponse>, t: Throwable) {
                _meal.value = UIState.Error("Error de red: ${t.message}")
            }
        })
    }
}