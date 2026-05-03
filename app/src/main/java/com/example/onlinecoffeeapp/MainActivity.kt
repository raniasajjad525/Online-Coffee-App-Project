package com.example.onlinecoffeeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.onlinecoffeeapp.screens.homescreen.HomeScreen
import com.example.onlinecoffeeapp.ui.theme.OnlineCoffeeAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()  // This is valid since we imported androidx.activity.enableEdgeToEdge
        setContent {
            OnlineCoffeeAppTheme {
                HomeScreen()
            }
        }
    }
}
