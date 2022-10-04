package org.michaelbel.movies.feed

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
import org.michaelbel.movies.core.viewmodel.BaseViewModel
import org.michaelbel.movies.domain.interactor.MovieInteractor
import org.michaelbel.movies.entities.MovieData

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val movieInteractor: MovieInteractor,
    analytics: Analytics
): BaseViewModel() {

    val pagingItems: Flow<PagingData<MovieData>> = Pager(
        PagingConfig(
            pageSize = DEFAULT_PAGE_SIZE
        )
    ) {
        MoviesPagingSource(movieInteractor)
    }.flow
        .stateIn(this, SharingStarted.Lazily, PagingData.empty())
        .cachedIn(this)

    init {
        analytics.trackScreen(AnalyticsScreen.FEED)
    }

    private companion object {
        private const val DEFAULT_PAGE_SIZE = 10
    }
}