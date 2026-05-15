package com.example.onlinecoffeeapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlinecoffeeapp.R
import com.example.onlinecoffeeapp.model.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class CartItem(
    val product: Product = Product(),
    val quantity: Int = 1,
    val size: String = "Medium"
)

// Firestore record ke liye simplified class
data class OrderItem(
    val name: String = "",
    val price: Double = 0.0,
    val quantity: Int = 0,
    val size: String = ""
)

data class Order(
    val id: String = "",
    val userId: String = "",
    val userEmail: String = "",
    val date: String = "",
    val amount: Double = 0.0,
    val status: String = "Placed",
    val items: List<OrderItem> = emptyList()
)

class CoffeeViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders.asStateFlow()

    private val _isPlacingOrder = MutableStateFlow(false)
    val isPlacingOrder: StateFlow<Boolean> = _isPlacingOrder.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _favoriteIds = MutableStateFlow<Set<Int>>(emptySet())
    val favoriteIds: StateFlow<Set<Int>> = _favoriteIds.asStateFlow()

    private val _currentLocation = MutableStateFlow("Gulberg, Lahore")
    val currentLocation: StateFlow<String> = _currentLocation.asStateFlow()

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    val availableLocations = listOf(
        "Gulberg, Lahore", "DHA Phase 1-9, Lahore", "Johar Town, Lahore", "Model Town, Lahore",
        "Bahria Town, Lahore", "Iqbal Town, Lahore", "Garden Town, Lahore", "Wapda Town, Lahore",
        "Cavalry Ground, Lahore", "Sammanabad, Lahore", "Shadman, Lahore", "Lahore Cantt",
        "Township, Lahore", "Faisal Town, Lahore", "Sabzazar, Lahore", "Lake City, Lahore",
        "Valencia, Lahore", "Green Town, Lahore", "Architects Society, Lahore", "EME Society, Lahore",
        "State Life Society, Lahore", "Paragon City, Lahore", "Bahria Orchard, Lahore", "Park View City, Lahore",
        "Al-Rehman Garden, Lahore", "Muslim Town, Lahore", "Gulshan-e-Ravi, Lahore", "Ichhra, Lahore",
        "Garhi Shahu, Lahore", "Mughalpura, Lahore", "Mall Road, Lahore", "Anarkali, Lahore"
    )

    val filteredProducts: StateFlow<List<Product>> = combine(_products, _selectedCategory, _searchQuery) { products, category, query ->
        products.filter { product ->
            (category == "All" || product.category == category) &&
            (query.isEmpty() || product.name.contains(query, ignoreCase = true))
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
        db.firestoreSettings = settings
        
        loadProductsFromFirestore()
        fetchOrders()
    }

    private fun loadProductsFromFirestore() {
        db.collection("products")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    loadLocalProducts()
                    return@addSnapshotListener
                }

                if (value != null && !value.isEmpty) {
                    val productList = value.toObjects(Product::class.java)
                    _products.value = productList
                } else {
                    syncLocalProductsToFirestore()
                }
            }
    }

    private fun loadLocalProducts() {
        _products.value = listOf(
            Product(1, "Espresso", "Strong and rich", 500.0, R.drawable.espresso, "", "Espresso"),
            Product(2, "Latte", "Smooth and creamy", 300.0, R.drawable.latte, "", "Latte"),
            Product(3, "Mocha", "Strong and smooth", 400.0, R.drawable.mocha, "", "Mocha"),
            Product(4, "Lungo", "With chocolate", 550.0, R.drawable.lungo, "", "Lungo"),
            Product(5, "Iris", "Velvety smooth", 450.0, R.drawable.iris, "", "Iris"),
            Product(6, "Cappuccino", "Strong and thick", 600.0, R.drawable.cappuccino, "", "Cappuccino")
        )
    }

    fun syncLocalProductsToFirestore() {
        val localProducts = listOf(
            Product(1, "Espresso", "Strong and rich", 500.0, R.drawable.espresso, "", "Espresso"),
            Product(2, "Latte", "Smooth and creamy", 300.0, R.drawable.latte, "", "Latte"),
            Product(3, "Mocha", "Strong and smooth", 400.0, R.drawable.mocha, "", "Mocha"),
            Product(4, "Lungo", "With chocolate", 550.0, R.drawable.lungo, "", "Lungo"),
            Product(5, "Iris", "Velvety smooth", 450.0, R.drawable.iris, "", "Iris"),
            Product(6, "Cappuccino", "Strong and thick", 600.0, R.drawable.cappuccino, "", "Cappuccino")
        )

        viewModelScope.launch {
            for (product in localProducts) {
                db.collection("products").document(product.id.toString()).set(product)
            }
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
        _cartItems.update { current -> current.filter { it != cartItem } }
    }

    fun updateQuantity(cartItem: CartItem, increase: Boolean) {
        _cartItems.update { current ->
            current.map {
                if (it == cartItem) {
                    val newQty = if (increase) it.quantity + 1 else (it.quantity - 1).coerceAtLeast(1)
                    it.copy(quantity = newQty)
                } else it
            }
        }
    }

    fun confirmOrder(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val currentItems = _cartItems.value
        if (currentItems.isEmpty()) {
            onFailure("Cart is empty")
            return
        }

        val user = auth.currentUser
        if (user == null) {
            onFailure("Please login first")
            return
        }

        _isPlacingOrder.value = true

        viewModelScope.launch {
            val totalPrice = currentItems.sumOf { it.product.price * it.quantity }
            val dateFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
            val dateString = dateFormat.format(Date())
            val orderId = db.collection("orders").document().id
            val userEmail = user.email ?: "Unknown User"
            
            val orderItemsList = currentItems.map {
                OrderItem(it.product.name, it.product.price, it.quantity, it.size)
            }

            val orderData = Order(
                id = orderId,
                userId = user.uid,
                userEmail = userEmail,
                date = dateString,
                amount = totalPrice,
                status = "Placed",
                items = orderItemsList
            )

            db.collection("orders").document(orderId).set(orderData)
                .addOnSuccessListener {
                    _orders.update { listOf(orderData) + it }
                    _cartItems.value = emptyList()
                    _isPlacingOrder.value = false
                    onSuccess()
                }
                .addOnFailureListener { e ->
                    _isPlacingOrder.value = false
                    onFailure(e.message ?: "Failed to save order")
                }
        }
    }

    fun cancelOrder(orderId: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        db.collection("orders").document(orderId).delete()
            .addOnSuccessListener {
                _orders.update { current -> current.filter { it.id != orderId } }
                onSuccess()
            }
            .addOnFailureListener { e ->
                onFailure(e.message ?: "Failed to delete order record")
            }
    }

    fun fetchOrders() {
        val user = auth.currentUser ?: return
        db.collection("orders")
            .whereEqualTo("userId", user.uid)
            .get()
            .addOnSuccessListener { result ->
                val ordersList = result.toObjects(Order::class.java)
                _orders.value = ordersList.sortedByDescending { it.date }
            }
    }

    fun setCategory(category: String) { _selectedCategory.value = category }
    fun setSearchQuery(query: String) { _searchQuery.value = query }

    fun toggleFavorite(productId: Int) {
        _favoriteIds.update { current ->
            if (current.contains(productId)) current - productId else current + productId
        }
    }

    fun setLocation(location: String) {
        _currentLocation.value = location
    }

    fun setDarkMode(enabled: Boolean) {
        _isDarkMode.value = enabled
    }
}
