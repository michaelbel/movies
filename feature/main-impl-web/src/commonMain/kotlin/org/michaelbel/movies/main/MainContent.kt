package org.michaelbel.movies.main

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun MainContent(
    navHostController: NavHostController = rememberNavController()
) {
    MaterialTheme {
        Text(
            "Content"
        )
    }
}