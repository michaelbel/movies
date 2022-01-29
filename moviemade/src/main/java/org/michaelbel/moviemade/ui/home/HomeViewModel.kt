package org.michaelbel.moviemade.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import org.michaelbel.moviemade.BuildConfig
import org.michaelbel.moviemade.app.data.Api
import org.michaelbel.moviemade.app.data.model.Movie

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val api: Api
): ViewModel() {

    private val _isRefreshing = MutableStateFlow(false)

    val moviesStateFlow = Pager(PagingConfig(pageSize = DEFAULT_PAGE_SIZE)) {
        MoviesPagingSource(
            api,
            Movie.NOW_PLAYING,
            BuildConfig.TMDB_API_KEY,
            Locale.getDefault().language,
            _isRefreshing
        )
    }.flow
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
        .cachedIn(viewModelScope)

    private companion object {
        private const val DEFAULT_PAGE_SIZE = 10
    }
}