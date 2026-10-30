@file:OptIn(
    ExperimentalLayoutApi::class,
    ExperimentalMaterial3ExpressiveApi::class
)

package org.michaelbel.movies.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.foundation.text.input.byValue
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedSecureTextField
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewWrapper
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.distinctUntilChanged
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.michaelbel.movies.auth.intent.AuthIntent
import org.michaelbel.movies.auth.ktx.text
import org.michaelbel.movies.auth.model.AuthModel
import org.michaelbel.movies.auth.preview.AuthModelPreviewParameterProvider
import org.michaelbel.movies.common.browser.navigateToUrl
import org.michaelbel.movies.common.browser.tmdbAuthRedirectUrl
import org.michaelbel.movies.domain.usecase.CreateSessionWithLoginUseCase.CreateSessionWithLoginException
import org.michaelbel.movies.interactor.entity.Password
import org.michaelbel.movies.interactor.entity.Username
import org.michaelbel.movies.interactor.ktx.isNotEmpty
import org.michaelbel.movies.interactor.ktx.trim
import org.michaelbel.movies.network.config.TMDB_AUTH_URL_2
import org.michaelbel.movies.network.config.TMDB_AUTH_URL_3
import org.michaelbel.movies.network.config.TMDB_PRIVACY_POLICY
import org.michaelbel.movies.network.config.TMDB_REGISTER
import org.michaelbel.movies.network.config.TMDB_RESET_PASSWORD
import org.michaelbel.movies.network.config.TMDB_TERMS_OF_USE
import org.michaelbel.movies.network.config.TMDB_URL
import org.michaelbel.movies.ui.accessibility.MoviesContentDescription
import org.michaelbel.movies.ui.clickableWithoutRipple
import org.michaelbel.movies.ui.collectAsStateCommon
import org.michaelbel.movies.ui.compose.PasswordVisibilityIcon
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.isNavigationBar
import org.michaelbel.movies.ui.preview.wrapper.ThemeWrapper
import org.michaelbel.movies.ui.strings.MoviesStrings

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = koinViewModel()
) {
    val state by viewModel.stateFlow.collectAsStateCommon()

    AuthScreenContent(
        state = state,
        dispatch = viewModel::dispatch
    )
}

