package com.example.onlinecoffeeapp.screens.navigationsetup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.onlinecoffeeapp.screens.cartitem.Veiw.CartScreen
import com.example.onlinecoffeeapp.screens.cartitem.Veiw.OrderReviewScreen
import com.example.onlinecoffeeapp.screens.favorites.View.FavoritesScreen
import com.example.onlinecoffeeapp.screens.forgetscreen.Veiw.ForgetPasswordScreen
import com.example.onlinecoffeeapp.screens.homescreen.Veiw.ProductDetailScreen
import com.example.onlinecoffeeapp.screens.homescreen.Veiw.HomeScreen
import com.example.onlinecoffeeapp.screens.loginscreen.View.LoginScreen
import com.example.onlinecoffeeapp.screens.profilescreen.View.AddressBookScreen
import com.example.onlinecoffeeapp.screens.profilescreen.View.ProfileScreen
import com.example.onlinecoffeeapp.screens.profilescreen.View.SettingsScreen
import com.example.onlinecoffeeapp.screens.profilescreen.View.MyOrdersScreen
import com.example.onlinecoffeeapp.screens.signupscreen.Veiw.SignUpScreen
import com.example.onlinecoffeeapp.screens.welcomescreen.Veiw.WelcomeScreen
import com.example.onlinecoffeeapp.viewmodel.AuthViewModel
import com.example.onlinecoffeeapp.viewmodel.CoffeeViewModel

@Composable
fun AppNavigation(
    authViewModel: AuthViewModel = viewModel(),
    coffeeViewModel: CoffeeViewModel = viewModel()
) {
    val navController = rememberNavController()
    val startDestination = if (authViewModel.currentUser != null) "home" else "welcome"

    NavHost(navController = navController, startDestination = startDestination) {
        
        composable("welcome") {
            WelcomeScreen(onGetStartedClick = {
                navController.navigate("login") { popUpTo("welcome") { inclusive = true } }
            })
        }

        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("home") { popUpTo("login") { inclusive = true } }
                },
                onSignUpClick = { navController.navigate("signup") },
                onForgetPasswordClick = { navController.navigate("forget") }
            )
        }

        composable("signup") {
            SignUpScreen(
                onSignUpSuccess = {
                    navController.navigate("home") { popUpTo("signup") { inclusive = true } }
                },
                onLoginClick = { navController.navigate("login") }
            )
        }

        composable("forget") {
            ForgetPasswordScreen(onBackToSignIn = { navController.popBackStack() })
        }

        composable("home") {
            HomeScreen(
                onProductClick = { product ->
                    navController.navigate("detail/${product.id}")
                },
                onCartClick = { navController.navigate("cart") },
                onFavoritesClick = { navController.navigate("favorites") },
                onProfileClick = { navController.navigate("profile") },
                coffeeViewModel = coffeeViewModel
            )
        }

        composable("cart") {
            CartScreen(
                coffeeViewModel = coffeeViewModel,
                onBackClick = { navController.popBackStack() },
                onCheckoutClick = { navController.navigate("order_review") }
            )
        }

        composable("order_review") {
            OrderReviewScreen(
                coffeeViewModel = coffeeViewModel,
                onBackClick = { navController.popBackStack() },
                onOrderSuccess = {
                    navController.navigate("home") { popUpTo("home") { inclusive = true } }
                }
            )
        }

        composable("favorites") {
            FavoritesScreen(
                coffeeViewModel = coffeeViewModel,
                onBackClick = { navController.popBackStack() },
                onProductClick = { product ->
                    navController.navigate("detail/${product.id}")
                }
            )
        }

        composable("profile") {
            ProfileScreen(
                authViewModel = authViewModel,
                onBackClick = { navController.popBackStack() },
                onLogoutSuccess = {
                    navController.navigate("welcome") { popUpTo("home") { inclusive = true } }
                },
                onMyOrdersClick = { navController.navigate("my_orders") },
                onAddressBookClick = { navController.navigate("address_book") },
                onSettingsClick = { navController.navigate("settings") }
            )
        }

        composable("my_orders") {
            MyOrdersScreen(onBackClick = { navController.popBackStack() }, coffeeViewModel = coffeeViewModel)
        }

        composable("address_book") {
            AddressBookScreen(onBackClick = { navController.popBackStack() })
        }

        composable("settings") {
            SettingsScreen(coffeeViewModel = coffeeViewModel, onBackClick = { navController.popBackStack() })
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
                        coffeeViewModel.addToCart(prod, size)
                        navController.navigate("cart")
                    }
                )
            }
        }
    }
}
