package org.michaelbel.moviemade.presentation.features.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.michaelbel.core.adapter.ItemsManager
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.data.Movie
import org.michaelbel.data.Movie.Companion.ASC
import org.michaelbel.domain.MoviesRepository
import org.michaelbel.domain.live.LiveDataEvent
import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY
import org.michaelbel.moviemade.core.state.EmptyState.MODE_NO_CONNECTION
import org.michaelbel.moviemade.core.state.EmptyState.MODE_NO_MOVIES
import org.michaelbel.moviemade.core.state.EmptyState.MODE_NO_RESULTS
import org.michaelbel.moviemade.presentation.listitem.MovieListItem
import java.util.*

class MoviesModel(val repository: MoviesRepository): ViewModel() {

    private var page: Int = 0
    private val itemsManager = Manager()

    var loading = MutableLiveData<Boolean>()
    var content = MutableLiveData<ArrayList<ListItem>>()
    var error = MutableLiveData<LiveDataEvent<Int>>()
    var click = MutableLiveData<LiveDataEvent<Movie>>()
    var longClick = MutableLiveData<LiveDataEvent<Movie>>()

    fun movies(list: String) {
        page += 1
        loading.postValue(page == 1)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = repository.movies(list, TMDB_API_KEY, Locale.getDefault().language, page)
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        val movies: List<Movie>? = result.body()?.results
                        if (movies.isNullOrEmpty()) {
                            error.postValue(LiveDataEvent(MODE_NO_MOVIES))
                        } else {
                            itemsManager.updateMovies(movies, page == 1)
                            content.postValue(itemsManager.get())
                        }

                        loading.postValue(false)
                    } else {
                        if (page == 1) {
                            error.postValue(LiveDataEvent(MODE_NO_MOVIES))
                            loading.postValue(false)
                        }
                    }
                }
            } catch (e: Throwable) {
                if (page == 1) {
                    error.postValue(LiveDataEvent(MODE_NO_CONNECTION))
                    loading.postValue(false)
                }
            }
        }
    }

    fun moviesById(movieId: Int, list: String) {
        page += 1
        loading.postValue(page == 1)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = repository.moviesById(movieId, list, TMDB_API_KEY, Locale.getDefault().language, page)
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        val movies: List<Movie>? = result.body()?.results
                        if (movies.isNullOrEmpty()) {
                            error.postValue(LiveDataEvent(MODE_NO_MOVIES))
                        } else {
                            itemsManager.updateMovies(movies, page == 1)
                            content.postValue(itemsManager.get())
                        }
                        loading.postValue(false)
                    } else {
                        if (page == 1) {
                            error.postValue(LiveDataEvent(MODE_NO_MOVIES))
                            loading.postValue(false)
                        }
                    }
                }
            } catch (e: Throwable) {
                if (page == 1) {
                    error.postValue(LiveDataEvent(MODE_NO_CONNECTION))
                    loading.postValue(false)
                }
            }
        }
    }

    fun moviesByKeyword(keywordId: Int) {
        page += 1
        loading.postValue(page == 1)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = repository.moviesByKeyword(keywordId, TMDB_API_KEY, Locale.getDefault().language, true, page)
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        val movies: List<Movie>? = result.body()?.results
                        if (movies.isNullOrEmpty()) {
                            error.postValue(LiveDataEvent(MODE_NO_MOVIES))
                        } else {
                            itemsManager.updateMovies(movies, page == 1)
                            content.postValue(itemsManager.get())
                        }

                        loading.postValue(false)
                    } else {
                        if (page == 1) {
                            error.postValue(LiveDataEvent(MODE_NO_MOVIES))
                            loading.postValue(false)
                        }
                    }
                }
            } catch (e: Throwable) {
                if (page == 1) {
                    error.postValue(LiveDataEvent(MODE_NO_CONNECTION))
                    loading.postValue(false)
                }
            }
        }
    }

    fun searchMovies(query: String) {
        page += 1
        loading.postValue(page == 1)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = repository.moviesSearch(TMDB_API_KEY, Locale.getDefault().language, query, page, true)
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        val movies: List<Movie>? = result.body()?.results
                        if (movies.isNullOrEmpty()) {
                            error.postValue(LiveDataEvent(MODE_NO_RESULTS))
                            page = 0
                        } else {
                            itemsManager.updateMovies(movies, page == 1)
                            content.postValue(itemsManager.get())
                        }

                        loading.postValue(false)
                    } else {
                        if (page == 1) {
                            error.postValue(LiveDataEvent(MODE_NO_RESULTS))
                            loading.postValue(false)
                            page = 0
                        }
                    }
                }
            } catch (e: Throwable) {
                if (page == 1) {
                    error.postValue(LiveDataEvent(MODE_NO_CONNECTION))
                    loading.postValue(false)
                    page = 0
                }
            }
        }
    }

    fun moviesWatchlist(accountId: Int, sessionId: String) {
        page += 1
        loading.postValue(page == 1)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = repository.moviesWatchlist(accountId, TMDB_API_KEY, sessionId, Locale.getDefault().language, ASC, page)
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        val movies: List<Movie>? = result.body()?.results
                        if (movies.isNullOrEmpty()) {
                            error.postValue(LiveDataEvent(MODE_NO_MOVIES))
                        } else {
                            itemsManager.updateMovies(movies, page == 1)
                            content.postValue(itemsManager.get())
                        }

                        loading.postValue(false)
                    } else {
                        if (page == 1) {
                            error.postValue(LiveDataEvent(MODE_NO_MOVIES))
                            loading.postValue(false)
                        }
                    }
                }
            } catch (e: Throwable) {
                if (page == 1) {
                    error.postValue(LiveDataEvent(MODE_NO_CONNECTION))
                    loading.postValue(false)
                }
            }
        }
    }

    fun moviesFavorite(accountId: Int, sessionId: String) {
        page += 1
        loading.postValue(page == 1)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = repository.moviesFavorite(accountId, TMDB_API_KEY, sessionId, Locale.getDefault().language, ASC, page)
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        val movies: List<Movie>? = result.body()?.results
                        if (movies.isNullOrEmpty()) {
                            error.postValue(LiveDataEvent(MODE_NO_MOVIES))
                        } else {
                            itemsManager.updateMovies(movies, page == 1)
                            content.postValue(itemsManager.get())
                        }

                        loading.postValue(false)
                    } else {
                        if (page == 1) {
                            error.postValue(LiveDataEvent(MODE_NO_MOVIES))
                            loading.postValue(false)
                        }
                    }
                }
            } catch (e: Throwable) {
                if (page == 1) {
                    error.postValue(LiveDataEvent(MODE_NO_CONNECTION))
                    loading.postValue(false)
                }
            }
        }
    }

    private inner class Manager: ItemsManager() {

        private val movies = ArrayList<ListItem>()

        fun updateMovies(items: List<Movie>, update: Boolean = true) {
            if (update) {
                movies.clear()
            }

            items.forEach {
                val movieItem = MovieListItem(it)
                movieItem.listener = object: MovieListItem.Listener {
                    override fun onClick(movie: Movie) {
                        click.postValue(LiveDataEvent(movie))
                    }

                    override fun onLongClick(movie: Movie): Boolean {
                        longClick.postValue(LiveDataEvent(movie))
                        return true
                    }
                }
                movies.add(movieItem)
            }
        }

        override fun getItems(): ArrayList<ListItem> = movies
    }
}