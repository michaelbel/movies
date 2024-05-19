package org.michaelbel.movies.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainContent(
    viewModel: MainViewModel = koinViewModel(),
    enableEdgeToEdge: (Any, Any) -> Unit
) {
    val themeData by viewModel.themeData.collectAsStateWithLifecycle()

    MainNavigationContent(
        themeData = themeData,
        enableEdgeToEdge = enableEdgeToEdge
    )
}