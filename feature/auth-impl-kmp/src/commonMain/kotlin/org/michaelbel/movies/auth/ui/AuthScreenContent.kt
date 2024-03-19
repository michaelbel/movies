@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.auth.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import org.michaelbel.movies.auth.AuthViewModel
import org.michaelbel.movies.interactor.entity.Password
import org.michaelbel.movies.interactor.entity.Username

@Composable
expect fun AuthRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel()
)

@Composable
expect fun AuthScreenContent(
    error: Throwable?,
    signInLoading: Boolean,
    loginLoading: Boolean,
    requestToken: String?,
    onBackClick: () -> Unit,
    onSignInClick: (Username, Password) -> Unit,
    onLoginClick: () -> Unit,
    onResetRequestToken: () -> Unit,
    modifier: Modifier = Modifier
)