package org.michaelbel.movies.details

import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import org.michaelbel.movies.ui.navigation.DetailsDestination

@Composable
expect fun DetailsScreen(
    destination: DetailsDestination,
    viewModel: DetailsViewModel = koinViewModel(parameters = { parametersOf(destination) })
)