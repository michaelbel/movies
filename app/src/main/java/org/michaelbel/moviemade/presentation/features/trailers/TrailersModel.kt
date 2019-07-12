package org.michaelbel.moviemade.presentation.features.trailers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.michaelbel.core.adapter.ItemsManager
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.data.Video
import org.michaelbel.domain.TrailersRepository
import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY
import org.michaelbel.moviemade.core.state.EmptyState
import org.michaelbel.moviemade.core.state.EmptyState.MODE_NO_TRAILERS
import org.michaelbel.moviemade.presentation.listitem.TrailerListItem

class TrailersModel(val repository: TrailersRepository): ViewModel() {

    private val itemsManager = Manager()

    var loading = MutableLiveData<Boolean>()
    var content = MutableLiveData<ArrayList<ListItem>>()
    var error = MutableLiveData<Int>()
    var click = MutableLiveData<Video>()
    var longClick = MutableLiveData<Video>()

    fun trailers(movieId: Int) {
        loading.postValue(true)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = repository.trailers(movieId, TMDB_API_KEY)
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        val list = result.body()?.results
                        if (list.isNullOrEmpty()) {
                            error.postValue(MODE_NO_TRAILERS)
                        } else {
                            itemsManager.updateTrailers(list)
                            content.postValue(itemsManager.get())
                        }

                        loading.postValue(false)
                    } else {
                        error.postValue(MODE_NO_TRAILERS)
                        loading.postValue(false)
                    }
                }
            } catch (e: Throwable) {
                error.postValue(EmptyState.MODE_NO_CONNECTION)
                loading.postValue(false)
            }
        }
    }

    private inner class Manager: ItemsManager() {

        private val trailers = ArrayList<ListItem>()

        fun updateTrailers(items: List<Video>) {
            trailers.clear()

            items.forEach {
                val videoItem = TrailerListItem(it)
                videoItem.listener = object: TrailerListItem.Listener {
                    override fun onClick(video: Video) {
                        click.postValue(video)
                    }

                    override fun onLongClick(video: Video): Boolean {
                        longClick.postValue(video)
                        return true
                    }
                }
                trailers.add(videoItem)
            }
        }

        override fun getItems(): ArrayList<ListItem> {
            return trailers
        }
    }
}