package org.michaelbel.movies.auth.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import org.koin.androidx.compose.koinViewModel
import org.michaelbel.movies.auth.AuthViewModel
import org.michaelbel.movies.auth.ktx.text
import org.michaelbel.movies.auth_impl_kmp.R
import org.michaelbel.movies.common.browser.openUrl
import org.michaelbel.movies.common.exceptions.CreateSessionWithLoginException
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.interactor.entity.Password
import org.michaelbel.movies.interactor.entity.Username
import org.michaelbel.movies.interactor.ktx.PasswordSaver
import org.michaelbel.movies.interactor.ktx.UsernameSaver
import org.michaelbel.movies.interactor.ktx.isNotEmpty
import org.michaelbel.movies.interactor.ktx.trim
import org.michaelbel.movies.network.config.TMDB_AUTH_REDIRECT_URL
import org.michaelbel.movies.network.config.TMDB_AUTH_URL
import org.michaelbel.movies.network.config.TMDB_PRIVACY_POLICY
import org.michaelbel.movies.network.config.TMDB_REGISTER
import org.michaelbel.movies.network.config.TMDB_RESET_PASSWORD
import org.michaelbel.movies.network.config.TMDB_TERMS_OF_USE
import org.michaelbel.movies.network.config.TMDB_URL
import org.michaelbel.movies.ui.accessibility.MoviesContentDescription
import org.michaelbel.movies.ui.compose.iconbutton.PasswordIcon
import org.michaelbel.movies.ui.icons.MoviesAndroidIcons
import org.michaelbel.movies.ui.ktx.clickableWithoutRipple
import org.michaelbel.movies.ui.ktx.isPortrait
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.theme.MoviesTheme

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

