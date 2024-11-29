package org.michaelbel.movies.auth.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject
import org.michaelbel.movies.auth.AuthViewModel

@Composable
fun AuthRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = koinInject<AuthViewModel>()
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