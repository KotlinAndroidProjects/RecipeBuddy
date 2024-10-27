package com.jaennova.recipebuddy.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jaennova.recipebuddy.ui.screens.componets.RecipeBottomNavigation
import com.jaennova.recipebuddy.ui.screens.componets.home.RecipeTopAppBar

@Composable
fun SearchScreen(navController: NavController) {
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
                text = "Search Screen",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    SearchScreen(rememberNavController())
}