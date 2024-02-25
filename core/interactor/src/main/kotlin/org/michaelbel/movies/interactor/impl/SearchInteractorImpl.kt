package org.michaelbel.movies.interactor.impl

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.michaelbel.movies.interactor.SearchInteractor

@Singleton
internal class SearchInteractorImpl @Inject constructor(): SearchInteractor {

    private val isActiveMutableFlow: MutableStateFlow<Boolean> = MutableStateFlow(true)

    override val isSearchActive: StateFlow<Boolean>
        get() = isActiveMutableFlow.asStateFlow()

    override fun setSearchActive(value: Boolean) {
        isActiveMutableFlow.value = value
    }
}