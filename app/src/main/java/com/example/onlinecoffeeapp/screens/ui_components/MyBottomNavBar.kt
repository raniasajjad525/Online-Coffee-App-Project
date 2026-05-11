package com.example.onlinecoffeeapp.screens.ui_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onlinecoffeeapp.R
import com.example.onlinecoffeeapp.ui.theme.DarkBrown

@Composable
fun MyBottomNavBar(
    selectedItem: String = "Home",
    onHomeClick: () -> Unit = {},
    onCartClick: () -> Unit = {},
    onFavoritesClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    val navItems = listOf(
        NavItem(title = "Home", icon = R.drawable.home),
        NavItem(title = "Cart", icon = R.drawable.cart),
        NavItem(title = "Favourites", icon = R.drawable.heart),
        NavItem(title = "Profile", icon = R.drawable.profile)
    )

    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .height(95.dp) // Fixed height to prevent cutting off
            .navigationBarsPadding(), // Handle gesture navigation
        tonalElevation = 8.dp
    ) {
        navItems.forEach { item ->
            val isSelected = selectedItem == item.title
            // Making icons as visible as the home icon
            val contentColor = if (isSelected) DarkBrown else DarkBrown.copy(alpha = 0.85f)

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    when (item.title) {
                        "Home" -> onHomeClick()
                        "Cart" -> onCartClick()
                        "Favourites" -> onFavoritesClick()
                        "Profile" -> onProfileClick()
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                        modifier = Modifier.size(28.dp), // Increased size
                        tint = contentColor
                    )
                },
                label = { 
                    Text(
                        text = item.title,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = contentColor
                    ) 
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent, // Unique look
                    selectedIconColor = DarkBrown,
                    unselectedIconColor = DarkBrown.copy(alpha = 0.85f),
                    selectedTextColor = DarkBrown,
                    unselectedTextColor = DarkBrown.copy(alpha = 0.85f)
                )
            )
        }
    }
}

data class NavItem(
    val title: String,
    val icon: Int
)
