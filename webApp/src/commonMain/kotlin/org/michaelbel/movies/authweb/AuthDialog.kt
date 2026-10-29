@file:OptIn(
    ExperimentalLayoutApi::class,
    ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalMaterial3Api::class
)

package org.michaelbel.movies.authweb

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
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.foundation.text.input.byValue
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.michaelbel.movies.authweb.ktx.text
import org.michaelbel.movies.domain.usecase.AccountDetailsUseCase
import org.michaelbel.movies.domain.usecase.CreateRequestTokenUseCase
import org.michaelbel.movies.domain.usecase.CreateRequestTokenUseCase.CreateRequestTokenException
import org.michaelbel.movies.domain.usecase.CreateSessionUseCase
import org.michaelbel.movies.domain.usecase.CreateSessionWithLoginUseCase
import org.michaelbel.movies.domain.usecase.CreateSessionWithLoginUseCase.CreateSessionWithLoginException
import org.michaelbel.movies.interactor.BrowserInteractor
import org.michaelbel.movies.main.mainWebWindowWidth
import org.michaelbel.movies.network.config.TMDB_AUTH_URL_2
import org.michaelbel.movies.network.config.TMDB_AUTH_URL_3
import org.michaelbel.movies.network.config.TMDB_PRIVACY_POLICY
import org.michaelbel.movies.network.config.TMDB_REGISTER
import org.michaelbel.movies.network.config.TMDB_RESET_PASSWORD
import org.michaelbel.movies.network.config.TMDB_TERMS_OF_USE
import org.michaelbel.movies.network.config.TMDB_URL
import org.michaelbel.movies.ui.clickableWithoutRipple
import org.michaelbel.movies.ui.compose.PasswordVisibilityIcon
import org.michaelbel.movies.ui.icons.MoviesIcons

