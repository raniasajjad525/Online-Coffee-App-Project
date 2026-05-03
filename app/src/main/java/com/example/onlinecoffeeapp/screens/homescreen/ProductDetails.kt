package com.example.onlinecoffeeapp.screens.homescreen

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.onlinecoffeeapp.R
import com.example.onlinecoffeeapp.ui.theme.CreamBeige

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Int,
    val imagesRes: Int
)

@Composable
fun ProductDetails(
    product: Product,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(size = 16.dp),
        colors = cardColors(containerColor = LightGray.copy(alpha = 0.2f))
    ) {
        // ← YE MAIN BOX ADD KIYA - Iske andar heart rahega
        Box {
            Column(
                modifier = Modifier.padding(all = 12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                ) {
                    Image(
                        painter = painterResource(id = product.imagesRes),
                        contentDescription = product.name,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                    // ← Heart yahan se hata diya
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = product.name,
                    style = typography.titleMedium.copy(
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                )

                Text(
                    text = product.description,
                    style = typography.bodySmall.copy(
                        color = Color.Gray
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Rs. ${product.price}",
                        style = typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF8A5A36)
                        )
                    )

                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(
                                color = Color(0xFF8A5A36),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable {
                                // Add to cart logic
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add to cart",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            // ← HEART AB YAHAN HAI - CARD KE BILKUL CORNER ME
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 6.dp, end = 6.dp) // Card ke corner me chipka hua
                    .size(28.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(size = 8.dp)
                    )
                    .clickable {
                        // Favorite logic
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.heart),
                    contentDescription = "Add to Favourites",
                    tint = Color(0xFF8A5A36),
                    modifier = Modifier.size(size = 18.dp)
                )
            }
        }
    }
}