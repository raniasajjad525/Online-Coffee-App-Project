package com.example.onlinecoffeeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlinecoffeeapp.R
import com.example.onlinecoffeeapp.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class CartItem(
    val product: Product,
    var quantity: Int = 1,
    val size: String = "Medium"
)

data class Order(
    val id: String,
    val date: String,
    val amount: String,
    val status: String,
    val items: List<CartItem>
)

class CoffeeViewModel : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _currentLocation = MutableStateFlow("Iqbal Town, Lahore")
    val currentLocation: StateFlow<String> = _currentLocation.asStateFlow()

    val availableLocations = listOf(
        "Iqbal Town, Lahore",
        "Gulberg, Lahore",
        "DHA Phase 5, Lahore",
        "Johar Town, Lahore",
        "Model Town, Lahore",
        "Bahria Town, Lahore"
    )

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    private val _favoriteIds = MutableStateFlow<Set<Int>>(emptySet())
    val favoriteIds: StateFlow<Set<Int>> = _favoriteIds.asStateFlow()

    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders.asStateFlow()

    val filteredProducts: StateFlow<List<Product>> = combine(_products, _selectedCategory, _searchQuery) { products, category, query ->
        products.filter { product ->
            (category == "All" || product.category == category) &&
            (query.isEmpty() || product.name.contains(query, ignoreCase = true))
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        loadProducts()
    }

    private fun loadProducts() {
        _products.value = listOf(
            Product(1, "Espresso", "Strong and rich", 500.0, R.drawable.espresso, "Espresso"),
            Product(2, "Latte", "Smooth and creamy", 300.0, R.drawable.latte, "Latte"),
            Product(3, "Mocha", "Strong and smooth", 400.0, R.drawable.mocha, "Mocha"),
            Product(4, "Lungo", "With chocolate", 550.0, R.drawable.lungo, "Lungo"),
            Product(5, "Iris", "Velvety smooth", 450.0, R.drawable.iris, "Iris"),
            Product(6, "Cappuccino", "Strong and thick", 600.0, R.drawable.cappuccino, "Cappuccino")
        )
    }

    fun setCategory(category: String) {
        _selectedCategory.value = category
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setLocation(location: String) {
        _currentLocation.value = location
    }

    fun toggleFavorite(productId: Int) {
        _favoriteIds.update { current ->
            if (current.contains(productId)) current - productId else current + productId
        }
    }

    fun addToCart(product: Product, size: String) {
        _cartItems.update { currentCart ->
            val existingItem = currentCart.find { it.product.id == product.id && it.size == size }
            if (existingItem != null) {
                currentCart.map {
                    if (it.product.id == product.id && it.size == size) it.copy(quantity = it.quantity + 1) else it
                }
            } else {
                currentCart + CartItem(product, 1, size)
            }
        }
    }

    fun removeFromCart(cartItem: CartItem) {
        _cartItems.update { currentCart ->
            currentCart.filter { it != cartItem }
        }
    }

    fun updateQuantity(cartItem: CartItem, increase: Boolean) {
        _cartItems.update { currentCart ->
            currentCart.map {
                if (it == cartItem) {
                    val newQuantity = if (increase) it.quantity + 1 else (it.quantity - 1).coerceAtLeast(1)
                    it.copy(quantity = newQuantity)
                } else it
            }
        }
    }

    fun checkout(): Boolean {
        if (_cartItems.value.isEmpty()) return false

        val totalPrice = _cartItems.value.sumOf { it.product.price * it.quantity }
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val newOrder = Order(
            id = "#${(1000..9999).random()}",
            date = dateFormat.format(Date()),
            amount = "Rs. $totalPrice",
            status = "In Progress",
            items = _cartItems.value
        )

        _orders.update { listOf(newOrder) + it }
        _cartItems.value = emptyList()
        return true
    }
}
