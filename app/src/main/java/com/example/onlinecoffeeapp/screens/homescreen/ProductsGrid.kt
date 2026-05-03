package com.example.onlinecoffeeapp.screens.homescreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MovableContent
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


// @Preview
@Composable
fun ProductsGrid(
    products: List<Product>,
    topContent: @Composable () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 8.dp)
    ) {
        item{
            topContent()
        }
        items(items = products.chunked(size = 2)) { rowItems ->
            Row {
                ProductDetails(
                    product = rowItems[0],
                    modifier = Modifier.weight(1f)
                )
                if (rowItems.size > 1) {
                    ProductDetails(
                        product = rowItems[1],
                        modifier = Modifier.weight(1f)
                    )
                } else {
                    // Empty spacer to maintain alignment for the last odd item
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}