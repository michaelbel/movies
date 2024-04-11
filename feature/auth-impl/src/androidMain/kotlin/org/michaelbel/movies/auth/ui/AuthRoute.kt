package org.michaelbel.movies.auth.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import org.koin.androidx.compose.koinViewModel
import org.michaelbel.movies.auth.AuthViewModel
import org.michaelbel.movies.common.browser.openUrl

@Composable
fun AuthRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = koinViewModel()
) {
    val resultContract = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}
    val toolbarColor = MaterialTheme.colorScheme.primary.toArgb()

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
        onUrlClick = { url -> openUrl(resultContract, toolbarColor, url) },
        modifier = modifier
    )
}