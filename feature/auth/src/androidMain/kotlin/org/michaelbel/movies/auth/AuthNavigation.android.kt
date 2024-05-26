package org.michaelbel.movies.auth

import androidx.navigation.NavGraphBuilder
import org.michaelbel.movies.auth.ktx.authGraphInternal
import org.michaelbel.movies.auth.ui.AuthRoute

fun NavGraphBuilder.authGraph(
    navigateBack: () -> Unit
) {
    authGraphInternal {
        AuthRoute(
            onBackClick = navigateBack
        )
    }
}