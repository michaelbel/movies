package org.michaelbel.movies.feed

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import org.michaelbel.movies.analytics.Analytics
import org.michaelbel.movies.analytics.model.AnalyticsScreen
import org.michaelbel.movies.domain.interactor.MovieInteractor
import org.michaelbel.movies.entities.MovieData

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val movieInteractor: MovieInteractor,
    analytics: Analytics
): ViewModel() {

    val pagingItems: Flow<PagingData<MovieData>> = Pager(
        PagingConfig(
            pageSize = DEFAULT_PAGE_SIZE
        )
    ) {
        MoviesPagingSource(movieInteractor)
    }.flow
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
        .cachedIn(viewModelScope)

    var updateAvailableMessage: Boolean by mutableStateOf(false)

    init {
        analytics.trackScreen(AnalyticsScreen.FEED)
    }

    private companion object {
        private const val DEFAULT_PAGE_SIZE = 10
    }
}