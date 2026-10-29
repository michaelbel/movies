package org.michaelbel.movies.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.michaelbel.movies.common.dispatchers.SharedDispatchers
import org.michaelbel.movies.common.list.TraktList
import org.michaelbel.movies.persistence.datastore.TraktPreferences

class CurrentTraktListFlowUseCase(
    private val preferences: TraktPreferences,
    dispatchers: SharedDispatchers
): FlowUseCase<Unit, TraktList>(dispatchers.io) {

    override fun execute(params: Unit): Flow<TraktList> {
        return preferences.getValueFlow(TraktPreferences.PreferenceKey.PreferenceTraktListKey)
            .map { className -> TraktList.transform(className ?: TraktList.Trending().toString()) }
    }
}
