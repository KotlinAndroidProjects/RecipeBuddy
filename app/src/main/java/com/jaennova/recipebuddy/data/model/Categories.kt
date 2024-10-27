package com.jaennova.recipebuddy.data.model

data class Category(
    val idCategory: String,
    val strCategory: String,
    val strCategoryThumb: String?,
    val strCategoryDescription: String?
)

data class CategoryListResponse(
    val categories: List<Category>?
)

data class CategoryItem(
    val strCategory: String
)

data class CategoryList(
    val meals: List<CategoryItem>?
)