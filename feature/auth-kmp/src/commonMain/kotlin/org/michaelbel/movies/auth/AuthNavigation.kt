@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

expect fun NavController.navigateToAuth()

expect fun NavGraphBuilder.authGraph(
    navigateBack: () -> Unit
)