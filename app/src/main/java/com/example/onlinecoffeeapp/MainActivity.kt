package com.example.onlinecoffeeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.onlinecoffeeapp.navigation.AppNavigation
import com.example.onlinecoffeeapp.ui.theme.OnlineCoffeeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OnlineCoffeeAppTheme {
                AppNavigation()
            }
        }
    }
}