@Composable
fun AuthScreenContent(
    error: Throwable?,
    signInLoading: Boolean,
    loginLoading: Boolean,
    requestToken: String?,
    onBackClick: () -> Unit,
    onSignInClick: (Username, Password) -> Unit,
    onLoginClick: () -> Unit,
    onResetRequestToken: () -> Unit,
    modifier: Modifier = Modifier
) {
    val resultContract = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}
    val toolbarColor = MaterialTheme.colorScheme.primary.toArgb()

    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    var username by rememberSaveable(saver = UsernameSaver) { mutableStateOf(Username("")) }
    var password by rememberSaveable(saver = PasswordSaver) { mutableStateOf(Password("")) }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    if (requestToken != null) {
        val signUrl = String.format(TMDB_AUTH_URL, requestToken, TMDB_AUTH_REDIRECT_URL)
        openUrl(resultContract, toolbarColor, signUrl)
        onResetRequestToken()
    }

    ConstraintLayout(
        modifier = modifier
            .padding(horizontal = if (isPortrait) 16.dp else 64.dp)
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.small
            )
            .verticalScroll(scrollState)
    ) {
        val (
            toolbar,
            logo,
            usernameField,
            passwordField,
            resetPasswordButton,
            signUpButton,
            signInButton,
            loginButton,
            linksBox
        ) = createRefs()

        AuthToolbar(
            modifier = Modifier.constrainAs(toolbar) {
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            },
            onNavigationIconClick = onBackClick
        )

        Icon(
            painter = painterResource(MoviesAndroidIcons.TmdbLogo),
            contentDescription = MoviesContentDescription.None,
            modifier = Modifier
                .constrainAs(logo) {
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(toolbar.bottom, 8.dp)
                    end.linkTo(parent.end, 16.dp)
                }
                .clickableWithoutRipple { openUrl(resultContract, toolbarColor, TMDB_URL) },
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )

        OutlinedTextField(
            value = username.value,
            onValueChange = { value ->
                username = Username(value)
            },
            modifier = Modifier.constrainAs(usernameField) {
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
                start.linkTo(parent.start, 16.dp)
                top.linkTo(logo.bottom, 8.dp)
                end.linkTo(parent.end, 16.dp)
            },
            label = {
                Text(
                    text = stringResource(R.string.auth_label_username)
                )
            },
            isError = error != null,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            singleLine = true
        )

        OutlinedTextField(
            value = password.value,
            onValueChange = { value ->
                password = Password(value)
            },
            modifier = Modifier.constrainAs(passwordField) {
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
                start.linkTo(parent.start, 16.dp)
                top.linkTo(usernameField.bottom, 4.dp)
                end.linkTo(parent.end, 16.dp)
            },
            label = {
                Text(
                    text = stringResource(R.string.auth_label_password)
                )
            },
            trailingIcon = {
                AnimatedVisibility(
                    visible = password.isNotEmpty,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    PasswordIcon(
                        state = passwordVisible,
                        onClick = { passwordVisible = !passwordVisible }
                    )
                }
            },
            supportingText = {
                if (error != null) {
                    Text(
                        text = error.text,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            isError = error != null,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    onSignInClick(username, password)
                }
            ),
            singleLine = true
        )

        AnimatedVisibility(
            visible = error != null && error is CreateSessionWithLoginException,
            modifier = Modifier.constrainAs(resetPasswordButton) {
                width = Dimension.wrapContent
                height = Dimension.wrapContent
                start.linkTo(parent.start, 6.dp)
                top.linkTo(passwordField.bottom, 4.dp)
            },
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            TextButton(
                onClick = { openUrl(resultContract, toolbarColor, TMDB_RESET_PASSWORD) }
            ) {
                Text(
                    text = stringResource(R.string.auth_reset_password)
                )
            }
        }

        AnimatedVisibility(
            visible = error != null && error is CreateSessionWithLoginException,
            modifier = Modifier.constrainAs(signUpButton) {
                width = Dimension.wrapContent
                height = Dimension.wrapContent
                start.linkTo(resetPasswordButton.end, 2.dp)
                top.linkTo(resetPasswordButton.top)
                bottom.linkTo(resetPasswordButton.bottom)
            },
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            TextButton(
                onClick = { openUrl(resultContract, toolbarColor, TMDB_REGISTER) }
            ) {
                Text(
                    text = stringResource(R.string.auth_sign_up)
                )
            }
        }

        Button(
            onClick = { onSignInClick(username.trim, password.trim) },
            modifier = Modifier.constrainAs(signInButton) {
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
                start.linkTo(parent.start, 16.dp)
                top.linkTo(if (error != null && error is CreateSessionWithLoginException) resetPasswordButton.bottom else passwordField.bottom, 16.dp)
                end.linkTo(parent.end, 16.dp)
            },
            enabled = username.isNotEmpty && password.isNotEmpty && !signInLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceTint
            ),
            contentPadding = PaddingValues(horizontal = 24.dp)
        ) {
            if (signInLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = stringResource(R.string.auth_sign_in)
                )
            }
        }

        Button(
            onClick = onLoginClick,
            modifier = Modifier.constrainAs(loginButton) {
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
                start.linkTo(parent.start, 16.dp)
                top.linkTo(signInButton.bottom, 8.dp)
                end.linkTo(parent.end, 16.dp)
            },
            enabled = !loginLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceTint
            ),
            contentPadding = PaddingValues(horizontal = 24.dp)
        ) {
            if (loginLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = stringResource(R.string.auth_login)
                )
            }
        }

        AuthLinksBox(
            onTermsOfUseClick = { openUrl(resultContract, toolbarColor, TMDB_TERMS_OF_USE) },
            onPrivacyPolicyClick = { openUrl(resultContract, toolbarColor, TMDB_PRIVACY_POLICY) },
            modifier = Modifier.constrainAs(linksBox) {
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
                start.linkTo(parent.start)
                top.linkTo(loginButton.bottom, 16.dp)
                end.linkTo(parent.end)
            }
        )
    }
}

@Composable
@DevicePreviews
private fun AuthScreenContentPreview() {
    MoviesTheme {
        AuthScreenContent(
            error = null,
            signInLoading = false,
            loginLoading = false,
            requestToken = null,
            onBackClick = {},
            onSignInClick = { _,_ -> },
            onLoginClick = {},
            onResetRequestToken = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}

@Composable
@Preview
private fun AuthScreenContentAmoledPreview() {
    MoviesTheme(
        theme = AppTheme.Amoled
    ) {
        AuthScreenContent(
            error = null,
            signInLoading = false,
            loginLoading = false,
            requestToken = null,
            onBackClick = {},
            onSignInClick = { _,_ -> },
            onLoginClick = {},
            onResetRequestToken = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}