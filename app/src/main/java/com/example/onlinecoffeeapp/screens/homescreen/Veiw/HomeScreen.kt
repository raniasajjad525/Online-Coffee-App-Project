package com.example.onlinecoffeeapp.screens.homescreen.Veiw

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
    coffeeViewModel: CoffeeViewModel = viewModel()
) {
    val products by coffeeViewModel.products.collectAsState()
    val location = "Iqbal town, Lahore"

    Scaffold(
        bottomBar = { MyBottomNavBar() }
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
                products = products,
                topContent = {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Location",
                            color = Color.White,
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(4.dp))

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

                        Spacer(modifier = Modifier.height(30.dp))

                        MySearchBar()

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

                        HomeCatagories()

                        Spacer(modifier = Modifier.height(8.dp))
                    }
                },
                onProductClick = onProductClick
            )
        }
    }
}
