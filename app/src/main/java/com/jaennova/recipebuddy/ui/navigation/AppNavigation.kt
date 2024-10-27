package com.jaennova.recipebuddy.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jaennova.recipebuddy.ui.screens.DetailScreen
import com.jaennova.recipebuddy.ui.screens.HomeScreen
import com.jaennova.recipebuddy.ui.screens.ProfileScreen
import com.jaennova.recipebuddy.ui.screens.componets.BottomNavItem
import com.jaennova.recipebuddy.ui.screens.SearchScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                navigateToDetail = { mealId ->
                    navController.navigate(Screen.Detail.createRoute(mealId))
                },
                navController = navController
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("mealId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val mealId = backStackEntry.arguments?.getString("mealId")
            DetailScreen(
                mealId = mealId ?: "",
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(BottomNavItem.Search.route) {
            SearchScreen(navController)
        }
        composable(BottomNavItem.Profile.route) {
            ProfileScreen(navController)
        }
    }
}