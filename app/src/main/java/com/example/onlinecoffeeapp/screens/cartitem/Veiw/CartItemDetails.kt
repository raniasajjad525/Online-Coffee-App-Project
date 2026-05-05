package com.example.onlinecoffeeapp.data

import com.example.onlinecoffeeapp.screens.homescreen.Product

data class CartItem(
    val product: Product,
    val size: String,
    val price: Int,
    val quantity: Int = 1
)

