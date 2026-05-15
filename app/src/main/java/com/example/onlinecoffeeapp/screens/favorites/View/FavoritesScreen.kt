package com.example.onlinecoffeeapp.screens.favorites.View

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onlinecoffeeapp.model.Product
import com.example.onlinecoffeeapp.screens.homescreen.Veiw.ProductCard
import com.example.onlinecoffeeapp.viewmodel.CoffeeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    coffeeViewModel: CoffeeViewModel,
    onBackClick: () -> Unit,
    onProductClick: (Product) -> Unit,
    onCartClick: () -> Unit = {}
) {
    val products by coffeeViewModel.products.collectAsState()
    val favoriteIds by coffeeViewModel.favoriteIds.collectAsState()
    
    val favoriteProducts = products.filter { favoriteIds.contains(it.id) }
    
    val brownColor = Color(0xFF8A5A36)
    val lightBgColor = Color(0xFFFFF5EE)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Favorites", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = brownColor)
            )
        },
        containerColor = lightBgColor
    ) { innerPadding ->
        if (favoriteProducts.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("No favorite coffees yet", fontSize = 18.sp, color = brownColor)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(favoriteProducts.chunked(2)) { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        ProductCard(
                            product = rowItems[0],
                            isFavorite = true,
                            onFavoriteClick = { coffeeViewModel.toggleFavorite(rowItems[0].id) },
                            modifier = Modifier.weight(1f),
                            onProductClick = onProductClick,
                            onAddToCartClick = {
                                onProductClick(rowItems[0])
                            }
                        )
                        if (rowItems.size > 1) {
                            ProductCard(
                                product = rowItems[1],
                                isFavorite = true,
                                onFavoriteClick = { coffeeViewModel.toggleFavorite(rowItems[1].id) },
                                modifier = Modifier.weight(1f),
                                onProductClick = onProductClick,
                                onAddToCartClick = {
                                    onProductClick(rowItems[1])
                                }
                            )
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}
