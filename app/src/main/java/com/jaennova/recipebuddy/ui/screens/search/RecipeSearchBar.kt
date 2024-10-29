package com.jaennova.recipebuddy.ui.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jaennova.recipebuddy.data.model.Meal
import com.jaennova.recipebuddy.ui.screens.appcomponets.ErrorMessage
import com.jaennova.recipebuddy.viewmodel.SearchFoodViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeSearch(
    modifier: Modifier = Modifier,
    viewModel: SearchFoodViewModel = SearchFoodViewModel(),
    onMealClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Search Bar
        SearchBar(
            query = uiState.searchQuery,
            onQueryChange = viewModel::onSearchQueryChange,
            onSearch = { },
            active = false,
            onActiveChange = { },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            placeholder = { Text("Search meals...") }
        ) { }

        Spacer(modifier = Modifier.height(8.dp))

        // Filters Section
        FiltersSection(
            selectedCategory = uiState.selectedCategory,
            selectedArea = uiState.selectedArea,
            categories = uiState.categories,
            areas = uiState.areas,
            onCategorySelected = viewModel::onCategorySelected,
            onAreaSelected = viewModel::onAreaSelected,
            onClearFilters = {
                viewModel.onCategorySelected(null)
                viewModel.onAreaSelected(null)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Content Section
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                uiState.error != null -> {
                    ErrorMessage(
                        message = uiState.error,
                        onRetry = {
                            if (uiState.searchQuery.isNotEmpty()) {
                                viewModel.onSearchQueryChange(uiState.searchQuery)
                            }
                        },
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                uiState.meals.isEmpty() -> {
                    EmptyState(
                        message = if (uiState.searchQuery.isEmpty() && uiState.selectedCategory == null && uiState.selectedArea == null)
                            "Search for your favorite meals!"
                        else
                            "No meals found",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 160.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(
                            items = uiState.meals,
                            key = { it.idMeal }
                        ) { meal ->
                            MealCard(
                                meal = meal,
                                onClick = { onMealClick(meal.idMeal) }
                            )
                        }
                    }
                }
            }
        }
    }
}
