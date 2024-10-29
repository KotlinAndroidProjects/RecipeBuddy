package com.jaennova.recipebuddy.ui.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

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