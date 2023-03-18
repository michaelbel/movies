package org.michaelbel.movies.auth.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.michaelbel.movies.auth.AuthViewModel
import org.michaelbel.movies.auth_impl.R
import org.michaelbel.movies.ui.icons.MoviesIcons

@Composable
fun AuthRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val error: Throwable? by viewModel.error.collectAsStateWithLifecycle()
    val loading: Boolean by viewModel.loading.collectAsStateWithLifecycle()

    AuthScreenContent(
        modifier = modifier,
        error = error,
        loading = loading,
        onBackClick = onBackClick,
        onSignInClick = { username, password ->
            viewModel.onSignInClick(username, password) {
                onBackClick()
            }
        }
    )
}

@Composable
internal fun AuthScreenContent(
    modifier: Modifier = Modifier,
    error: Throwable?,
    loading: Boolean,
    onBackClick: () -> Unit,
    onSignInClick: (String, String) -> Unit
) {
    val focusManager: FocusManager = LocalFocusManager.current
    val focusRequester: FocusRequester = remember { FocusRequester() }
    val keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current

    var username: String by remember { mutableStateOf("") }
    var password: String by remember { mutableStateOf("") }
    var passwordVisible: Boolean by rememberSaveable { mutableStateOf(false) }

    ConstraintLayout(
        modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.small
            )
    ) {
        val (
            toolbar,
            logo,
            usernameField,
            passwordField,
            resetPasswordText,
            errorBox,
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
                },
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
                }
                /*.autofill(
                    autofillTypes = listOf(AutofillType.Username),
                    onFill = { usernameFilled ->
                        username = usernameFilled
                    }
                )*/
                .focusRequester(focusRequester),
            label = {
                Text(
                    text = stringResource(R.string.auth_label_username)
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
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
                }
                /*.autofill(
                    autofillTypes = listOf(AutofillType.Password),
                    onFill = { passwordFilled ->
                        password = passwordFilled
                    }
                )*/
                .focusRequester(focusRequester),
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
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    onSignInClick(username, password)
                }
            ),
            maxLines = 1
        )

        AnimatedVisibility(
            visible = error != null,
            modifier = Modifier
                .constrainAs(errorBox) {
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(passwordField.bottom, 8.dp)
                    end.linkTo(parent.end, 16.dp)
                },
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            AuthErrorBox(
                error = error
            )
        }

        /*Text(
            text = stringResource(R.string.auth_reset_password),
            modifier = Modifier
                .constrainAs(resetPasswordText) {
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                    start.linkTo(parent.start, 18.dp)
                    top.linkTo(passwordField.bottom, 8.dp)
                },
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontSize = 12.sp,
            maxLines = 1
        )*/

        Button(
            onClick = {
                onSignInClick(username, password)
            },
            modifier = Modifier
                .constrainAs(signInButton) {
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(if (error != null) errorBox.bottom else passwordField.bottom, 32.dp)
                    end.linkTo(parent.end, 16.dp)
                },
            enabled = username.isNotEmpty() && password.isNotEmpty() && !loading,
            contentPadding = PaddingValues(
                horizontal = 24.dp,
                vertical = 0.dp
            )
        ) {
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(24.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = stringResource(R.string.auth_sign_in).uppercase(),
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