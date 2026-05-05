package com.example.onlinecoffeeapp.screens.detailespresso

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onlinecoffeeapp.model.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    product: Product,
    onBackClick: () -> Unit,
    onAddToCart: (Product, String) -> Unit
) {
    var selectedSize by remember { mutableStateOf("M") }
    val brownColor = Color(0xFF8A5A36)
    val lightBgColor = Color(0xFFFFF5EE)

    Scaffold(
        containerColor = lightBgColor
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Top Image Section with Back Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
                    .background(brownColor)
                    .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopStart)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }

                Image(
                    painter = painterResource(id = product.imagesRes),
                    contentDescription = product.name,
                    modifier = Modifier
                        .size(200.dp)
                        .align(Alignment.Center),
                    contentScale = ContentScale.Fit
                )
            }

            // Details Section
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                Text(
                    text = product.name,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = brownColor
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Ice/Hot",
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Description:",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = brownColor
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = product.description,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Size",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = brownColor
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Size Selector
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    listOf("S", "M", "L").forEach { size ->
                        SizeButton(
                            text = size,
                            isSelected = selectedSize == size,
                            onClick = { selectedSize = size },
                            brownColor = brownColor
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Price and Add to Cart
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Price",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = brownColor
                        )
                        Text(
                            text = "Rs.${product.price}",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = brownColor
                        )
                    }

                    Button(
                        onClick = { onAddToCart(product, selectedSize) },
                        modifier = Modifier
                            .height(56.dp)
                            .shadow(8.dp, RoundedCornerShape(16.dp)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = brownColor
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = "Add to cart",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 24.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SizeButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    brownColor: Color
) {
    Box(
        modifier = Modifier
            .shadow(
                elevation = if (isSelected) 8.dp else 4.dp,
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) brownColor else Color.White)
            .clickable { onClick() }
            .padding(horizontal = 24.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (isSelected) Color.White else brownColor
        )
    }
}