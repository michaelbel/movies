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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import org.michaelbel.movies.analytics.Analytics
import org.michaelbel.movies.analytics.model.AnalyticsScreen
import org.michaelbel.movies.domain.interactor.MovieInteractor

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val movieInteractor: MovieInteractor,
    analytics: Analytics
): ViewModel() {

    private val _isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val moviesStateFlow = Pager(PagingConfig(pageSize = DEFAULT_PAGE_SIZE)) {
        MoviesPagingSource(
            movieInteractor,
            "now_playing",
            _isRefreshing
        )
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