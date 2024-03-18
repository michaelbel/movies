@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.account

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

expect fun NavController.navigateToAccount()

expect fun NavGraphBuilder.accountGraph(
    navigateBack: () -> Unit
)