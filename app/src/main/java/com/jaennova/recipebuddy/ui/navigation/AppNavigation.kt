package com.jaennova.recipebuddy.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jaennova.recipebuddy.ui.screens.detail.DetailScreen
import com.jaennova.recipebuddy.ui.screens.home.HomeScreen
import com.jaennova.recipebuddy.ui.screens.profile.ProfileScreen
import com.jaennova.recipebuddy.ui.screens.appcomponets.BottomNavItem
import com.jaennova.recipebuddy.ui.screens.search.SearchScreen

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