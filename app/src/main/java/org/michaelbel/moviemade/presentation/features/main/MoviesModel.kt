package org.michaelbel.moviemade.presentation.features.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import org.michaelbel.data.Movie
import org.michaelbel.data.remote.model.MoviesResponse.Companion.ASC
import org.michaelbel.domain.MoviesRepository
import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY
import org.michaelbel.moviemade.core.errors.EmptyViewMode
import org.michaelbel.moviemade.core.net.NetworkUtil
import retrofit2.HttpException
import java.util.*

class MoviesModel(val repository: MoviesRepository): ViewModel() {

    private val disposable = CompositeDisposable()
    private var page: Int = 0

    var loading = MutableLiveData<Boolean>()
    var content = MutableLiveData<List<Movie>>()
    var error = MutableLiveData<Int>()

    fun movies(movieId: Int = 0, list: String) {
        page = 1
        disposable.add(repository.movies(movieId, list, TMDB_API_KEY, Locale.getDefault().language, page)
                .doOnSubscribe { loading.postValue(true) }
                .doOnTerminate { loading.postValue(false) }
                .subscribe({
                    if (it.isEmpty()) {
                        error.postValue(EmptyViewMode.MODE_NO_MOVIES)
                        return@subscribe
                    }
                    content.postValue(it)
                }, { error.postValue(EmptyViewMode.MODE_NO_MOVIES) }))
    }

    fun moviesNext(movieId: Int = 0, list: String) {
        page++
        disposable.add(repository.movies(movieId, list, TMDB_API_KEY, Locale.getDefault().language, page)
                .subscribe { content.postValue(it) })
    }

    fun movies(keywordId: Int) {
        page = 1
        disposable.add(repository.movies(keywordId, TMDB_API_KEY, Locale.getDefault().language, true, page)
                .doOnSubscribe { loading.postValue(true) }
                .doOnTerminate { loading.postValue(false) }
                .switchIfEmpty { error.postValue(EmptyViewMode.MODE_NO_MOVIES) }
                .subscribe({ content.postValue(it) }, { error.postValue(EmptyViewMode.MODE_NO_MOVIES) }))
    }

    fun moviesNext(keywordId: Int) {
        page++
        disposable.add(repository.movies(keywordId, TMDB_API_KEY, Locale.getDefault().language, true, page)
                .subscribe { content.postValue(it) })
    }

    fun moviesWatchlist(accountId: Int, sessionId: String) {
        page = 1
        disposable.add(repository.moviesWatchlist(accountId, TMDB_API_KEY, sessionId, Locale.getDefault().language, ASC, page)
                .doOnSubscribe { loading.postValue(true) }
                .doOnTerminate { loading.postValue(false) }
                .subscribe({
                    if (it.isEmpty()) {
                        error.postValue(1)
                        return@subscribe
                    }
                    content.postValue(it)
                }, { error.postValue((it as HttpException).code()) })
        )
    }

    fun moviesWatchlistNext(accountId: Int, sessionId: String) {
        page++
        disposable.add(repository.moviesWatchlist(accountId, TMDB_API_KEY, sessionId, Locale.getDefault().language, ASC, page)
                .subscribe { content.postValue(it) })
    }

    fun moviesFavorite(accountId: Int, sessionId: String) {
        page = 1
        disposable.add(repository.moviesFavorite(accountId, TMDB_API_KEY, sessionId, Locale.getDefault().language, ASC, page)
                .doOnSubscribe { loading.postValue(true) }
                .doOnTerminate { loading.postValue(false) }
                .subscribe({
                    if (it.isEmpty()) {
                        error.postValue(1)
                        return@subscribe
                    }
                    content.postValue(it)
                }, { error.postValue((it as HttpException).code())})
        )
    }

    fun moviesFavoriteNext(accountId: Int, sessionId: String) {
        page++
        disposable.add(repository.moviesFavorite(accountId, TMDB_API_KEY, sessionId, Locale.getDefault().language, ASC, page)
                .subscribe { content.postValue(it) })
    }

    fun searchMovies(query: String) {
        page = 1
        disposable.add(repository.searchMovies(TMDB_API_KEY, Locale.getDefault().language, query, page, true)
                .doOnSubscribe { loading.postValue(true) }
                .doOnTerminate { loading.postValue(false) }
                .subscribe({
                    if (it.isEmpty()) {
                        error.postValue(EmptyViewMode.MODE_NO_RESULTS)
                        return@subscribe
                    }
                    content.postValue(it)
                }, { error.postValue(EmptyViewMode.MODE_NO_RESULTS) }))
    }

    fun searchMoviesNext(query: String) {
        if (!NetworkUtil.isNetworkConnected()) return

        page++
        disposable.add(repository.searchMovies(TMDB_API_KEY, Locale.getDefault().language, query, page, true)
                .subscribe { content.postValue(it) })
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}