@Composable
fun AuthDialog(
    onDismissRequest: () -> Unit,
    onAuthorized: () -> Unit = {},
    browserInteractor: BrowserInteractor = koinInject(),
    accountDetailsUseCase: AccountDetailsUseCase = koinInject(),
    createSessionUseCase: CreateSessionUseCase = koinInject(),
    createRequestTokenUseCase: CreateRequestTokenUseCase = koinInject(),
    createSessionWithLoginUseCase: CreateSessionWithLoginUseCase = koinInject()
) {
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val uriHandler = LocalUriHandler.current
    val scrollState = rememberScrollState()

    var username by remember { mutableStateOf("") }
    val passwordTextFieldState = remember { TextFieldState() }
    var passwordVisible by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<Throwable?>(null) }
    var signInJob by remember { mutableStateOf<Job?>(null) }
    var loginJob by remember { mutableStateOf<Job?>(null) }

    val isSignInJobActive = signInJob != null
    val isLoginJobActive = loginJob != null
    val isPasswordNotEmpty = passwordTextFieldState.text.isNotEmpty()

    val buttonContainerColor = MaterialTheme.colorScheme.surfaceTint
    val buttonContentColor = contentColorFor(buttonContainerColor).let { color ->
        if (color == Color.Unspecified) MaterialTheme.colorScheme.onSurface else color
    }

    val signInButtonColors = ButtonDefaults.buttonColors(
        containerColor = buttonContainerColor,
        contentColor = buttonContentColor,
        disabledContainerColor = if (isSignInJobActive) buttonContainerColor else MaterialTheme.colorScheme.onSurface.copy(alpha = .12F),
        disabledContentColor = if (isSignInJobActive) buttonContentColor else MaterialTheme.colorScheme.onSurface.copy(alpha = .38F)
    )
    val loadingButtonColors = ButtonDefaults.buttonColors(
        containerColor = buttonContainerColor,
        contentColor = buttonContentColor,
        disabledContainerColor = buttonContainerColor,
        disabledContentColor = buttonContentColor
    )

    val onSignInClick: () -> Unit = {
        error = null
        val job = scope.launch {
            try {
                val token = createRequestTokenUseCase(false).getOrThrow()
                val params = CreateSessionWithLoginUseCase.Params(
                    username = username.trim(),
                    password = passwordTextFieldState.text.toString().trim(),
                    requestToken = token.requestToken
                )
                val sessionToken = createSessionWithLoginUseCase(params).getOrThrow()
                createSessionUseCase(sessionToken.requestToken).getOrThrow()
                accountDetailsUseCase(Unit).getOrThrow()
                onAuthorized()
                onDismissRequest()
            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: Exception) {
                error = exception
            }
        }
        signInJob = job
        job.invokeOnCompletion { signInJob = null }
    }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = true
        )
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = if (mainWebWindowWidth < 840.dp) 16.dp else 64.dp)
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
                        text = "Login",
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
                        onClick = onDismissRequest,
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
                            contentDescription = "Close Icon",
                            modifier = Modifier.size(IconButtonDefaults.smallIconSize),
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )

            Icon(
                painter = painterResource(MoviesIcons.TmdbLogo),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .clickableWithoutRipple { uriHandler.openUri(TMDB_URL) }
                    .align(Alignment.CenterHorizontally),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )

            OutlinedTextField(
                value = username,
                onValueChange = { value ->
                    username = value.filterNot(Char::isWhitespace)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp)
                    .semantics { contentType = ContentType.Username },
                label = {
                    Text(
                        text = "Username"
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

            OutlinedSecureTextField(
                state = passwordTextFieldState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp)
                    .semantics { contentType = ContentType.Password },
                label = {
                    Text(
                        text = "Password"
                    )
                },
                trailingIcon = {
                    AnimatedVisibility(
                        visible = isPasswordNotEmpty,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        IconButton(
                            onClick = { passwordVisible = !passwordVisible },
                            modifier = Modifier.pointerHoverIcon(PointerIcon.Hand)
                        ) {
                            PasswordVisibilityIcon(
                                passwordVisible = passwordVisible,
                                contentDescription = if (passwordVisible) "Password Visible Icon" else "Password Invisible Icon",
                                modifier = Modifier.size(IconButtonDefaults.smallIconSize),
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                },
                supportingText = if (error != null) {
                    {
                        Text(
                            text = error.text,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                } else null,
                isError = error != null,
                inputTransformation = InputTransformation.byValue { _, proposed -> proposed.filterNot(Char::isWhitespace) },
                textObfuscationMode = if (passwordVisible) TextObfuscationMode.Visible else TextObfuscationMode.Hidden,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                onKeyboardAction = {
                    focusManager.clearFocus()
                    onSignInClick()
                }
            )

            FlowRow(
                modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                TextButton(
                    onClick = { uriHandler.openUri(TMDB_REGISTER) },
                    shapes = ButtonDefaults.shapes()
                ) {
                    Text(
                        text = "Sign up",
                        style = LocalTextStyle.current.copy(
                            textAlign = TextAlign.Center
                        )
                    )
                }

                AnimatedVisibility(
                    visible = error != null && error is CreateSessionWithLoginException,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    TextButton(
                        onClick = { uriHandler.openUri(TMDB_RESET_PASSWORD) },
                        shapes = ButtonDefaults.shapes()
                    ) {
                        Text(
                            text = "Reset Password",
                            style = LocalTextStyle.current.copy(
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }
            }

            Button(
                onClick = onSignInClick,
                shapes = ButtonDefaults.shapes(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 4.dp, end = 16.dp),
                enabled = username.trim().isNotEmpty() && passwordTextFieldState.text.toString().trim().isNotEmpty() && !isSignInJobActive,
                colors = signInButtonColors,
                contentPadding = PaddingValues(horizontal = 24.dp)
            ) {
                if (isSignInJobActive) {
                    LoadingIndicator(
                        modifier = Modifier.size(40.dp),
                        color = buttonContentColor
                    )
                } else {
                    Text(
                        text = "Sign in",
                        style = LocalTextStyle.current.copy(
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }

            Button(
                onClick = {
                    error = null
                    val job = scope.launch {
                        try {
                            val requestToken = createRequestTokenUseCase(true).getOrThrow().requestToken
                            browserInteractor.navigate("$TMDB_AUTH_URL_2/$requestToken$TMDB_AUTH_URL_3${browserInteractor.authRedirectUrl}")
                        } catch (exception: CancellationException) {
                            throw exception
                        } catch (_: CreateRequestTokenException) {
                            // Mirrors desktop: failed token request for TMDB login does not show an error.
                        } catch (exception: Exception) {
                            error = exception
                        }
                    }
                    loginJob = job
                    job.invokeOnCompletion { loginJob = null }
                },
                shapes = ButtonDefaults.shapes(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp),
                enabled = !isLoginJobActive,
                colors = loadingButtonColors,
                contentPadding = PaddingValues(horizontal = 24.dp)
            ) {
                if (isLoginJobActive) {
                    LoadingIndicator(
                        modifier = Modifier.size(40.dp),
                        color = buttonContentColor
                    )
                } else {
                    Text(
                        text = "Login with Themoviedb",
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
                        onClick = { uriHandler.openUri(TMDB_TERMS_OF_USE) },
                        shapes = ButtonDefaults.shapes(),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        Text(
                            text = "Terms of Use",
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
                        onClick = { uriHandler.openUri(TMDB_PRIVACY_POLICY) },
                        shapes = ButtonDefaults.shapes(),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        Text(
                            text = "Privacy Policy",
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
