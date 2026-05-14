package com.example.onlinecoffeeapp.screens.cartitem.Veiw

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onlinecoffeeapp.viewmodel.CartItem
import com.example.onlinecoffeeapp.viewmodel.CoffeeViewModel
import com.example.onlinecoffeeapp.viewmodel.Order

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    coffeeViewModel: CoffeeViewModel,
    onBackClick: () -> Unit,
    onCheckoutClick: () -> Unit
) {
    val cartItems by coffeeViewModel.cartItems.collectAsState()
    val orders by coffeeViewModel.orders.collectAsState()
    val context = LocalContext.current
    
    val brownColor = Color(0xFF8A5A36)
    val lightBgColor = Color(0xFFFFF5EE)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Cart & Orders", color = Color.White) },
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
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Current Cart Section
            if (cartItems.isNotEmpty()) {
                item {
                    Text(
                        "Items in Cart",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = brownColor,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                items(cartItems) { item ->
                    CartItemRow(
                        item = item,
                        onIncrease = { coffeeViewModel.updateQuantity(item, true) },
                        onDecrease = { coffeeViewModel.updateQuantity(item, false) },
                        onRemove = { coffeeViewModel.removeFromCart(item) },
                        brownColor = brownColor
                    )
                }
                item {
                    val totalPrice = cartItems.sumOf { it.product.price * it.quantity }
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Total", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = brownColor)
                                Text("Rs. $totalPrice", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = brownColor)
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = onCheckoutClick,
                                modifier = Modifier.fillMaxWidth().height(50.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = brownColor),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Review Order", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            }
                        }
                    }
                }
            } else if (orders.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillParentMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Your cart is empty", fontSize = 18.sp, color = brownColor)
                    }
                }
            }

            // Recent Orders Section
            if (orders.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Order History",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = brownColor,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                items(orders) { order ->
                    OrderHistoryCard(
                        order = order,
                        brownColor = brownColor,
                        onDelete = {
                            coffeeViewModel.cancelOrder(
                                orderId = order.id,
                                onSuccess = {
                                    Toast.makeText(context, "Order record deleted", Toast.LENGTH_SHORT).show()
                                },
                                onFailure = { error ->
                                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CartItemRow(
    item: CartItem,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onRemove: () -> Unit,
    brownColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = item.product.imagesRes),
                contentDescription = item.product.name,
                modifier = Modifier.size(70.dp).clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = item.product.name, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = brownColor)
                Text(text = "Size: ${item.size}", fontSize = 12.sp, color = Color.Gray)
                Text(text = "Rs. ${item.product.price}", fontWeight = FontWeight.SemiBold, color = brownColor)
            }
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onDecrease) {
                    Icon(Icons.Default.Remove, contentDescription = "Decrease", tint = brownColor)
                }
                Text(text = item.quantity.toString(), fontWeight = FontWeight.Bold, color = brownColor)
                IconButton(onClick = onIncrease) {
                    Icon(Icons.Default.Add, contentDescription = "Increase", tint = brownColor)
                }
                IconButton(onClick = onRemove) {
                    Icon(Icons.Default.Delete, contentDescription = "Remove", tint = Color.Red)
                }
            }
        }
    }
}

@Composable
fun OrderHistoryCard(order: Order, brownColor: Color, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Order ID: ${order.id.takeLast(6)}", fontWeight = FontWeight.Bold, color = brownColor)
                    Text(order.date, fontSize = 12.sp, color = Color.Gray)
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Order", tint = Color.Red.copy(alpha = 0.7f))
                }
            }
            
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), thickness = 0.5.dp, color = Color.LightGray)
            
            order.items.forEach { item ->
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("${item.name} (x${item.quantity})", fontSize = 14.sp, color = brownColor)
                    Text("Rs. ${item.price * item.quantity}", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Total Paid", fontWeight = FontWeight.Bold, color = brownColor)
                Text("Rs. ${order.amount}", fontWeight = FontWeight.Bold, color = brownColor)
            }
            Text("Status: ${order.status}", fontSize = 12.sp, color = Color(0xFF4CAF50), fontWeight = FontWeight.SemiBold)
        }
    }
}
