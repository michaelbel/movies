package org.michaelbel.movies.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.michaelbel.movies.main.ui.NavContent

@Composable
fun MainContent(
    navHostController: NavHostController = rememberNavController()
) {
    NavContent(
        navHostController = navHostController
    )
}