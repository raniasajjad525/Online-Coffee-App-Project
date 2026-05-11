package com.example.onlinecoffeeapp.screens.profilescreen.View

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onlinecoffeeapp.viewmodel.CoffeeViewModel
import com.example.onlinecoffeeapp.viewmodel.Order

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyOrdersScreen(
    coffeeViewModel: CoffeeViewModel,
    onBackClick: () -> Unit
) {
    val orders by coffeeViewModel.orders.collectAsState()
    val brownColor = Color(0xFF8A5A36)
    val lightBgColor = Color(0xFFFFF5EE)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Orders", color = Color.White) },
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
        if (orders.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                Text("No orders placed yet", fontSize = 18.sp, color = brownColor)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(orders) { order ->
                    OrderCard(order, brownColor)
                }
            }
        }
    }
}

@Composable
fun OrderCard(order: Order, brownColor: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Order ${order.id}", fontWeight = FontWeight.Bold, color = brownColor)
                Text(text = order.status, color = if (order.status == "Delivered") Color(0xFF4CAF50) else Color(0xFFFFA000), fontWeight = FontWeight.SemiBold)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = order.date, fontSize = 14.sp, color = Color.Gray)
                Text(text = order.amount, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = brownColor)
            }
            
            if (order.items.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = order.items.joinToString { "${it.product.name} (x${it.quantity})" },
                    fontSize = 12.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}
