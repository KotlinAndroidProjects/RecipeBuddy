package com.jaennova.recipebuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.jaennova.recipebuddy.ui.navigation.AppNavigation
import com.jaennova.recipebuddy.ui.theme.RecipeBuddyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipeBuddyTheme {
                AppNavigation()
            }
        }
    }
}
