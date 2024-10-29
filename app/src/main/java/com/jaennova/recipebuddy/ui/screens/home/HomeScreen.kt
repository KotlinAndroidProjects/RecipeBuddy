package com.jaennova.recipebuddy.ui.screens.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jaennova.recipebuddy.data.model.Category
import com.jaennova.recipebuddy.data.model.FilteredMeal
import com.jaennova.recipebuddy.data.model.Meal
import com.jaennova.recipebuddy.ui.screens.appcomponets.ErrorMessage
import com.jaennova.recipebuddy.ui.screens.appcomponets.LoadingIndicator
import com.jaennova.recipebuddy.ui.screens.appcomponets.RecipeBottomNavigation
import com.jaennova.recipebuddy.ui.screens.appcomponets.RecipeTopAppBar
import com.jaennova.recipebuddy.viewmodel.HomeViewModel
import com.jaennova.recipebuddy.viewmodel.UIState

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    navigateToDetail: (String) -> Unit,
    navController: NavController
) {
    val categoriesState by viewModel.categories.observeAsState()
    val randomMealState by viewModel.randomMeal.observeAsState()
    val categoryMealsState by viewModel.categoryMeals.observeAsState()
    LaunchedEffect(Unit) {
        viewModel.loadMealsByCategory("Beef")
        viewModel.refreshAllData()
    }

    Scaffold(
        topBar = { RecipeTopAppBar() },
        bottomBar = { RecipeBottomNavigation(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text(
                text = "¿Qué quieres cocinar hoy?",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )

            // Random Meal Section
            when (randomMealState) {
                is UIState.Loading -> {
                    LoadingIndicator()
                }

                is UIState.Success -> {
                    val meal = (randomMealState as UIState.Success<Meal>).data
                    RandomFoodCard(
                        foodName = meal.strMeal ?: "",
                        foodCategory = meal.strCategory ?: "",
                        foodArea = meal.strArea ?: "",
                        foodImage = meal.strMealThumb ?: "",
                        onClick = { navigateToDetail(meal.idMeal) }
                    )
                    Log.d("HomeScreen", "Random Meal: ${meal.strMeal}")
                }

                is UIState.Error -> {
                    ErrorMessage(
                        message = (randomMealState as UIState.Error).message,
                        onRetry = { viewModel.loadRandomMeal() }
                    )
                    Log.e("HomeScreen", (randomMealState as UIState.Error).message)
                }

                null -> { /* Estado inicial, no mostrar nada */
                }
            }

            // Categories Section
            when (categoriesState) {
                is UIState.Loading -> {
                    LoadingIndicator()
                }

                is UIState.Success -> {
                    val categories = (categoriesState as UIState.Success<List<Category>>).data
                    CategorySection(
                        categories = categories,
                        onCategorySelected = { category ->
                            viewModel.loadMealsByCategory(category.strCategory)
                        }
                    )
                }

                is UIState.Error -> {
                    ErrorMessage(
                        message = (categoriesState as UIState.Error).message,
                        onRetry = { viewModel.loadCategories() }
                    )
                }

                null -> { /* Estado inicial, no mostrar nada */
                }
            }

            // Category Meals Section
            when (categoryMealsState) {
                is UIState.Loading -> {
                    LoadingIndicator()
                }

                is UIState.Success -> {
                    val meals = (categoryMealsState as UIState.Success<List<FilteredMeal>>).data
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 96.dp),
                        modifier = Modifier.padding(8.dp)
                    ) {
                        items(meals) { meal ->
                            FoodCard(
                                foodName = meal.strMeal,
                                foodImage = meal.strMealThumb,
                                onClick = { navigateToDetail(meal.idMeal) }
                            )
                        }
                    }
                }

                is UIState.Error -> {
                    ErrorMessage(
                        message = (categoryMealsState as UIState.Error).message,
                        onRetry = { /* Re-load current category */ }
                    )
                    Log.e("HomeScreen", (categoryMealsState as UIState.Error).message)
                }

                null -> { /* Estado inicial, no mostrar nada */
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(viewModel = viewModel(), {}, rememberNavController())
}
