@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package org.michaelbel.movies.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.dsl.koinConfiguration
import org.michaelbel.movies.account.AccountScreen
import org.michaelbel.movies.account.di.accountKoinModule
import org.michaelbel.movies.auth.AuthScreen
import org.michaelbel.movies.auth.di.authKoinModule
import org.michaelbel.movies.common.ThemeData
import org.michaelbel.movies.details.DetailsScreen
import org.michaelbel.movies.details.di.detailsKoinModule
import org.michaelbel.movies.domain.usecase.AccountDetailsUseCase
import org.michaelbel.movies.domain.usecase.AccountPojoFlowUseCase
import org.michaelbel.movies.domain.usecase.CreateSessionUseCase
import org.michaelbel.movies.domain.usecase.ThemeDataFlowUseCase
import org.michaelbel.movies.domain.usecase.UpdateFavoriteUseCase
import org.michaelbel.movies.fave.di.faveKoinModule
import org.michaelbel.movies.interactor.BrowserInteractor
import org.michaelbel.movies.interactor.UiInteractor
import org.michaelbel.movies.interactor.di.interactorKoinModule
import org.michaelbel.movies.interactor.di.webInteractorKoinModule
import org.michaelbel.movies.main.tabs.MainWebTabsScreen
import org.michaelbel.movies.persistence.database.entity.pojo.AccountPojo
import org.michaelbel.movies.persistence.database.ktx.isNotEmpty
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey
import org.michaelbel.movies.platform.inject.flavorServiceKtorModule
import org.michaelbel.movies.settings.di.settingsKoinModule
import org.michaelbel.movies.ui.ObserveAsEvents
import org.michaelbel.movies.ui.dialogUsePlatformDefaultWidth
import org.michaelbel.movies.ui.navigation.AccountDestination
import org.michaelbel.movies.ui.navigation.AppRoute
import org.michaelbel.movies.ui.navigation.AuthDestination
import org.michaelbel.movies.ui.navigation.DetailsDestination
import org.michaelbel.movies.ui.navigation.MainNavigator
import org.michaelbel.movies.ui.pending.PendingAction
import org.michaelbel.movies.ui.pending.PendingActionStore
import org.michaelbel.movies.webui.theme.WebTheme

@Composable
fun MainScreen() {
    KoinApplication(
        configuration = koinConfiguration {
            modules(
                interactorKoinModule,
                webInteractorKoinModule,
                flavorServiceKtorModule,
                accountKoinModule,
                authKoinModule,
                detailsKoinModule,
                faveKoinModule,
                settingsKoinModule
            )
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
    val dialogs = remember { mutableStateListOf<AppRoute>() }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val isAuthorized = accountPojo.isNotEmpty

    ObserveAsEvents(
        flow = MainNavigator.destFlow
    ) { event ->
        when (event) {
            is MainNavigator.NavigationEvent.Back -> {
                when {
                    dialogs.isNotEmpty() -> {
                        dialogs.removeLastOrNull()
                    }
                    else -> {
                        details = null
                    }
                }
            }
            is MainNavigator.NavigationEvent.Forward -> {
                val destination = event.destination
                when {
                    destination is DetailsDestination -> {
                        details = MainWebDestination.Details(
                            pagingKey = destination.movieList.orEmpty(),
                            movieId = destination.movieId
                        )
                    }
                    (destination == AuthDestination || destination == AccountDestination) && dialogs.lastOrNull() != destination -> {
                        dialogs.add(destination)
                    }
                }
            }
            else -> Unit
        }
    }

    LaunchedEffect(isAuthorized) {
        when {
            !isAuthorized -> {
                if (tab == MainWebDestination.Fave) {
                    tab = MainWebDestination.Feed
                }
            }
            else -> {
                when (val pendingAction = PendingActionStore.action) {
                    PendingAction.OpenFave -> {
                        PendingActionStore.clear()
                        tab = MainWebDestination.Fave
                    }
                    is PendingAction.AddFavorite -> {
                        PendingActionStore.clear()
                        val params = UpdateFavoriteUseCase.Params(
                            movieId = pendingAction.movieId,
                            favorite = true
                        )
                        updateFavoriteUseCase(params).onFailure { throwable ->
                            snackbarHostState.currentSnackbarData?.dismiss()
                            snackbarHostState.showSnackbar(
                                message = throwable.message.orEmpty().ifBlank { "Failed to update favorites." }
                            )
                        }
                    }
                    null -> Unit
                }
            }
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

    WebTheme(
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
                            PendingActionStore.set(PendingAction.OpenFave)
                            scope.launch { MainNavigator.forward(AuthDestination) }
                        }
                        else -> {
                            tab = destination
                        }
                    }
                },
                onMovieClick = { pagingKey, movieId ->
                    scope.launch { MainNavigator.forward(DetailsDestination(pagingKey, movieId)) }
                },
                accountPojo = accountPojo,
                onAccountClick = {
                    when {
                        isAuthorized -> {
                            scope.launch { MainNavigator.forward(AccountDestination) }
                        }
                        else -> {
                            scope.launch { MainNavigator.forward(AuthDestination) }
                        }
                    }
                }
            )

            val currentDetails = details
            if (currentDetails != null) {
                key(currentDetails) {
                    val viewModelStoreOwner = remember {
                        object: ViewModelStoreOwner {
                            override val viewModelStore = ViewModelStore()
                        }
                    }

                    DisposableEffect(viewModelStoreOwner) {
                        onDispose { viewModelStoreOwner.viewModelStore.clear() }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .pointerInput(Unit) {}
                    ) {
                        CompositionLocalProvider(LocalViewModelStoreOwner provides viewModelStoreOwner) {
                            DetailsScreen(
                                destination = DetailsDestination(
                                    movieList = currentDetails.pagingKey,
                                    movieId = currentDetails.movieId
                                )
                            )
                        }
                    }
                }
            }

            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.BottomCenter)
            )

            val currentDialog = dialogs.lastOrNull()

            if (currentDialog == AuthDestination) {
                val viewModelStoreOwner = remember {
                    object: ViewModelStoreOwner {
                        override val viewModelStore = ViewModelStore()
                    }
                }

                DisposableEffect(viewModelStoreOwner) {
                    onDispose { viewModelStoreOwner.viewModelStore.clear() }
                }

                Dialog(
                    onDismissRequest = {
                        PendingActionStore.clear()
                        dialogs.removeLastOrNull()
                    },
                    properties = DialogProperties(usePlatformDefaultWidth = dialogUsePlatformDefaultWidth)
                ) {
                    CompositionLocalProvider(LocalViewModelStoreOwner provides viewModelStoreOwner) {
                        AuthScreen()
                    }
                }
            }

            if (currentDialog == AccountDestination) {
                val viewModelStoreOwner = remember {
                    object: ViewModelStoreOwner {
                        override val viewModelStore = ViewModelStore()
                    }
                }

                DisposableEffect(viewModelStoreOwner) {
                    onDispose { viewModelStoreOwner.viewModelStore.clear() }
                }

                Dialog(
                    onDismissRequest = { dialogs.removeLastOrNull() },
                    properties = DialogProperties(usePlatformDefaultWidth = dialogUsePlatformDefaultWidth)
                ) {
                    CompositionLocalProvider(LocalViewModelStoreOwner provides viewModelStoreOwner) {
                        AccountScreen()
                    }
                }
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
