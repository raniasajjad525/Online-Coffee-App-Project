package com.example.onlinecoffeeapp.screens.homescreen.Veiw

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
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun ProductsGrid(
    products: List<Product>,
    favoriteIds: Set<Int>,
    onFavoriteClick: (Int) -> Unit,
    topContent: @Composable () -> Unit,
    onProductClick: (Product) -> Unit,
    isListView: Boolean = false
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 8.dp)
    ) {
        item {
            topContent()
        }
        
        if (isListView) {
            items(products) { product ->
                ProductListItem(
                    product = product,
                    isFavorite = favoriteIds.contains(product.id),
                    onFavoriteClick = { onFavoriteClick(product.id) },
                    onProductClick = onProductClick
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        } else {
            items(items = products.chunked(size = 2)) { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ProductCard(
                        product = rowItems[0],
                        isFavorite = favoriteIds.contains(rowItems[0].id),
                        onFavoriteClick = { onFavoriteClick(rowItems[0].id) },
                        modifier = Modifier.weight(1f),
                        onProductClick = onProductClick
                    )
                    if (rowItems.size > 1) {
                        ProductCard(
                            product = rowItems[1],
                            isFavorite = favoriteIds.contains(rowItems[1].id),
                            onFavoriteClick = { onFavoriteClick(rowItems[1].id) },
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
}

@Composable
fun ProductListItem(
    product: Product,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onProductClick: (Product) -> Unit
) {
    val brownColor = Color(0xFF8A5A36)
    val cardColor = Color(0xFFF8F8F8)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(1.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(color = cardColor)
            .clickable { onProductClick(product) }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = product.imagesRes),
            contentDescription = product.name,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = product.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = product.description,
                fontSize = 12.sp,
                color = Color.Gray,
                maxLines = 1
            )
            Text(
                text = "Rs.${product.price}",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = brownColor
            )
        }

        IconButton(onClick = onFavoriteClick) {
            Icon(
                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "Favourite",
                tint = if (isFavorite) brownColor else Color.Gray.copy(alpha = 0.5f),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun ProductCard(
    product: Product,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier,
    onProductClick: (Product) -> Unit
) {
    val brownColor = Color(0xFF8A5A36)
    val cardColor = Color(0xFFF8F8F8)

    Box(
        modifier = modifier
            .shadow(1.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(color = cardColor)
            .clickable { onProductClick(product) }
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Image(
                painter = painterResource(id = product.imagesRes),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
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

        Icon(
            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = "Favourite",
            tint = if (isFavorite) brownColor else Color.Gray.copy(alpha = 0.5f),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(14.dp)
                .size(20.dp)
                .clickable { onFavoriteClick() }
        )
    }
}
