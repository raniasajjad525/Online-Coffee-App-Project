package com.example.onlinecoffeeapp.screens.homescreen.Veiw

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onlinecoffeeapp.model.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleProductDetailScreen(
    product: Product,
    onBackClick: () -> Unit
) {
    val brownColor = Color(0xFF8A5A36)
    val lightBgColor = Color(0xFFFFF5EE)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = product.name, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = brownColor)
            )
        },
        containerColor = lightBgColor
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = product.imagesRes),
                contentDescription = product.name,
                modifier = Modifier
                    .size(250.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = product.name,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = brownColor
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = product.description,
                fontSize = 16.sp,
                color = Color.DarkGray,
                lineHeight = 24.sp
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Price: Rs.${product.price}",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = brownColor
            )
        }
    }
}
