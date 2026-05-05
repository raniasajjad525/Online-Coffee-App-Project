package com.example.onlinecoffeeapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.onlinecoffeeapp.R
import com.example.onlinecoffeeapp.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CoffeeViewModel : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    init {
        loadProducts()
    }

    private fun loadProducts() {
        _products.value = listOf(
            Product(1, "Espresso", "Strong and rich", 500.0, R.drawable.espresso),
            Product(2, "Latte", "Smooth and creamy", 300.0, R.drawable.latte),
            Product(3, "Mocha", "Strong and smooth", 400.0, R.drawable.mocha),
            Product(4, "Lungo", "With chocolate", 550.0, R.drawable.lungo),
            Product(5, "Iris", "Velvety smooth", 450.0, R.drawable.iris),
            Product(6, "Cappuccino", "Strong and thick", 600.0, R.drawable.cappuccino)
        )
    }
}
