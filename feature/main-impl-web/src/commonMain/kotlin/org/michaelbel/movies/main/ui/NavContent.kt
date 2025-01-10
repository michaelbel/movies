package org.michaelbel.movies.main.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
expect fun NavContent(
    navHostController: NavHostController = rememberNavController()
)