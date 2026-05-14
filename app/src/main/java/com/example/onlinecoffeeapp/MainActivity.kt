package com.example.onlinecoffeeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.onlinecoffeeapp.screens.navigationsetup.AppNavigation
import com.example.onlinecoffeeapp.ui.theme.OnlineCoffeeAppTheme
import com.example.onlinecoffeeapp.viewmodel.CoffeeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val coffeeViewModel: CoffeeViewModel = viewModel()
            val isDarkMode by coffeeViewModel.isDarkMode.collectAsState()
            
            OnlineCoffeeAppTheme(darkTheme = isDarkMode) {
                AppNavigation(coffeeViewModel = coffeeViewModel)
            }
        }
    }
}
