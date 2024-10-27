package com.jaennova.recipebuddy.data.api

import com.jaennova.recipebuddy.data.model.AreaList
import com.jaennova.recipebuddy.data.model.CategoryList
import com.jaennova.recipebuddy.data.model.CategoryListResponse
import com.jaennova.recipebuddy.data.model.FilterResponse
import com.jaennova.recipebuddy.data.model.IngredientList
import com.jaennova.recipebuddy.data.model.MealByLetterResponse
import com.jaennova.recipebuddy.data.model.MealDetailResponse
import com.jaennova.recipebuddy.data.model.MealSearchResponse
import com.jaennova.recipebuddy.data.model.RandomMealResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    // Search meal by name
    @GET("search.php")
    fun searchMealByName(@Query("s") name: String): Call<MealSearchResponse>

    // List all meals by first letter
    @GET("search.php")
    fun listMealsByFirstLetter(@Query("f") letter: Char): Call<MealByLetterResponse>

    // Lookup full meal details by id
    @GET("lookup.php")
    fun lookupMealById(@Query("i") id: String): Call<MealDetailResponse>

    // Lookup a single random meal
    @GET("random.php")
    fun getRandomMeal(): Call<RandomMealResponse>

    // List all meal categories
    @GET("categories.php")
    fun getAllCategories(): Call<CategoryListResponse>


    // List all categories
    @GET("list.php")
    fun getCategoriesList(@Query("c") type: String = "list"): Call<CategoryList>

    // List all areas
    @GET("list.php")
    fun getAreasList(@Query("a") type: String = "list"): Call<AreaList>

    // List all ingredients
    @GET("list.php")
    fun getIngredientsList(@Query("i") type: String = "list"): Call<IngredientList>

    // Filter by main ingredient
    @GET("filter.php")
    fun filterByIngredient(@Query("i") ingredient: String): Call<FilterResponse>

    // Filter by category
    @GET("filter.php")
    fun filterByCategory(@Query("c") category: String): Call<FilterResponse>

    // Filter by area
    @GET("filter.php")
    fun filterByArea(@Query("a") area: String): Call<FilterResponse>
}
