package org.michaelbel.movies.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.domain.repository.SettingsRepository
import org.michaelbel.movies.ui.SystemTheme

@HiltViewModel
class MainViewModel @Inject constructor(
    settingsRepository: SettingsRepository
): ViewModel() {

    val currentTheme: Flow<SystemTheme> = settingsRepository.currentTheme
}