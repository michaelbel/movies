package org.michaelbel.movies.main

import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import org.michaelbel.movies.common.ThemeData
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.common.viewmodel.BaseViewModel

class MainViewModel: BaseViewModel() {

    val themeData: StateFlow<ThemeData> = flowOf(ThemeData.Companion.Default.copy(appTheme = AppTheme.NightYes))
        .stateIn(
            scope = scope,
            started = SharingStarted.Companion.Lazily,
            initialValue = ThemeData.Companion.Default
        )
}