@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package org.michaelbel.movies.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.dsl.koinConfiguration
import org.michaelbel.movies.accountweb.AccountDialog
import org.michaelbel.movies.authweb.AuthDialog
import org.michaelbel.movies.common.ThemeData
import org.michaelbel.movies.detailsweb.DetailsWebScreen
import org.michaelbel.movies.domain.usecase.AccountDetailsUseCase
import org.michaelbel.movies.domain.usecase.AccountPojoFlowUseCase
import org.michaelbel.movies.domain.usecase.CreateSessionUseCase
import org.michaelbel.movies.domain.usecase.ThemeDataFlowUseCase
import org.michaelbel.movies.domain.usecase.UpdateFavoriteUseCase
import org.michaelbel.movies.interactor.BrowserInteractor
import org.michaelbel.movies.interactor.UiInteractor
import org.michaelbel.movies.interactor.di.interactorKoinModule
import org.michaelbel.movies.main.tabs.MainWebTabsScreen
import org.michaelbel.movies.persistence.database.entity.pojo.AccountPojo
import org.michaelbel.movies.persistence.database.ktx.isNotEmpty
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey
import org.michaelbel.movies.ui.theme.AppTheme

@Composable
fun MainScreen() {
    KoinApplication(
        configuration = koinConfiguration {
            modules(interactorKoinModule)
        }
    ) {
        MainScreenContent()
    }
}

@Composable
private fun MainScreenContent(
    uiInteractor: UiInteractor = koinInject(),
    themeDataFlowUseCase: ThemeDataFlowUseCase = koinInject(),
    browserInteractor: BrowserInteractor = koinInject(),
    accountPojoFlowUseCase: AccountPojoFlowUseCase = koinInject(),
    accountDetailsUseCase: AccountDetailsUseCase = koinInject(),
    createSessionUseCase: CreateSessionUseCase = koinInject(),
    updateFavoriteUseCase: UpdateFavoriteUseCase = koinInject()
) {
    val themeData by remember(themeDataFlowUseCase, uiInteractor) {
        themeDataFlowUseCase(uiInteractor.defaultDynamicColorsEnabled)
    }.collectAsState(initial = ThemeData.Default)
    val accountPojo by remember(accountPojoFlowUseCase) {
        accountPojoFlowUseCase(Unit)
    }.collectAsState(initial = AccountPojo.Empty)

    var tab by remember { mutableStateOf<MainWebDestination>(MainWebDestination.Feed) }
    var details by remember { mutableStateOf<MainWebDestination.Details?>(null) }
    var isAuthDialogVisible by remember { mutableStateOf(false) }
    var isFavePending by remember { mutableStateOf(false) }
    var pendingFavoriteMovieId by remember { mutableStateOf<MovieId?>(null) }
    var isAccountDialogVisible by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val isAuthorized = accountPojo.isNotEmpty

    LaunchedEffect(isAuthorized) {
        if (!isAuthorized && tab == MainWebDestination.Fave) {
            tab = MainWebDestination.Feed
        }
    }

    LaunchedEffect(browserInteractor) {
        val redirect = browserInteractor.consumeAuthRedirect()
        val requestToken = redirect?.requestToken
        val approved = redirect?.approved
        when {
            requestToken == null || approved == null -> Unit
            !approved -> {
                snackbarHostState.showSnackbar(message = "Failure while signing in. Wrong token or no approval")
            }
            else -> {
                try {
                    createSessionUseCase(requestToken).getOrThrow()
                    accountDetailsUseCase(Unit).getOrThrow()
                    snackbarHostState.showSnackbar(message = "Successful authorization")
                } catch (exception: CancellationException) {
                    throw exception
                } catch (_: Exception) {
                    snackbarHostState.showSnackbar(message = "Failure while signing in. Wrong token or no approval")
                }
            }
        }
    }

    AppTheme(
        themeData = themeData
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            MainWebTabsScreen(
                currentDestination = tab,
                onDestinationChange = { destination ->
                    when {
                        destination == MainWebDestination.Fave && !isAuthorized -> {
                            pendingFavoriteMovieId = null
                            isFavePending = true
                            isAuthDialogVisible = true
                        }
                        else -> {
                            tab = destination
                        }
                    }
                },
                onMovieClick = { pagingKey, movieId ->
                    details = MainWebDestination.Details(
                        pagingKey = pagingKey,
                        movieId = movieId
                    )
                },
                accountPojo = accountPojo,
                onAccountClick = {
                    when {
                        isAuthorized -> isAccountDialogVisible = true
                        else -> isAuthDialogVisible = true
                    }
                }
            )

            val currentDetails = details
            if (currentDetails != null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {}
                ) {
                    DetailsWebScreen(
                        pagingKey = currentDetails.pagingKey,
                        movieId = currentDetails.movieId,
                        onBackClick = { details = null },
                        onAuthRequired = {
                            isFavePending = false
                            pendingFavoriteMovieId = currentDetails.movieId
                            isAuthDialogVisible = true
                        },
                        onFavoriteError = { message ->
                            scope.launch {
                                snackbarHostState.currentSnackbarData?.dismiss()
                                snackbarHostState.showSnackbar(message = message)
                            }
                        }
                    )
                }
            }

            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.BottomCenter)
            )

            if (isAuthDialogVisible) {
                AuthDialog(
                    onDismissRequest = {
                        isAuthDialogVisible = false
                        isFavePending = false
                        pendingFavoriteMovieId = null
                    },
                    onAuthorized = {
                        val favoriteMovieId = pendingFavoriteMovieId
                        when {
                            isFavePending -> {
                                tab = MainWebDestination.Fave
                            }
                            favoriteMovieId != null -> {
                                val params = UpdateFavoriteUseCase.Params(
                                    movieId = favoriteMovieId,
                                    favorite = true
                                )
                                scope.launch {
                                    updateFavoriteUseCase(params).onFailure { throwable ->
                                        snackbarHostState.currentSnackbarData?.dismiss()
                                        snackbarHostState.showSnackbar(
                                            message = throwable.message.orEmpty().ifBlank { "Failed to update favorites." }
                                        )
                                    }
                                }
                            }
                        }
                    }
                )
            }

            if (isAccountDialogVisible) {
                AccountDialog(
                    accountPojo = accountPojo,
                    onDismissRequest = { isAccountDialogVisible = false }
                )
            }
        }
    }
}



sealed interface MainWebDestination {
    data object Feed: MainWebDestination
    data object Fave: MainWebDestination
    data object Settings: MainWebDestination
    data class Details(
        val pagingKey: PagingKey,
        val movieId: MovieId
    ): MainWebDestination
}

val mainWebWindowWidth: Dp
    @Composable get() = with(LocalDensity.current) { LocalWindowInfo.current.containerSize.width.toDp() }
