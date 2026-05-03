package com.example.onlinecoffeeapp.screens.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onlinecoffeeapp.R
import com.example.onlinecoffeeapp.screens.ui_components.MyBottomNavBar

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreen() {
    val location = "Iqbal town,Lahore"

    Scaffold(
        bottomBar = { MyBottomNavBar() }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            // Background Brown Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .background(color = Color(0xFF8A5A36))
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Location",
                    color = Color.White,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(height = 4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = location,
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

                Spacer(modifier = Modifier.height(height = 30.dp))

                MySearchBar()

                Spacer(modifier = Modifier.height(height = 40.dp))

                Image(
                    painter = painterResource(id = R.drawable.ban),
                    contentDescription = "Home Banner",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.FillWidth
                )

                Spacer(modifier = Modifier.height(height = 16.dp))

                HomeCatagories()

                Spacer(modifier = Modifier.height(height = 8.dp))

                //Displaying Products
                val products = remember {
                    listOf(
                        Product(id = 1, name = "Espresso", description = "Strong and rich", price = 500, imagesRes = R.drawable.expresso),
                        Product(id = 2, name = "Latte", description = "Smooth and creamy", price = 300, imagesRes = R.drawable.latte),
                        Product(id = 3, name = "Mocha", description = "Strong and smooth", price = 400, imagesRes = R.drawable.mocha),
                        Product(id = 4, name = "Lungo", description = "With chocolate", price = 550, imagesRes = R.drawable.lungo),
                        Product(id = 5, name = "Iris", description = "Velvety smooth", price = 450, imagesRes = R.drawable.iris),
                        Product(id = 6, name = "Cappuccino", description = "Strong and thick", price = 600, imagesRes = R.drawable.cappuccino)
                    )
                }

                ProductsGrid(products = products, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun ProductsGrid(products: List<Product>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(products) { product ->
            ProductDetails(product = product)
        }
    }
}