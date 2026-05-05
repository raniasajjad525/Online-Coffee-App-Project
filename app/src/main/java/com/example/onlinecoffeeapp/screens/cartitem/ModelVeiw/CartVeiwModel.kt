package com.example.onlinecoffeeapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.onlinecoffeeapp.data.CartItem
import com.example.onlinecoffeeapp.model.Product

class CartViewModel : ViewModel() {
    private val _cartItems = mutableStateListOf<CartItem>()
    val cartItems: List<CartItem> = _cartItems

    fun addToCart(product: Product, size: String, price: Int) {
        val existingItem = _cartItems.find { it.product.id == product.id && it.size == size }
        if (existingItem!= null) {
            val index = _cartItems.indexOf(existingItem)
            _cartItems[index] = existingItem.copy(quantity = existingItem.quantity + 1)
        } else {
            _cartItems.add(CartItem(product, size, price))
        }
    }

    fun getTotalPrice(): Int = _cartItems.sumOf { it.price * it.quantity }
}

