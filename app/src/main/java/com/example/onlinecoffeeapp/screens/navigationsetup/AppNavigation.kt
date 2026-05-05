package com.example.onlinecoffeeapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.onlinecoffeeapp.screens.forgetscreen.Veiw.ForgetPasswordScreen
import com.example.onlinecoffeeapp.screens.homescreen.Veiw.HomeScreen
import com.example.onlinecoffeeapp.screens.homescreen.Veiw.ProductDetailScreen
import com.example.onlinecoffeeapp.screens.loginscreen.View.LoginScreen
import com.example.onlinecoffeeapp.screens.signupscreen.Veiw.SignUpScreen
import com.example.onlinecoffeeapp.viewmodel.AuthViewModel
import com.example.onlinecoffeeapp.viewmodel.CoffeeViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val coffeeViewModel: CoffeeViewModel = viewModel()
    
    val startDestination = if (authViewModel.currentUser != null) "home" else "login"

    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onSignUpClick = {
                    navController.navigate("signup")
                },
                onForgetPasswordClick = {
                    navController.navigate("forget")
                }
            )
        }

        composable("signup") {
            SignUpScreen(
                onSignUpSuccess = {
                    navController.navigate("home") {
                        popUpTo("signup") { inclusive = true }
                    }
                },
                onLoginClick = {
                    navController.navigate("login")
                }
            )
        }

        composable("forget") {
            ForgetPasswordScreen(
                onBackToSignIn = {
                    navController.popBackStack()
                }
            )
        }

        composable("home") {
            HomeScreen(
                onProductClick = { product ->
                    navController.navigate("detail/${product.id}")
                },
                coffeeViewModel = coffeeViewModel
            )
        }

        composable(
            route = "detail/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId")
            val products by coffeeViewModel.products.collectAsState()
            val product = products.find { it.id == productId }

            product?.let {
                ProductDetailScreen(
                    product = it,
                    onBackClick = { navController.popBackStack() },
                    onAddToCart = { prod, size, price ->
                        // Implementation for cart
                    }
                )
            }
        }
    }
}