@Composable
private fun AuthScreenContent(
    state: AuthModel,
    dispatch: (AuthIntent) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()
    val passwordTextFieldState = rememberTextFieldState(initialText = state.password.value)
    val password = Password(passwordTextFieldState.text.toString())
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    val navigateToTmdbUrl = navigateToUrl(TMDB_URL)
    val navigateToTmdbResetPasswordUrl = navigateToUrl(TMDB_RESET_PASSWORD)
    val navigateToTmdbRegisterUrl = navigateToUrl(TMDB_REGISTER)

    state.requestToken?.let { requestToken ->
        val signUrl = "$TMDB_AUTH_URL_2/$requestToken$TMDB_AUTH_URL_3${tmdbAuthRedirectUrl()}"
        val navigateToTmdbAuthUrl = navigateToUrl(signUrl)
        LaunchedEffect(requestToken) {
            navigateToTmdbAuthUrl()
            dispatch(AuthIntent.ResetRequestToken)
        }
    }

    val navigateToTermsOfUseUrl = navigateToUrl(TMDB_TERMS_OF_USE)
    val navigateToPrivacyPolicyUrl = navigateToUrl(TMDB_PRIVACY_POLICY)
    val buttonContainerColor = MaterialTheme.colorScheme.surfaceTint
    val buttonContentColor = contentColorFor(buttonContainerColor).let { color ->
        if (color == Color.Unspecified) MaterialTheme.colorScheme.onSurface else color
    }

    LaunchedEffect(state.password) {
        if (passwordTextFieldState.text.toString() != state.password.value) {
            passwordTextFieldState.setTextAndPlaceCursorAtEnd(state.password.value)
        }
    }

    LaunchedEffect(passwordTextFieldState, dispatch) {
        snapshotFlow { passwordTextFieldState.text.toString() }
            .distinctUntilChanged()
            .collect { value ->
                dispatch(AuthIntent.PasswordChange(Password(value.filterNot(Char::isWhitespace))))
            }
    }

    val signInButtonColors = ButtonDefaults.buttonColors(
        containerColor = buttonContainerColor,
        contentColor = buttonContentColor,
        disabledContainerColor = if (state.isSignInJobActive) buttonContainerColor else MaterialTheme.colorScheme.onSurface.copy(alpha = .12F),
        disabledContentColor = if (state.isSignInJobActive) buttonContentColor else MaterialTheme.colorScheme.onSurface.copy(alpha = .38F)
    )
    val loadingButtonColors = ButtonDefaults.buttonColors(
        containerColor = buttonContainerColor,
        contentColor = buttonContentColor,
        disabledContainerColor = buttonContainerColor,
        disabledContentColor = buttonContentColor
    )

    Column(
        modifier = Modifier
            .padding(horizontal = if (isNavigationBar) 16.dp else 64.dp)
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.small
            )
            .verticalScroll(scrollState)
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(MoviesStrings.auth_title),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            },
            modifier = Modifier.fillMaxWidth(),
            navigationIcon = {
                FilledIconButton(
                    onClick = { dispatch(AuthIntent.BackClick) },
                    shapes = IconButtonDefaults.shapes(
                        shape = IconButtonDefaults.smallRoundShape,
                        pressedShape = IconButtonDefaults.smallPressedShape
                    ),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = .08F),
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                ) {
                    Image(
                        imageVector = MoviesIcons.Close,
                        contentDescription = stringResource(MoviesContentDescription.CloseIcon),
                        modifier = Modifier.size(IconButtonDefaults.smallIconSize),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            )
        )

        if (state.isTraktProviderSelected) {
            Icon(
                painter = painterResource(MoviesIcons.TraktLogo),
                contentDescription = MoviesContentDescription.None,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .size(64.dp)
                    .align(Alignment.CenterHorizontally),
                tint = Color.Unspecified
            )

            Text(
                text = stringResource(MoviesStrings.auth_trakt_title),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    textAlign = TextAlign.Center
                )
            )

            Text(
                text = stringResource(MoviesStrings.auth_trakt_description),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    textAlign = TextAlign.Center
                )
            )

            if (state.error != null) {
                Text(
                    text = state.error.text,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 8.dp, end = 16.dp),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                )
            }

            Button(
                onClick = { dispatch(AuthIntent.TraktSignInClick) },
                shapes = ButtonDefaults.shapes(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp),
                enabled = !state.isTraktSignInJobActive,
                colors = loadingButtonColors,
                contentPadding = PaddingValues(horizontal = 24.dp)
            ) {
                if (state.isTraktSignInJobActive) {
                    LoadingIndicator(
                        modifier = Modifier.size(40.dp),
                        color = buttonContentColor
                    )
                } else {
                    Text(
                        text = stringResource(MoviesStrings.auth_trakt_sign_in),
                        style = LocalTextStyle.current.copy(
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }

            if (state.isTraktSignInJobActive) {
                Text(
                    text = stringResource(MoviesStrings.auth_trakt_waiting),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 8.dp, end = 16.dp),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        textAlign = TextAlign.Center
                    )
                )

                TextButton(
                    onClick = { dispatch(AuthIntent.TraktSignInCancelClick) },
                    shapes = ButtonDefaults.shapes(),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 4.dp)
                ) {
                    Text(
                        text = stringResource(MoviesStrings.auth_trakt_cancel),
                        style = LocalTextStyle.current.copy(
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }

            Box(
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        if (!state.isTraktProviderSelected) {
            Icon(
                painter = painterResource(MoviesIcons.TmdbLogo),
                contentDescription = MoviesContentDescription.None,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .clickableWithoutRipple { navigateToTmdbUrl() }
                    .align(Alignment.CenterHorizontally),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )

            OutlinedTextField(
                value = state.username.value,
                onValueChange = { value ->
                    dispatch(AuthIntent.UsernameChange(Username(value.filterNot(Char::isWhitespace))))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp)
                    .semantics { contentType = ContentType.Username },
                label = {
                    Text(
                        text = stringResource(MoviesStrings.auth_label_username)
                    )
                },
                isError = state.error != null,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                singleLine = true
            )

            OutlinedSecureTextField(
                state = passwordTextFieldState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp)
                    .semantics { contentType = ContentType.Password },
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
                        IconButton(
                            onClick = { passwordVisible = !passwordVisible },
                            modifier = Modifier.pointerHoverIcon(PointerIcon.Hand)
                        ) {
                            PasswordVisibilityIcon(
                                passwordVisible = passwordVisible,
                                contentDescription = stringResource(if (passwordVisible) MoviesContentDescription.PasswordIcon else MoviesContentDescription.PasswordOffIcon),
                                modifier = Modifier.size(IconButtonDefaults.smallIconSize),
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                },
                supportingText = if (state.error != null) {
                    {
                        Text(
                            text = state.error.text,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                } else null,
                isError = state.error != null,
                inputTransformation = InputTransformation.byValue { _, proposed -> proposed.filterNot(Char::isWhitespace) },
                textObfuscationMode = if (passwordVisible) TextObfuscationMode.Visible else TextObfuscationMode.Hidden,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                onKeyboardAction = {
                    focusManager.clearFocus()
                    dispatch(AuthIntent.SignInClick(state.username.trim, password.trim))
                }
            )

            FlowRow(
                modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                TextButton(
                    onClick = navigateToTmdbRegisterUrl,
                    shapes = ButtonDefaults.shapes()
                ) {
                    Text(
                        text = stringResource(MoviesStrings.auth_sign_up),
                        style = LocalTextStyle.current.copy(
                            textAlign = TextAlign.Center
                        )
                    )
                }

                AnimatedVisibility(
                    visible = state.error != null && state.error is CreateSessionWithLoginException,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    TextButton(
                        onClick = navigateToTmdbResetPasswordUrl,
                        shapes = ButtonDefaults.shapes()
                    ) {
                        Text(
                            text = stringResource(MoviesStrings.auth_reset_password),
                            style = LocalTextStyle.current.copy(
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }
            }

            Button(
                onClick = { dispatch(AuthIntent.SignInClick(state.username.trim, password.trim)) },
                shapes = ButtonDefaults.shapes(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 4.dp, end = 16.dp),
                enabled = state.username.isNotEmpty && password.isNotEmpty && !state.isSignInJobActive,
                colors = signInButtonColors,
                contentPadding = PaddingValues(horizontal = 24.dp)
            ) {
                if (state.isSignInJobActive) {
                    LoadingIndicator(
                        modifier = Modifier.size(40.dp),
                        color = buttonContentColor
                    )
                } else {
                    Text(
                        text = stringResource(MoviesStrings.auth_sign_in),
                        style = LocalTextStyle.current.copy(
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }

            Button(
                onClick = { dispatch(AuthIntent.LoginClick) },
                shapes = ButtonDefaults.shapes(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp),
                enabled = !state.isLoginJobActive,
                colors = loadingButtonColors,
                contentPadding = PaddingValues(horizontal = 24.dp)
            ) {
                if (state.isLoginJobActive) {
                    LoadingIndicator(
                        modifier = Modifier.size(40.dp),
                        color = buttonContentColor
                    )
                } else {
                    Text(
                        text = stringResource(MoviesStrings.auth_login),
                        style = LocalTextStyle.current.copy(
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HorizontalDivider(
                    thickness = .1.dp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        modifier = Modifier.padding(vertical = 8.dp),
                        onClick = navigateToTermsOfUseUrl,
                        shapes = ButtonDefaults.shapes(),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        Text(
                            text = stringResource(MoviesStrings.auth_terms_of_use),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                textAlign = TextAlign.Center
                            )
                        )
                    }

                    Box(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .size(3.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.onPrimaryContainer)
                    )

                    TextButton(
                        modifier = Modifier.padding(vertical = 8.dp),
                        onClick = navigateToPrivacyPolicyUrl,
                        shapes = ButtonDefaults.shapes(),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        Text(
                            text = stringResource(MoviesStrings.auth_privacy_policy),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }
            }
        }
    }
}

@PreviewWrapper(ThemeWrapper::class)
@Preview
@Composable
private fun AuthScreenContentPreview(
    @PreviewParameter(AuthModelPreviewParameterProvider::class) state: AuthModel
) {
    AuthScreenContent(
        state = state,
        dispatch = {}
    )
}
