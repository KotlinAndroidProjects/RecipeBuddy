package com.jaennova.recipebuddy.ui.screens.componets.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jaennova.recipebuddy.data.model.Meal
import com.jaennova.recipebuddy.ui.viewmodel.SearchFoodViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchFoodScreen(
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

@Composable
fun FiltersSection(
    selectedCategory: String?,
    selectedArea: String?,
    categories: List<String>,
    areas: List<String>,
    onCategorySelected: (String?) -> Unit,
    onAreaSelected: (String?) -> Unit,
    onClearFilters: () -> Unit
) {
    var showCategoryDropdown by remember { mutableStateOf(false) }
    var showAreaDropdown by remember { mutableStateOf(false) }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Category Filter
            Box(modifier = Modifier.weight(1f)) {
                FilterChip(
                    selected = selectedCategory != null,
                    onClick = { showCategoryDropdown = true },
                    label = {
                        Text(
                            selectedCategory ?: "Category",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    leadingIcon = { Icon(Icons.Default.FilterList, "Category filter") },
                    modifier = Modifier.fillMaxWidth()
                )
                DropdownMenu(
                    expanded = showCategoryDropdown,
                    onDismissRequest = { showCategoryDropdown = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("All Categories") },
                        onClick = {
                            onCategorySelected(null)
                            showCategoryDropdown = false
                        }
                    )
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category) },
                            onClick = {
                                onCategorySelected(category)
                                showCategoryDropdown = false
                            }
                        )
                    }
                }
            }

            // Area Filter
            Box(modifier = Modifier.weight(1f)) {
                FilterChip(
                    selected = selectedArea != null,
                    onClick = { showAreaDropdown = true },
                    label = {
                        Text(
                            selectedArea ?: "Area",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    leadingIcon = { Icon(Icons.Default.FilterList, "Area filter") },
                    modifier = Modifier.fillMaxWidth()
                )
                DropdownMenu(
                    expanded = showAreaDropdown,
                    onDismissRequest = { showAreaDropdown = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("All Areas") },
                        onClick = {
                            onAreaSelected(null)
                            showAreaDropdown = false
                        }
                    )
                    areas.forEach { area ->
                        DropdownMenuItem(
                            text = { Text(area) },
                            onClick = {
                                onAreaSelected(area)
                                showAreaDropdown = false
                            }
                        )
                    }
                }
            }
        }

        // Show clear filters button if any filter is selected
        if (selectedCategory != null || selectedArea != null) {
            TextButton(
                onClick = onClearFilters,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Clear Filters")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealCard(
    meal: Meal,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Box {
            // Image
            AsyncImage(
                model = meal.strMealThumb,
                contentDescription = meal.strMeal,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                contentScale = ContentScale.Crop
            )

            // Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.height(140.dp))

                Text(
                    text = meal.strMeal ?: "",
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    meal.strCategory?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                    meal.strArea?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorMessage(
    message: String?,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message ?: "An error occurred",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}

@Composable
fun EmptyState(
    message: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .padding(bottom = 16.dp),
            tint = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}