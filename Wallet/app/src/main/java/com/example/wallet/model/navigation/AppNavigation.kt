package com.example.wallet.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.wallet.uii.screens.*

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(navController, startDestination = "splash") {

        composable("splash") {
            SplashScreen {
                navController.navigate("login") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }

        composable("login") {
            LoginScreen {
                navController.navigate("home")
            }
        }

        composable("home") {
            HomeScreen(navController)
        }

        composable("detail/{cardId}") { backStack ->
            val id = backStack.arguments?.getString("cardId")?.toInt() ?: 0
            CardDetailScreen(id, navController)
        }

        composable("transactions/{cardId}") { backStack ->
            val id = backStack.arguments?.getString("cardId")?.toInt() ?: 0
            TransactionScreen(id)
        }
    }
}