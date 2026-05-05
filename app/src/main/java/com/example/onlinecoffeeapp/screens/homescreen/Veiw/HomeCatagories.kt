package com.example.onlinecoffeeapp.screens.homescreen.Veiw

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeCatagories(){
    val categories = listOf("All", "Latte", "Mocha", "Espresso", "Iris", "Lungo", "Cappuccino")

    var selectedCategory by remember { mutableStateOf (categories.first()) }

    LazyRow(
        modifier = Modifier.padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items(items= categories){ category ->
            CatagoriesList(
                text = category,
                isSelected = category == selectedCategory,
                onSelected = { selectedCategory = category}
            )
        }
    }
}
