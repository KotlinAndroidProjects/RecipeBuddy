package com.jaennova.recipebuddy.ui.screens.componets.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaennova.recipebuddy.data.model.Category

@Composable
fun CategorySection(
    categories: List<Category>,
    onCategorySelected: (Category) -> Unit,
    onViewAllClick: () -> Unit = {}
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Categorías",
                style = MaterialTheme.typography.titleLarge
            )
            TextButton(onClick = onViewAllClick) {
                Text(
                    text = "Ver todas",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                CategoryCard(
                    category = category,
                    onClick = { onCategorySelected(category) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScreenPreview() {

    CategorySection(
        categories = listOf(
            Category(
                "1",
                "Beef", "https://www.themealdb.com/images/category/beef.png",
                "Beef"
            ),
            Category(
                "2",
                "Chicken", "https://www.themealdb.com/images/category/chicken.png",
                "Chicken"
            ),
        ),
        onCategorySelected = {},
    ) {}
}
