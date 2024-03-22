package org.michaelbel.movies.debug

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.common.viewmodel.BaseViewModel
import org.michaelbel.movies.interactor.Interactor
import javax.inject.Inject

@HiltViewModel
internal class DebugViewModel @Inject constructor(
    interactor: Interactor,
): BaseViewModel() {

    val currentTheme: StateFlow<AppTheme> = interactor.currentTheme
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = AppTheme.FollowSystem
        )

    val dynamicColors: StateFlow<Boolean> = interactor.dynamicColors
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = false
        )
}