package org.michaelbel.moviemade.presentation.features.main

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.michaelbel.core.adapter.ItemsManager
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.data.remote.model.Keyword
import org.michaelbel.data.remote.model.Movie
import org.michaelbel.domain.MoviesRepository
import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY
import org.michaelbel.moviemade.core.local.SharedPrefs
import org.michaelbel.moviemade.core.state.EmptyState
import org.michaelbel.moviemade.presentation.listitem.MovieListItem
import java.util.*

class MainViewModel @AssistedInject constructor(
    @Assisted val list: String?,
    @Assisted val movie: Movie?,
    @Assisted val keyword: Keyword?,
    @Assisted val accountId: Long?,
    private val repository: MoviesRepository,
    private val preferences: SharedPreferences
): ViewModel() {

    private var page: Int = 0
    private val itemsManager = Manager()

    val loading = MutableSharedFlow<Boolean>()
    val content = MutableSharedFlow<List<ListItem>>()
    val error = MutableSharedFlow<Int>()
    val click = MutableSharedFlow<Movie>()
    val longClick = MutableSharedFlow<Movie>()

    private val sessionId: String
        get() = preferences.getString(SharedPrefs.KEY_SESSION_ID, "") ?: ""

    init {
        load()
    }

    fun load() {
        when {
            list != null && list == Movie.SIMILAR -> movies()
            list != null && list == Movie.RECOMMENDATIONS -> movies()
            list != null && list == Movie.FAVORITE -> moviesFavorite()
            list != null && list == Movie.WATCHLIST -> moviesWatchlist()
            keyword != null -> moviesByKeyword()
            movie != null -> moviesById()
            else -> movies()
        }
    }

    fun movies() = viewModelScope.launch {
        page += 1
        loading.emit(page == 1)

        runCatching {
            val result = repository.movies(list!!, TMDB_API_KEY, Locale.getDefault().language, page)
            if (result.isSuccessful) {
                val movies: List<Movie>? = result.body()?.results
                if (movies.isNullOrEmpty()) {
                    error.emit(EmptyState.MODE_NO_MOVIES)
                } else {
                    itemsManager.updateMovies(movies, page == 1)
                    content.emit(itemsManager.get())
                }

                loading.emit(false)
            } else {
                if (page == 1) {
                    error.emit(EmptyState.MODE_NO_MOVIES)
                    loading.emit(false)
                }
            }
        }.onFailure {
            if (page == 1) {
                error.emit(EmptyState.MODE_NO_CONNECTION)
                loading.emit(false)
            }
        }

        viewModelScope.launch {
            try {
                val result = repository.movies(list!!, TMDB_API_KEY, Locale.getDefault().language, page)
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        val movies: List<Movie>? = result.body()?.results
                        if (movies.isNullOrEmpty()) {
                            error.emit(EmptyState.MODE_NO_MOVIES)
                        } else {
                            itemsManager.updateMovies(movies, page == 1)
                            content.emit(itemsManager.get())
                        }

                        loading.emit(false)
                    } else {
                        if (page == 1) {
                            error.emit(EmptyState.MODE_NO_MOVIES)
                            loading.emit(false)
                        }
                    }
                }
            } catch (e: Throwable) {
                if (page == 1) {
                    error.emit(EmptyState.MODE_NO_CONNECTION)
                    loading.emit(false)
                }
            }
        }
    }

    private fun moviesById() {
        viewModelScope.launch {
            page += 1
            loading.emit(page == 1)

            try {
                val result = repository.moviesById(movie!!.id.toLong(), list!!, TMDB_API_KEY, Locale.getDefault().language, page)
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        val movies: List<Movie>? = result.body()?.results
                        if (movies.isNullOrEmpty()) {
                            error.emit(EmptyState.MODE_NO_MOVIES)
                        } else {
                            itemsManager.updateMovies(movies, page == 1)
                            content.emit(itemsManager.get())
                        }
                        loading.emit(false)
                    } else {
                        if (page == 1) {
                            error.emit(EmptyState.MODE_NO_MOVIES)
                            loading.emit(false)
                        }
                    }
                }
            } catch (e: Throwable) {
                if (page == 1) {
                    error.emit(EmptyState.MODE_NO_CONNECTION)
                    loading.emit(false)
                }
            }
        }
    }

    private fun moviesByKeyword() {
        viewModelScope.launch {
            page += 1
            loading.emit(page == 1)

            try {
                val result = repository.moviesByKeyword(keyword!!.id, TMDB_API_KEY, Locale.getDefault().language, true, page)
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        val movies: List<Movie>? = result.body()?.results
                        if (movies.isNullOrEmpty()) {
                            error.emit(EmptyState.MODE_NO_MOVIES)
                        } else {
                            itemsManager.updateMovies(movies, page == 1)
                            content.emit(itemsManager.get())
                        }

                        loading.emit(false)
                    } else {
                        if (page == 1) {
                            error.emit(EmptyState.MODE_NO_MOVIES)
                            loading.emit(false)
                        }
                    }
                }
            } catch (e: Throwable) {
                if (page == 1) {
                    error.emit(EmptyState.MODE_NO_CONNECTION)
                    loading.emit(false)
                }
            }
        }
    }

    fun searchMovies(query: String) {
        viewModelScope.launch {
            page += 1
            loading.emit(page == 1)

            try {
                val result = repository.moviesSearch(TMDB_API_KEY, Locale.getDefault().language, query, page, true)
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        val movies: List<Movie>? = result.body()?.results
                        if (movies.isNullOrEmpty()) {
                            error.emit(EmptyState.MODE_NO_RESULTS)
                            page = 0
                        } else {
                            itemsManager.updateMovies(movies, page == 1)
                            content.emit(itemsManager.get())
                        }

                        loading.emit(false)
                    } else {
                        if (page == 1) {
                            error.emit(EmptyState.MODE_NO_RESULTS)
                            loading.emit(false)
                            page = 0
                        }
                    }
                }
            } catch (e: Throwable) {
                if (page == 1) {
                    error.emit(EmptyState.MODE_NO_CONNECTION)
                    loading.emit(false)
                    page = 0
                }
            }
        }
    }

    private fun moviesWatchlist() {
        viewModelScope.launch {
            page += 1
            loading.emit(page == 1)

            try {
                val result = repository.moviesWatchlist(accountId!!, TMDB_API_KEY, sessionId, Locale.getDefault().language, Movie.ASC, page)
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        val movies: List<Movie>? = result.body()?.results
                        if (movies.isNullOrEmpty()) {
                            error.emit(EmptyState.MODE_NO_MOVIES)
                        } else {
                            itemsManager.updateMovies(movies, page == 1)
                            content.emit(itemsManager.get())
                        }

                        loading.emit(false)
                    } else {
                        if (page == 1) {
                            error.emit(EmptyState.MODE_NO_MOVIES)
                            loading.emit(false)
                        }
                    }
                }
            } catch (e: Throwable) {
                if (page == 1) {
                    error.emit(EmptyState.MODE_NO_CONNECTION)
                    loading.emit(false)
                }
            }
        }
    }

    private fun moviesFavorite() {
        viewModelScope.launch {
            page += 1
            loading.emit(page == 1)

            try {
                val result = repository.moviesFavorite(accountId!!, TMDB_API_KEY, sessionId, Locale.getDefault().language, Movie.ASC, page)
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        val movies: List<Movie>? = result.body()?.results
                        if (movies.isNullOrEmpty()) {
                            error.emit(EmptyState.MODE_NO_MOVIES)
                        } else {
                            itemsManager.updateMovies(movies, page == 1)
                            content.emit(itemsManager.get())
                        }

                        loading.emit(false)
                    } else {
                        if (page == 1) {
                            error.emit(EmptyState.MODE_NO_MOVIES)
                            loading.emit(false)
                        }
                    }
                }
            } catch (e: Throwable) {
                if (page == 1) {
                    error.emit(EmptyState.MODE_NO_CONNECTION)
                    loading.emit(false)
                }
            }
        }
    }

    private inner class Manager: ItemsManager() {

        private val movies = mutableListOf<ListItem>()

        fun updateMovies(items: List<Movie>, update: Boolean = true) {
            if (update) {
                movies.clear()
            }

            items.forEach {
                val movieItem = MovieListItem(it)
                movieItem.listener = object: MovieListItem.Listener {
                    override fun onClick(movie: Movie) {
                        viewModelScope.launch {
                            click.emit(movie)
                        }
                    }

                    override fun onLongClick(movie: Movie): Boolean {
                        viewModelScope.launch {
                            longClick.emit(movie)
                        }
                        return true
                    }
                }
                movies.add(movieItem)
            }
        }

        override fun getItems(): List<ListItem> = movies
    }

    @AssistedFactory
    interface Factory {
        fun create(
            list: String?,
            keyword: Keyword? = null,
            movie: Movie? = null,
            accountId: Long? = null
        ): MainViewModel
    }
}