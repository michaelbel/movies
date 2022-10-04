package org.michaelbel.movies

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.domain.interactor.SettingsInteractor
import org.michaelbel.movies.ui.SystemTheme

@HiltViewModel
class MainViewModel @Inject constructor(
    settingsInteractor: SettingsInteractor
): ViewModel() {

    val currentTheme: Flow<SystemTheme> = settingsInteractor.currentTheme

    val dynamicColors: Flow<Boolean> = settingsInteractor.dynamicColors
}