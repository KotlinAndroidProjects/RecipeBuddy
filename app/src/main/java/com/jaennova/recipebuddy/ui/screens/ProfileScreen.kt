package com.jaennova.recipebuddy.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jaennova.recipebuddy.ui.screens.componets.RecipeBottomNavigation
import com.jaennova.recipebuddy.ui.screens.componets.home.RecipeTopAppBar
import com.jaennova.recipebuddy.ui.screens.componets.profile.CardProfile
import com.jaennova.recipebuddy.ui.screens.componets.profile.CardRecipeSaved
import com.jaennova.recipebuddy.ui.screens.componets.profile.SavedSection

@Composable
fun ProfileScreen(navController: NavController) {
    Scaffold(
        topBar = { RecipeTopAppBar() },
        bottomBar = { RecipeBottomNavigation(navController) }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            CardProfile()
            SavedSection()
            CardRecipeSaved(
                foodImage = "https://www.themealdb.com/images/media/meals/llcbn01574260722.jpg",
                foodName = "Beef and Mustard Pie",
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(rememberNavController())
}
