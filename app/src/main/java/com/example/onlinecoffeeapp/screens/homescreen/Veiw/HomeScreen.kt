package com.example.onlinecoffeeapp.screens.homescreen.Veiw

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.onlinecoffeeapp.R
import com.example.onlinecoffeeapp.model.Product
import com.example.onlinecoffeeapp.screens.ui_components.MyBottomNavBar
import com.example.onlinecoffeeapp.viewmodel.CoffeeViewModel

@Composable
fun HomeScreen(
    onProductClick: (Product) -> Unit = {},
    onCartClick: () -> Unit = {},
    onFavoritesClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    coffeeViewModel: CoffeeViewModel = viewModel()
) {
    val filteredProducts by coffeeViewModel.filteredProducts.collectAsState()
    val selectedCategory by coffeeViewModel.selectedCategory.collectAsState()
    val searchQuery by coffeeViewModel.searchQuery.collectAsState()
    val favoriteIds by coffeeViewModel.favoriteIds.collectAsState()
    val currentLocation by coffeeViewModel.currentLocation.collectAsState()
    
    var showLocationMenu by remember { mutableStateOf(false) }
    var isListView by remember { mutableStateOf(false) } // State for toggling List/Grid view

    Scaffold(
        bottomBar = { 
            MyBottomNavBar(
                selectedItem = "Home",
                onHomeClick = { /* Already on Home */ },
                onCartClick = onCartClick,
                onFavoritesClick = onFavoritesClick,
                onProfileClick = onProfileClick
            ) 
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Background Brown Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .background(color = Color(0xFF8A5A36))
            )

            ProductsGrid(
                products = filteredProducts,
                favoriteIds = favoriteIds,
                onFavoriteClick = { productId -> coffeeViewModel.toggleFavorite(productId) },
                isListView = isListView, // Passing the toggle state
                topContent = {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Location",
                                    color = Color.White,
                                    fontSize = 14.sp
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Box {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.clickable { showLocationMenu = true }
                                    ) {
                                        Text(
                                            text = currentLocation,
                                            color = Color.White,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 16.sp
                                        )
                                        Icon(
                                            imageVector = Icons.Default.KeyboardArrowDown,
                                            contentDescription = "Change Location",
                                            tint = Color.White
                                        )
                                    }

                                    DropdownMenu(
                                        expanded = showLocationMenu,
                                        onDismissRequest = { showLocationMenu = false },
                                        modifier = Modifier.background(Color.White)
                                    ) {
                                        coffeeViewModel.availableLocations.forEach { location ->
                                            DropdownMenuItem(
                                                text = { Text(location, color = Color(0xFF8A5A36)) },
                                                onClick = {
                                                    coffeeViewModel.setLocation(location)
                                                    showLocationMenu = false
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(30.dp))

                        MySearchBar(
                            query = searchQuery,
                            onQueryChange = { coffeeViewModel.setSearchQuery(it) },
                            onFilterClick = {
                                isListView = !isListView // Toggling List/Grid view on icon click
                            }
                        )

                        Spacer(modifier = Modifier.height(40.dp))

                        Image(
                            painter = painterResource(id = R.drawable.ban),
                            contentDescription = "Home Banner",
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.FillWidth
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        HomeCatagories(
                            selectedCategory = selectedCategory,
                            onCategorySelected = { category -> coffeeViewModel.setCategory(category) }
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                    }
                },
                onProductClick = onProductClick
            )
        }
    }
}
