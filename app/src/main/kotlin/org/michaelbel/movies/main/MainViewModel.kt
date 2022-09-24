package org.michaelbel.movies.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.domain.SettingsRepository

@HiltViewModel
class MainViewModel @Inject constructor(
    settingsRepository: SettingsRepository
): ViewModel() {

    val currentTheme: Flow<Int> = settingsRepository.currentTheme
}