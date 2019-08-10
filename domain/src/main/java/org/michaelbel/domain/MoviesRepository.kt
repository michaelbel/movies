package org.michaelbel.domain

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.michaelbel.data.local.dao.MoviesDao
import org.michaelbel.data.local.model.MovieLocal
import org.michaelbel.data.remote.Api
import org.michaelbel.data.remote.model.*
import org.michaelbel.data.remote.model.base.Result
import retrofit2.Response

class MoviesRepository(private val api: Api, private val dao: MoviesDao) {

    //region Remote

    suspend fun movies(list: String, apiKey: String, language: String, page: Int): Response<Result<Movie>> {
        return api.movies(list, apiKey, language, page).await()
    }

    suspend fun moviesById(movieId: Long, list: String, apiKey: String, language: String, page: Int): Response<Result<Movie>> {
        return api.moviesById(movieId, list, apiKey, language, page).await()
    }

    suspend fun moviesByKeyword(keywordId: Long, apiKey: String, language: String, adult: Boolean, page: Int): Response<Result<Movie>>  {
        return api.moviesByKeyword(keywordId, apiKey, language, adult, page).await()
    }

    suspend fun moviesSearch(apiKey: String, language: String, query: String, page: Int, adult: Boolean): Response<Result<Movie>> {
        return api.searchMovies(apiKey, language, query, page, adult, "").await()
    }

    suspend fun moviesWatchlist(accountId: Long, apiKey: String, sessionId: String, language: String, sort: String, page: Int): Response<Result<Movie>> {
        return api.moviesWatchlist(accountId, apiKey, sessionId, language, sort, page).await()
    }

    suspend fun moviesFavorite(accountId: Long, apiKey: String, sessionId: String, language: String, sort: String, page: Int): Response<Result<Movie>> {
        return api.moviesFavorite(accountId, apiKey, sessionId, language, sort, page).await()
    }

    suspend fun movie(movieId: Long, apiKey: String, language: String, addToResponse: String): Response<Movie> {
        return api.movie(movieId, apiKey, language, addToResponse).await()
    }

    suspend fun markFavorite(contentType: String, accountId: Long, apiKey: String, sessionId: String, mediaId: Long, favorite: Boolean): Response<Mark> {
        return api.markAsFavorite(contentType, accountId, apiKey, sessionId, Fave(Movie.MOVIE, mediaId, favorite)).await()
    }

    suspend fun addWatchlist(contentType: String, accountId: Long, sessionId: String, apiKey: String, mediaId: Long, watchlist: Boolean): Response<Mark> {
        return api.addToWatchlist(contentType, accountId, apiKey, sessionId, Watch(Movie.MOVIE, mediaId, watchlist)).await()
    }

    suspend fun accountStates(movieId: Long, apiKey: String, sessionId: String): Response<AccountStates> {
        return api.accountStates(movieId, apiKey, sessionId, "").await()
    }

    //endregion

    // region Local

    suspend fun addAll(items: List<Movie>) {
        val movies = ArrayList<MovieLocal>()
        items.forEach {
            val movie = MovieLocal(id = it.id.toLong(), title = it.title)
            movies.add(movie)
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = dao.insert(movies)
                withContext(Dispatchers.Main) {
                    Log.e("1488", "Вставка произошла успешно!")
                }
            } catch (e: Exception) {
                Log.e("1488", "Exception: $e")
            }
        }

        dao.insert(movies)
    }

    //endregion
}