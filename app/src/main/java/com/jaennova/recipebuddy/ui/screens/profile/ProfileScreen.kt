package com.jaennova.recipebuddy.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jaennova.recipebuddy.ui.screens.appcomponets.RecipeBottomNavigation
import com.jaennova.recipebuddy.ui.screens.appcomponets.RecipeTopAppBar
import com.jaennova.recipebuddy.ui.screens.appcomponets.ScreenEmptyState

@Composable
fun ProfileScreen(navController: NavController) {
    Scaffold(
        topBar = { RecipeTopAppBar() },
        bottomBar = { RecipeBottomNavigation(navController) }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            CardProfile()
            SavedSection()
            ScreenEmptyState(
                message = "Aqui apareceran las recetas que guardes"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(rememberNavController())
}
