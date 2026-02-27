package com.example.lab5.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lab5.ui.screens.GameDetailScreen
import com.example.lab5.ui.screens.HomeScreen

/**
 * Main navigation graph for the Pixel Vault game store.
 */
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onGameClick = { gameId ->
                    navController.navigate("detail/$gameId")
                }
            )
        }

        composable(
            route = "detail/{gameId}",
            arguments = listOf(
                navArgument("gameId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val gameId = backStackEntry.arguments?.getInt("gameId") ?: 0
            GameDetailScreen(
                gameId = gameId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
