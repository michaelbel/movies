package org.michaelbel.moviemade.app.features.main

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold {
        Content(navController)
    }
}

@Composable
private fun Content(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = ROUTE_HOME
    ) {
        composable(ROUTE_HOME) { HomeScreen(navHostController) }
        composable(ROUTE_DETAILS) { DetailsScreen(navHostController) }
    }
}