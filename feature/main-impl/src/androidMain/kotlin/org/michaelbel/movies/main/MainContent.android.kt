package org.michaelbel.movies.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel
import org.michaelbel.movies.ui.ktx.collectAsStateCommon

@Composable
fun MainContent(
    viewModel: MainViewModel = koinViewModel(),
    enableEdgeToEdge: (Any, Any) -> Unit
) {
    val themeData by viewModel.themeData.collectAsStateCommon()

    MainNavigationContent(
        themeData = themeData,
        enableEdgeToEdge = enableEdgeToEdge
    )
}