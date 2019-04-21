package org.michaelbel.moviemade.presentation.features.trailers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.michaelbel.core.adapter.ItemsManager
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.data.Video
import org.michaelbel.domain.TrailersRepository
import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY
import org.michaelbel.moviemade.core.errors.EmptyViewMode
import org.michaelbel.moviemade.presentation.listitem.TrailerListItem
import java.util.*
import kotlin.collections.ArrayList

class TrailersModel(val repository: TrailersRepository): ViewModel() {

    private val disposable = CompositeDisposable()
    private val itemsManager = Manager()

    var loading = MutableLiveData<Boolean>()
    var content = MutableLiveData<ArrayList<ListItem>>()
    var error = MutableLiveData<Int>()
    var click = MutableLiveData<Video>()
    var longClick = MutableLiveData<Video>()

    fun trailers(movieId: Int) {
        disposable.add(repository.trailers(movieId, TMDB_API_KEY, Locale.getDefault().language)
                .doOnSubscribe { loading.postValue(true) }
                .doOnTerminate { loading.postValue(false) }
                .flatMap {
                    if (it.isEmpty()) {
                        error.postValue(EmptyViewMode.MODE_NO_TRAILERS)
                    } else {
                        itemsManager.updateTrailers(it)
                    }
                    Observable.just(true)
                }
                .subscribe({
                    content.postValue(itemsManager.get())
                }, { error.postValue(EmptyViewMode.MODE_NO_TRAILERS) }))
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

    private inner class Manager: ItemsManager() {

        private val trailers = ArrayList<ListItem>()

        fun updateTrailers(items: List<Video>) {
            trailers.clear()
            for (video in items) {
                val videoItem = TrailerListItem(video)
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