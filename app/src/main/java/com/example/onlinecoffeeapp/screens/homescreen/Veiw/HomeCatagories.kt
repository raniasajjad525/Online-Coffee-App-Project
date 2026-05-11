package com.example.onlinecoffeeapp.screens.homescreen.Veiw

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeCatagories(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    val categories = listOf("All", "Latte", "Mocha", "Espresso", "Iris", "Lungo", "Cappuccino")

    LazyRow(
        modifier = Modifier.padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = categories) { category ->
            CatagoriesList(
                text = category,
                isSelected = category == selectedCategory,
                onSelected = { onCategorySelected(category) }
            )
        }
    }
}
