package org.michaelbel.movies.auth.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ScrollState
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import org.michaelbel.movies.auth.AuthViewModel
import org.michaelbel.movies.auth.ktx.text
import org.michaelbel.movies.auth_impl.R
import org.michaelbel.movies.common.browser.openUrl
import org.michaelbel.movies.common.exceptions.CreateSessionWithLoginException
import org.michaelbel.movies.network.TMDB_PRIVACY_POLICY
import org.michaelbel.movies.network.TMDB_REGISTER
import org.michaelbel.movies.network.TMDB_RESET_PASSWORD
import org.michaelbel.movies.network.TMDB_TERMS_OF_USE
import org.michaelbel.movies.network.TMDB_URL
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.ktx.clickableWithoutRipple
import org.michaelbel.movies.ui.ktx.isPortrait

@Composable
fun AuthRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel()
) {
    AuthScreenContent(
        error = viewModel.error,
        loading = viewModel.loading,
        onBackClick = onBackClick,
        onSignInClick = { username, password ->
            viewModel.onSignInClick(username, password, onBackClick)
        },
        modifier = modifier
    )
}

@Composable
internal fun AuthScreenContent(
    error: Throwable?,
    loading: Boolean,
    onBackClick: () -> Unit,
    onSignInClick: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val resultContract = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {}
    val toolbarColor: Int = MaterialTheme.colorScheme.primary.toArgb()

    val focusManager: FocusManager = LocalFocusManager.current
    val scrollState: ScrollState = rememberScrollState()

    var username: String by rememberSaveable { mutableStateOf("") }
    var password: String by rememberSaveable { mutableStateOf("") }
    var passwordVisible: Boolean by rememberSaveable { mutableStateOf(false) }

    ConstraintLayout(
        modifier
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
            divider,
            loginButton,
            linksBox
        ) = createRefs()

        AuthToolbar(
            modifier = Modifier
                .constrainAs(toolbar) {
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                },
            onNavigationIconClick = onBackClick
        )

        Icon(
            painter = painterResource(MoviesIcons.TmdbLogo),
            contentDescription = null,
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
            value = username,
            onValueChange = { value: String ->
                username = value
            },
            modifier = Modifier
                .constrainAs(usernameField) {
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
            singleLine = true,
            maxLines = 1
        )

        OutlinedTextField(
            value = password,
            onValueChange = { value: String ->
                password = value
            },
            modifier = Modifier
                .constrainAs(passwordField) {
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
                    visible = password.isNotEmpty(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    IconButton(
                        onClick = {
                            passwordVisible = !passwordVisible
                        }
                    ) {
                        Icon(
                            imageVector = if (passwordVisible) MoviesIcons.Visibility else MoviesIcons.VisibilityOff,
                            contentDescription = null
                        )
                    }
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
            singleLine = true,
            maxLines = 1
        )

        AnimatedVisibility(
            visible = error != null && error is CreateSessionWithLoginException,
            modifier = Modifier
                .constrainAs(resetPasswordButton) {
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
            modifier = Modifier
                .constrainAs(signUpButton) {
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
            onClick = {
                onSignInClick(username.trim(), password.trim())
            },
            modifier = Modifier
                .constrainAs(signInButton) {
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(if (error != null && error is CreateSessionWithLoginException) resetPasswordButton.bottom else passwordField.bottom, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                },
            enabled = username.isNotEmpty() && password.isNotEmpty() && !loading,
            contentPadding = PaddingValues(horizontal = 24.dp)
        ) {
            if (loading) {
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

        /*Text(
            text = "Or",
            modifier = Modifier
                .constrainAs(divider) {
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(signInButton.bottom, 8.dp)
                    end.linkTo(parent.end, 16.dp)
                },
            color = MaterialTheme.colorScheme.primary,
            fontSize = 14.sp,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.W400,
            maxLines = 1
        )*/

        /*Button(
            onClick = {},
            modifier = Modifier
                .constrainAs(loginButton) {
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(divider.bottom, 8.dp)
                    end.linkTo(parent.end, 16.dp)
                },
            enabled = true,
            contentPadding = PaddingValues(
                horizontal = 24.dp,
                vertical = 0.dp
            )
        ) {
            Text(
                text = stringResource(R.string.auth_login).uppercase(),
            )
        }*/

        AuthLinksBox(
            onTermsOfUseClick = { openUrl(resultContract, toolbarColor, TMDB_TERMS_OF_USE) },
            onPrivacyPolicyClick = { openUrl(resultContract, toolbarColor, TMDB_PRIVACY_POLICY) },
            modifier = Modifier
                .constrainAs(linksBox) {
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                    start.linkTo(parent.start)
                    top.linkTo(signInButton.bottom, 16.dp)
                    end.linkTo(parent.end)
                }
        )
    }
}