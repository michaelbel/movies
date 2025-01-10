package org.michaelbel.movies.interactor.impl

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.michaelbel.movies.interactor.SearchInteractor

internal class SearchInteractorImpl: SearchInteractor {

    private val isActiveMutableFlow: MutableStateFlow<Boolean> = MutableStateFlow(true)

    override val isSearchActive: StateFlow<Boolean>
        get() = isActiveMutableFlow.asStateFlow()

    override fun setSearchActive(
        value: Boolean
    ) {
        isActiveMutableFlow.value = value
    }
}