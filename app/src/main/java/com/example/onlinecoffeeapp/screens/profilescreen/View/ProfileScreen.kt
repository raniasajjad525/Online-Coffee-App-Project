package com.example.onlinecoffeeapp.screens.profilescreen.View

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onlinecoffeeapp.R
import com.example.onlinecoffeeapp.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    authViewModel: AuthViewModel,
    onBackClick: () -> Unit,
    onLogoutSuccess: () -> Unit,
    onMyOrdersClick: () -> Unit,
    onAddressBookClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    val currentUser = authViewModel.currentUser
    val brownColor = Color(0xFF8A5A36)
    val goldColor = Color(0xFFFFD700)
    val lightBgColor = Color(0xFFFFF5EE)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Profile", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        authViewModel.logout()
                        onLogoutSuccess()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Logout",
                            tint = Color.White
                        )
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
            Spacer(modifier = Modifier.height(20.dp))

            // Profile Image Placeholder
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(brownColor.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile Picture",
                    modifier = Modifier.size(80.dp),
                    tint = brownColor
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Jee Aayan Nu! (Welcome)",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = brownColor
            )
            
            Text(
                text = "Lahore's Coffee Connoisseur",
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic,
                color = goldColor,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(12.dp))

            // User ID / Email
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "User Email / ID",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = currentUser?.email ?: "Guest User",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = brownColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Profile Options
            ProfileOptionItem(title = "My Orders", brownColor = brownColor, onClick = onMyOrdersClick)
            ProfileOptionItem(title = "Lahore Address Book", brownColor = brownColor, onClick = onAddressBookClick)
            ProfileOptionItem(title = "App Settings", brownColor = brownColor, onClick = onSettingsClick)
            
            Spacer(modifier = Modifier.weight(1f))
            
            Text(
                text = "Made with ❤️ in Lahore",
                fontSize = 12.sp,
                color = Color.Gray.copy(alpha = 0.6f),
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}

@Composable
fun ProfileOptionItem(title: String, brownColor: Color, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, fontSize = 16.sp, color = brownColor, fontWeight = FontWeight.Medium)
            Icon(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = brownColor.copy(alpha = 0.5f)
            )
        }
    }
}
