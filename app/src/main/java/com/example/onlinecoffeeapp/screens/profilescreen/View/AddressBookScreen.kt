package com.example.onlinecoffeeapp.screens.profilescreen.View

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class UserAddress(val label: String, val detail: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressBookScreen(onBackClick: () -> Unit) {
    val brownColor = Color(0xFF8A5A36)
    val lightBgColor = Color(0xFFFFF5EE)

    val addresses = listOf(
        UserAddress("Home", "House #12, Street 4, Iqbal Town, Lahore"),
        UserAddress("Office", "Software Park, 5th Floor, Gulberg, Lahore"),
        UserAddress("Gym", "Plot 45, Block Z, DHA Phase 3, Lahore"),
        UserAddress("University", "C-1, Johar Town, near Emporium Mall, Lahore"),
        UserAddress("Studio", "Sector C, Bahria Town, Lahore"),
        UserAddress("Cafe Hub", "Main Boulevard, Model Town, Lahore")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Address Book", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = brownColor)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Add address action */ },
                containerColor = brownColor,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Address")
            }
        },
        containerColor = lightBgColor
    ) { innerPadding ->
        if (addresses.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("No addresses saved yet", fontSize = 18.sp, color = brownColor)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(addresses) { address ->
                    AddressCard(address, brownColor)
                }
            }
        }
    }
}

@Composable
fun AddressCard(address: UserAddress, brownColor: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = brownColor,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = address.label, fontWeight = FontWeight.Bold, color = brownColor, fontSize = 16.sp)
                Text(text = address.detail, color = Color.Gray, fontSize = 14.sp)
            }
        }
    }
}
