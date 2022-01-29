package org.michaelbel.moviemade.ui.main

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.michaelbel.moviemade.ui.NavigationContent

@Composable
fun MainScreen() {
    val navController: NavHostController = rememberNavController()

    Scaffold {
        NavigationContent(navController = navController)
    }
}