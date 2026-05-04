package com.example.onlinecoffeeapp.screens.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
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
import com.example.onlinecoffeeapp.ui.theme.CreamBeige

@Composable
fun ProductsGrid(
    products: List<Product>,
    topContent: @Composable () -> Unit,
    onProductClick : (Product) -> Unit
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ProductCard(
                    product = rowItems[0],
                    modifier = Modifier.weight(1f),
                    onProductClick = onProductClick
                )
                if (rowItems.size > 1) {
                    ProductCard(
                        product = rowItems[1],
                        modifier = Modifier.weight(1f),
                        onProductClick = onProductClick
                    )
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun ProductCard(
    product: Product,
    modifier: Modifier = Modifier,
    onProductClick: (Product) -> Unit
) {
    val brownColor = Color(0xFF8A5A36)
    val cardColor = Color(0xFFF8F8F8) // <-- SCREENSHOT WALA OFF-WHITE
    var isFavourite by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .shadow(1.dp, RoundedCornerShape(16.dp)) // <-- Halka shadow
            .clip(RoundedCornerShape(16.dp))
            .background(color = cardColor) // <-- Off-white container
            .clickable { onProductClick(product) }
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            // <-- IMAGE KA EXTRA BOX AUR BACKGROUND HATA DIYA
            Image(
                painter = painterResource(id = product.imagesRes),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop // <-- Crop wapis kar diya
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold, // <-- Bold
                color = Color.Black // <-- Black
            )

            Text(
                text = product.description,
                fontSize = 11.sp,
                color = Color.Gray,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Rs.${product.price}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = brownColor
            )
        }

        // <-- HEART ICON AB DECENT HAI - BOX HATA DIYA
        Icon(
            imageVector = if (isFavourite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = "Favourite",
            tint = if (isFavourite) Color.Red else CreamBeige.copy(alpha = 0.5f),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(14.dp)
                .size(20.dp)
                .clickable { isFavourite =!isFavourite }
        )
    }
}