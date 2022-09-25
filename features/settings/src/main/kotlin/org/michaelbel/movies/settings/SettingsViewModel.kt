package org.michaelbel.movies.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.michaelbel.movies.domain.repository.SettingsRepository
import org.michaelbel.movies.ui.SystemTheme

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
): ViewModel() {

    val themes: List<SystemTheme> = listOf(
        SystemTheme.NightNo,
        SystemTheme.NightYes,
        SystemTheme.FollowSystem
    )
    val currentTheme: Flow<SystemTheme> = settingsRepository.currentTheme

    fun selectTheme(systemTheme: SystemTheme) = viewModelScope.launch {
        settingsRepository.selectTheme(systemTheme)
    }
}