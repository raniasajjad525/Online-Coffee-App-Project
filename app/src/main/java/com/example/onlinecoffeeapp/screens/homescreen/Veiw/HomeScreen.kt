package com.example.onlinecoffeeapp.screens.homescreen.Veiw

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.onlinecoffeeapp.R
import com.example.onlinecoffeeapp.model.Product
import com.example.onlinecoffeeapp.screens.ui_components.MyBottomNavBar
import com.example.onlinecoffeeapp.viewmodel.CoffeeViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    onProductClick: (Product) -> Unit = {},
    onCartClick: () -> Unit = {},
    onFavoritesClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    coffeeViewModel: CoffeeViewModel = viewModel()
) {
    val allProducts by coffeeViewModel.products.collectAsState()
    val filteredProducts by coffeeViewModel.filteredProducts.collectAsState()
    val selectedCategory by coffeeViewModel.selectedCategory.collectAsState()
    val searchQuery by coffeeViewModel.searchQuery.collectAsState()
    val favoriteIds by coffeeViewModel.favoriteIds.collectAsState()
    val currentLocation by coffeeViewModel.currentLocation.collectAsState()
    
    var showLocationMenu by remember { mutableStateOf(false) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val categories = remember(allProducts) {
        listOf("All") + allProducts.map { it.category }.distinct()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color.White,
                modifier = Modifier.width(300.dp)
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Coffee Menu",
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF8A5A36)
                )
                
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
                        Text(
                            text = "Categories",
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                            fontSize = 14.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    items(categories) { category ->
                        Text(
                            text = category,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    scope.launch { drawerState.close() }
                                    coffeeViewModel.setSearchQuery("")
                                    coffeeViewModel.setCategory(category)
                                }
                                .padding(horizontal = 24.dp, vertical = 12.dp),
                            fontSize = 17.sp,
                            color = if (selectedCategory == category) Color(0xFF8A5A36) else Color.Black,
                            fontWeight = if (selectedCategory == category) FontWeight.Bold else FontWeight.Normal
                        )
                    }

                    item {
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                        Text(
                            text = "Specific Coffees",
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                            fontSize = 14.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    items(allProducts) { product ->
                        Text(
                            text = product.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    scope.launch { drawerState.close() }
                                    coffeeViewModel.setCategory("All")
                                    coffeeViewModel.setSearchQuery(product.name)
                                }
                                .padding(horizontal = 24.dp, vertical = 12.dp),
                            fontSize = 17.sp,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    ) {
        Scaffold(
            bottomBar = { 
                MyBottomNavBar(
                    selectedItem = "Home",
                    onHomeClick = { 
                        scope.launch { drawerState.close() }
                        coffeeViewModel.setCategory("All")
                        coffeeViewModel.setSearchQuery("")
                    },
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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                        .background(color = Color(0xFF8A5A36))
                )

                ProductsGrid(
                    products = filteredProducts,
                    favoriteIds = favoriteIds,
                    onFavoriteClick = { productId: Int -> coffeeViewModel.toggleFavorite(productId) },
                    onProductClick = onProductClick,
                    onAddToCartClick = { product ->
                        onProductClick(product) // Open detail screen for selection and adding to cart
                    },
                    topContent = {
                        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "Brewing in Lahore ☕",
                                        color = Color(0xFFFFD700),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontStyle = FontStyle.Italic
                                    )
                                    Box {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.clickable { showLocationMenu = true }
                                        ) {
                                            Text(text = currentLocation, color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                                            Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null, tint = Color.White)
                                        }
                                        DropdownMenu(expanded = showLocationMenu, onDismissRequest = { showLocationMenu = false }) {
                                            coffeeViewModel.availableLocations.forEach { loc ->
                                                DropdownMenuItem(text = { Text(loc) }, onClick = { coffeeViewModel.setLocation(loc); showLocationMenu = false })
                                            }
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(30.dp))
                            MySearchBar(
                                query = searchQuery,
                                onQueryChange = { coffeeViewModel.setSearchQuery(it) },
                                onFilterClick = { scope.launch { drawerState.open() } }
                            )
                            Spacer(modifier = Modifier.height(40.dp))
                            Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp))) {
                                Image(painter = painterResource(id = R.drawable.ban), contentDescription = null, modifier = Modifier.fillMaxWidth(), contentScale = ContentScale.FillWidth)
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            HomeCatagories(selectedCategory = selectedCategory, onCategorySelected = { coffeeViewModel.setCategory(it) })
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                )
            }
        }
    }
}
