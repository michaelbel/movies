package org.michaelbel.movies.auth.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel
import org.michaelbel.movies.auth.AuthViewModel

@Composable
fun AuthRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = koinViewModel()
) {
    AuthScreenContent(
        error = viewModel.error,
        signInLoading = viewModel.signInLoading,
        loginLoading = viewModel.loginLoading,
        requestToken = viewModel.requestToken,
        onBackClick = onBackClick,
        onSignInClick = { username, password ->
            viewModel.onSignInClick(username, password, onBackClick)
        },
        onLoginClick = viewModel::onLoginClick,
        onResetRequestToken = viewModel::onResetRequestToken,
        modifier = modifier
    )
}