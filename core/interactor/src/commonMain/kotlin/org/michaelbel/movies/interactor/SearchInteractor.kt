package org.michaelbel.movies.interactor

import kotlinx.coroutines.flow.StateFlow

interface SearchInteractor {

    val isSearchActive: StateFlow<Boolean>

    fun setSearchActive(
        value: Boolean
    )
}