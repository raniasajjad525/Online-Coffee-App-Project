package com.example.onlinecoffeeapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.onlinecoffeeapp.screens.detailscreen.ProductDetailScreen
import com.example.onlinecoffeeapp.screens.homescreen.HomeScreen
import com.example.onlinecoffeeapp.screens.homescreen.products // <-- Ab ye mil jayegi
import com.example.onlinecoffeeapp.screens.homescreen.Product
import com.example.onlinecoffeeapp.viewmodel.CartViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val cartViewModel: CartViewModel = viewModel()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onProductClick = { product ->
                    navController.navigate("detail/${product.id}")
                }
            )
        }

        composable(
            route = "detail/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId")
            val product = products.find { it.id == productId }

            product?.let {
                ProductDetailScreen(
                    product = it,
                    onBackClick = { navController.popBackStack() },
                    onAddToCart = { prod, size, price ->
                        cartViewModel.addToCart(prod, size, price)
                        navController.navigate("cart")
                    }
                )
            }
        }
    }
}