package org.michaelbel.moviemade.app.features.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.michaelbel.moviemade.BuildConfig
import org.michaelbel.moviemade.data.Api
import org.michaelbel.moviemade.data.model.Movie
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val api: Api
): ViewModel() {

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

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

    fun openDetailsScreen(movieId: Int) {
        viewModelScope.launch {
            /*navigationFlow.navigate {
                fromHomeToDetails(movieId)
            }*/
        }
    }

    private companion object {
        private const val DEFAULT_PAGE_SIZE = 10
    }
}