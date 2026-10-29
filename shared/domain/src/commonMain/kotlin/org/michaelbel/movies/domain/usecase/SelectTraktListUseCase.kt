@file:Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")

package org.michaelbel.movies.domain.usecase

import org.michaelbel.movies.common.dispatchers.SharedDispatchers
import org.michaelbel.movies.common.list.TraktList
import org.michaelbel.movies.persistence.datastore.TraktPreferences

class SelectTraktListUseCase(
    private val preferences: TraktPreferences,
    dispatchers: SharedDispatchers
): UseCase<TraktList, Unit>(dispatchers.io) {

    override suspend fun execute(traktList: TraktList) {
        preferences.setValue(
            TraktPreferences.PreferenceKey.PreferenceTraktListKey,
            traktList.toString()
        )
    }
}
