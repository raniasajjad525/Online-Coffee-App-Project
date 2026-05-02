package com.example.onlinecoffeeapp.screens.ui_components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.onlinecoffeeapp.R
import com.example.onlinecoffeeapp.ui.theme.LightBrown


@Preview
@Composable
fun MyBottomNavBar(){
    //Bottom Nav Items
    val navItems = listOf(
        NavItem(title = "Home" , icon = R.drawable.home),
        NavItem(title = "Cart" , icon = R.drawable.cart),
        NavItem(title = "Favourites" , icon = R.drawable.heart),
        NavItem(title = "Profile" , icon = R.drawable.profile)
    )


    NavigationBar(
        containerColor =  MaterialTheme.colorScheme.surface,
        modifier =  Modifier.height(height = 100.dp)
    ) {

        navItems.forEachIndexed {
                index , item ->
            NavigationBarItem(
                //selected = true,
                //onClick = { },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                        //modifier = Modifier.size(30.dp)
                    )
                },
                label = { Text(text = item.title) },
                modifier = Modifier.size(30.dp),
                onClick = { },
                selected = true,
                alwaysShowLabel =  false,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = LightBrown,
                    selectedTextColor = LightBrown,
                    unselectedIconColor = Color.DarkGray,
                    unselectedTextColor = Color.DarkGray,
                    indicatorColor = LightBrown.copy(alpha = 0.3f)

                )
            )
        }
    }
}
data class NavItem(
    val title : String,
    val icon : Int
)