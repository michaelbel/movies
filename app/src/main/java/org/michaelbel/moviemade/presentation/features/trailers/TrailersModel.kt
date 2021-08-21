package org.michaelbel.moviemade.presentation.features.trailers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.michaelbel.core.adapter.ItemsManager
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.data.remote.model.Video
import org.michaelbel.domain.TrailersRepository
import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY
import org.michaelbel.moviemade.core.state.EmptyState
import org.michaelbel.moviemade.core.state.EmptyState.MODE_NO_TRAILERS
import org.michaelbel.moviemade.presentation.listitem.TrailerListItem
import javax.inject.Inject

@HiltViewModel
class TrailersModel @Inject constructor(
    val repository: TrailersRepository
): ViewModel() {

    private val itemsManager = Manager()

    var loading = MutableSharedFlow<Boolean>()
    var content = MutableSharedFlow<List<ListItem>>()
    var error = MutableSharedFlow<Int>()
    var click = MutableSharedFlow<Video>()
    var longClick = MutableSharedFlow<Video>()

    fun trailers(movieId: Long) {
        viewModelScope.launch {
            loading.emit(true)

            try {
                val result = repository.trailers(movieId, TMDB_API_KEY)
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        val list = result.body()?.results
                        if (list.isNullOrEmpty()) {
                            error.emit(MODE_NO_TRAILERS)
                        } else {
                            itemsManager.updateTrailers(list)
                            content.emit(itemsManager.get())
                        }

                        loading.emit(false)
                    } else {
                        error.emit(MODE_NO_TRAILERS)
                        loading.emit(false)
                    }
                }
            } catch (e: Throwable) {
                error.emit(EmptyState.MODE_NO_CONNECTION)
                loading.emit(false)
            }
        }
    }

    private inner class Manager: ItemsManager() {

        private val trailers = mutableListOf<ListItem>()

        fun updateTrailers(items: List<Video>) {
            trailers.clear()

            items.forEach {
                val videoItem = TrailerListItem(it)
                videoItem.listener = object: TrailerListItem.Listener {
                    override fun onClick(video: Video) {
                        viewModelScope.launch { click.emit(video) }
                    }

                    override fun onLongClick(video: Video): Boolean {
                        viewModelScope.launch { longClick.emit(video) }
                        return true
                    }
                }
                trailers.add(videoItem)
            }
        }

        override fun getItems(): List<ListItem> {
            return trailers
        }
    }
}