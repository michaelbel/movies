package org.michaelbel.movies.main

import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.michaelbel.movies.common.ThemeData
import org.michaelbel.movies.common.viewmodel.BaseViewModel
import org.michaelbel.movies.interactor.Interactor

class MainViewModel(
    interactor: Interactor
): BaseViewModel() {

    val themeData: StateFlow<ThemeData> = interactor.themeData
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = ThemeData.Default
        )
}