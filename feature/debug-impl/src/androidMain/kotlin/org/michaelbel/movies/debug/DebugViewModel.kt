package org.michaelbel.movies.debug

import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import org.michaelbel.movies.common.ThemeData
import org.michaelbel.movies.common.viewmodel.BaseViewModel
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.platform.Flavor
import org.michaelbel.movies.platform.app.AppService
import org.michaelbel.movies.platform.messaging.MessagingService

internal class DebugViewModel(
    interactor: Interactor,
    appService: AppService,
    private val messagingService: MessagingService
): BaseViewModel() {

    val isFirebaseTokenFeatureEnabled = appService.flavor == Flavor.Gms

    val themeDataFlow: StateFlow<ThemeData> = interactor.themeData
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = ThemeData.Default
        )

    val firebaseTokenFlow: StateFlow<String> = flow { emit(messagingService.awaitToken()) }
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = ""
        )
}