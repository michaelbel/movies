package org.michaelbel.movies.main

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import org.michaelbel.movies.common.ThemeData
import org.michaelbel.movies.common.viewmodel.BaseViewModel

class MainViewModel: BaseViewModel() {

    val themeData: StateFlow<ThemeData> = flowOf(ThemeData.Companion.Default)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.Lazily,
            initialValue = ThemeData.Companion.Default
        )
}