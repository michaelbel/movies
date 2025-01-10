package org.michaelbel.movies.auth.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.michaelbel.movies.auth.ktx.text
import org.michaelbel.movies.common.browser.navigateToUrl
import org.michaelbel.movies.common.exceptions.CreateSessionWithLoginException
import org.michaelbel.movies.interactor.entity.Password
import org.michaelbel.movies.interactor.entity.Username
import org.michaelbel.movies.interactor.ktx.PasswordSaver
import org.michaelbel.movies.interactor.ktx.UsernameSaver
import org.michaelbel.movies.interactor.ktx.isNotEmpty
import org.michaelbel.movies.interactor.ktx.trim
import org.michaelbel.movies.network.config.TMDB_AUTH_REDIRECT_URL
import org.michaelbel.movies.network.config.TMDB_AUTH_URL_2
import org.michaelbel.movies.network.config.TMDB_AUTH_URL_3
import org.michaelbel.movies.network.config.TMDB_REGISTER
import org.michaelbel.movies.network.config.TMDB_RESET_PASSWORD
import org.michaelbel.movies.network.config.TMDB_URL
import org.michaelbel.movies.ui.accessibility.MoviesContentDescriptionCommon
import org.michaelbel.movies.ui.compose.iconbutton.PasswordIcon
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.ktx.clickableWithoutRipple
import org.michaelbel.movies.ui.ktx.isPortrait
import org.michaelbel.movies.ui.strings.MoviesStrings
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun AuthScreenContent(
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
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    var username by rememberSaveable(saver = UsernameSaver) { mutableStateOf(Username("")) }
    var password by rememberSaveable(saver = PasswordSaver) { mutableStateOf(Password("")) }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    val navigateToTmdbUrl = navigateToUrl(TMDB_URL)
    val navigateToTmdbResetPasswordUrl = navigateToUrl(TMDB_RESET_PASSWORD)
    val navigateToTmdbRegisterUrl = navigateToUrl(TMDB_REGISTER)

    if (requestToken != null) {
        val signUrl = "$TMDB_AUTH_URL_2/$requestToken$TMDB_AUTH_URL_3$TMDB_AUTH_REDIRECT_URL"
        navigateToUrl(signUrl)
        onResetRequestToken()
    }

    Column(
        modifier = modifier
            .padding(horizontal = if (isPortrait) 16.dp else 64.dp)
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.small
            )
            .verticalScroll(scrollState)
    ) {
        AuthToolbar(
            onNavigationIconClick = onBackClick,
            modifier = Modifier.fillMaxWidth()
        )

        Icon(
            painter = painterResource(MoviesIcons.TmdbLogo),
            contentDescription = MoviesContentDescriptionCommon.None,
            modifier = Modifier
                .padding(top = 8.dp)
                .clickableWithoutRipple { navigateToTmdbUrl() }
                .align(Alignment.CenterHorizontally),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )

        OutlinedTextField(
            value = username.value,
            onValueChange = { value ->
                username = Username(value.filterNot(Char::isWhitespace))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 8.dp, end = 16.dp),
            label = {
                Text(
                    text = stringResource(MoviesStrings.auth_label_username)
                )
            },
            isError = error != null,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            singleLine = true
        )

        OutlinedTextField(
            value = password.value,
            onValueChange = { value -> password = Password(value.filterNot(Char::isWhitespace)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 8.dp, end = 16.dp),
            label = {
                Text(
                    text = stringResource(MoviesStrings.auth_label_password)
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

        Row(
            modifier = Modifier.padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = navigateToTmdbRegisterUrl
            ) {
                Text(
                    text = stringResource(MoviesStrings.auth_sign_up)
                )
            }

            AnimatedVisibility(
                visible = error != null && error is CreateSessionWithLoginException,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                TextButton(
                    onClick = navigateToTmdbResetPasswordUrl
                ) {
                    Text(
                        text = stringResource(MoviesStrings.auth_reset_password)
                    )
                }
            }
        }

        Button(
            onClick = { onSignInClick(username.trim, password.trim) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 4.dp, end = 16.dp),
            enabled = username.isNotEmpty && password.isNotEmpty && !signInLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceTint
            )
        ) {
            if (signInLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = stringResource(MoviesStrings.auth_sign_in)
                )
            }
        }

        Button(
            onClick = onLoginClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 8.dp, end = 16.dp),
            enabled = !loginLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceTint
            )
        ) {
            if (loginLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = stringResource(MoviesStrings.auth_login)
                )
            }
        }

        AuthLinksBox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
    }
}

@Preview
@Composable
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