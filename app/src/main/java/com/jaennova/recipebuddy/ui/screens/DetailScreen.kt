package com.jaennova.recipebuddy.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jaennova.recipebuddy.data.model.Meal
import com.jaennova.recipebuddy.ui.screens.componets.detail.DetailContent
import com.jaennova.recipebuddy.ui.screens.componets.home.ErrorMessage
import com.jaennova.recipebuddy.ui.viewmodel.DetailViewModel
import com.jaennova.recipebuddy.ui.viewmodel.UIState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    mealId: String,
    viewModel: DetailViewModel = viewModel(),
    onBackClick: () -> Unit
) {
    val mealState by viewModel.meal.observeAsState()

    LaunchedEffect(mealId) {
        viewModel.loadMealDetail(mealId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de la receta") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Regresar"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (mealState) {
                is UIState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is UIState.Success -> {
                    val meal = (mealState as UIState.Success<Meal>).data
                    DetailContent(meal = meal)
                }

                is UIState.Error -> {
                    ErrorMessage(
                        message = (mealState as UIState.Error).message,
                        onRetry = { viewModel.loadMealDetail(mealId) }
                    )
                }

                null -> { /* Estado inicial */
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    DetailScreen(
        mealId = "52772",
        viewModel = DetailViewModel(),
        onBackClick = {}
    )
}
