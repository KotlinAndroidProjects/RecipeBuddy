package com.jaennova.recipebuddy.ui.screens.search

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jaennova.recipebuddy.ui.navigation.Screen
import com.jaennova.recipebuddy.ui.screens.appcomponets.RecipeBottomNavigation
import com.jaennova.recipebuddy.ui.screens.appcomponets.RecipeTopAppBar
import com.jaennova.recipebuddy.viewmodel.SearchFoodViewModel

@Composable
fun SearchScreen(navController: NavController) {
    val viewModel: SearchFoodViewModel = viewModel()

    Scaffold(
        topBar = { RecipeTopAppBar() },
        bottomBar = { RecipeBottomNavigation(navController) }
    ) { paddingValues ->
        SearchFoodScreen(
            modifier = Modifier.padding(paddingValues),
            viewModel = viewModel,
            onMealClick = { mealId ->
                navController.navigate(Screen.Detail.createRoute(mealId))
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    SearchScreen(rememberNavController())
}