package org.michaelbel.movies.auth.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.michaelbel.movies.auth.AuthViewModel

@Composable
actual fun AuthRoute(
    onBackClick: () -> Unit,
    modifier: Modifier,
    viewModel: AuthViewModel
) {
    AuthScreenContent(
        error = null,
        signInLoading = false,
        loginLoading = false,
        requestToken = null,
        onBackClick = onBackClick,
        onSignInClick = { _, _ -> },
        onLoginClick = {},
        onResetRequestToken = {},
        onUrlClick = {},
        modifier = modifier
    )
}