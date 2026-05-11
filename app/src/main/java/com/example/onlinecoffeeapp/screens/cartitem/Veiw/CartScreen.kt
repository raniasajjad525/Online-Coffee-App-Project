package com.example.onlinecoffeeapp.screens.cartitem.Veiw

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onlinecoffeeapp.viewmodel.CartItem
import com.example.onlinecoffeeapp.viewmodel.CoffeeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    coffeeViewModel: CoffeeViewModel,
    onBackClick: () -> Unit
) {
    val cartItems by coffeeViewModel.cartItems.collectAsState()
    var showOrderDialog by remember { mutableStateOf(false) }
    
    val brownColor = Color(0xFF8A5A36)
    val lightBgColor = Color(0xFFFFF5EE)


    // Order Success Dialog
    if (showOrderDialog) {
        AlertDialog(
            onDismissRequest = { showOrderDialog = false },
            confirmButton = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = { 
                            showOrderDialog = false
                            onBackClick() // Order place hone ke baad back chale jao
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = brownColor),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Done", fontWeight = FontWeight.Bold)
                    }
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(64.dp)
                )
            },
            title = {
                Text(
                    text = "Order Placed Successfully!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Your coffee has been ordered successfully.",
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Please wait, it will be ready in a few minutes!",
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
            },
            shape = RoundedCornerShape(24.dp),
            containerColor = Color.White
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Cart", color = Color.White) },
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
        if (cartItems.isEmpty() && !showOrderDialog) {
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                Text("Your cart is empty", fontSize = 18.sp, color = brownColor)
            }
        } else {
            Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(cartItems) { item ->
                        CartItemRow(
                            item = item,
                            onIncrease = { coffeeViewModel.updateQuantity(item, true) },
                            onDecrease = { coffeeViewModel.updateQuantity(item, false) },
                            onRemove = { coffeeViewModel.removeFromCart(item) },
                            brownColor = brownColor
                        )
                    }
                }

                if (cartItems.isNotEmpty()) {
                    val totalPrice = cartItems.sumOf { it.product.price * it.quantity }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Column(modifier = Modifier.padding(24.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Total", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = brownColor)
                                Text("Rs. $totalPrice", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = brownColor)
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = { 
                                    if (coffeeViewModel.checkout()) {
                                        showOrderDialog = true
                                    }
                                },
                                modifier = Modifier.fillMaxWidth().height(56.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = brownColor),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Text("Check Out", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            }
                        }
                    }
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
