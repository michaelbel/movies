package org.michaelbel.movies.widget.configure

import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.michaelbel.movies.common.ThemeData
import org.michaelbel.movies.common.viewmodel.BaseViewModel
import org.michaelbel.movies.interactor.Interactor

internal class AppWidgetConfigureViewModel(
    interactor: Interactor
): BaseViewModel() {

    val themeData: StateFlow<ThemeData> = interactor.themeData
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = ThemeData.Default
        )
}