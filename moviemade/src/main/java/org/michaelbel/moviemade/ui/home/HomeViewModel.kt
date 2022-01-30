package org.michaelbel.moviemade.ui.home

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
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.michaelbel.moviemade.BuildConfig
import org.michaelbel.moviemade.app.data.Api
import org.michaelbel.moviemade.app.data.model.Movie
import org.michaelbel.moviemade.app.playcore.InAppUpdate

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val api: Api,
    inAppUpdate: InAppUpdate
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

    var updateAvailableMessage: Boolean by mutableStateOf(false)

    init {
        inAppUpdate.onUpdateAvailableListener = {
            viewModelScope.launch {
                updateAvailableMessage = true
            }
        }
    }

    private companion object {
        private const val DEFAULT_PAGE_SIZE = 10
    }
}