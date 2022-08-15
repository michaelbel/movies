package org.michaelbel.movies.ui.main

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.michaelbel.movies.ui.NavigationContent

@Composable
fun MainScreen(
    onAppUpdateClicked: () -> Unit
) {
    val navController: NavHostController = rememberNavController()

    Scaffold {
        NavigationContent(
            navController = navController,
            onAppUpdateClicked = onAppUpdateClicked
        )
    }
}