package org.michaelbel.domain

import org.michaelbel.data.Movie
import org.michaelbel.data.local.dao.MoviesDao
import org.michaelbel.data.remote.Api
import org.michaelbel.data.remote.model.AccountStates
import org.michaelbel.data.remote.model.Fave
import org.michaelbel.data.remote.model.Mark
import org.michaelbel.data.remote.model.Watch
import org.michaelbel.data.remote.model.base.Result
import retrofit2.Response

class MoviesRepository(private val api: Api, private val dao: MoviesDao) {

    suspend fun movies(list: String, apiKey: String, language: String, page: Int): Response<Result<Movie>> {
        return api.movies(list, apiKey, language, page).await()
    }

    suspend fun moviesById(movieId: Int, list: String, apiKey: String, language: String, page: Int): Response<Result<Movie>> {
        return api.moviesById(movieId, list, apiKey, language, page).await()
    }

    suspend fun moviesByKeyword(keywordId: Int, apiKey: String, language: String, adult: Boolean, page: Int): Response<Result<Movie>>  {
        return api.moviesByKeyword(keywordId, apiKey, language, adult, page).await()
    }

    suspend fun moviesSearch(apiKey: String, language: String, query: String, page: Int, adult: Boolean): Response<Result<Movie>> {
        return api.searchMovies(apiKey, language, query, page, adult, "").await()
    }

    suspend fun moviesWatchlist(accountId: Int, apiKey: String, sessionId: String, language: String, sort: String, page: Int): Response<Result<Movie>> {
        return api.moviesWatchlist(accountId, apiKey, sessionId, language, sort, page).await()
    }

    suspend fun moviesFavorite(accountId: Int, apiKey: String, sessionId: String, language: String, sort: String, page: Int): Response<Result<Movie>> {
        return api.moviesFavorite(accountId, apiKey, sessionId, language, sort, page).await()
    }

    suspend fun movie(movieId: Int, apiKey: String, language: String, addToResponse: String): Response<Movie> {
        return api.movie(movieId, apiKey, language, addToResponse).await()
    }

    suspend fun markFavorite(contentType: String, accountId: Int, apiKey: String, sessionId: String, mediaId: Int, favorite: Boolean): Response<Mark> {
        return api.markAsFavorite(contentType, accountId, apiKey, sessionId, Fave(Movie.MOVIE, mediaId, favorite)).await()
    }

    suspend fun addWatchlist(contentType: String, accountId: Int, sessionId: String, apiKey: String, mediaId: Int, watchlist: Boolean): Response<Mark> {
        return api.addToWatchlist(contentType, accountId, apiKey, sessionId, Watch(Movie.MOVIE, mediaId, watchlist)).await()
    }

    suspend fun accountStates(movieId: Int, apiKey: String, sessionId: String): Response<AccountStates> {
        return api.accountStates(movieId, apiKey, sessionId, "").await()
    }
}