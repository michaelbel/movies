package org.michaelbel.domain

import org.michaelbel.data.local.dao.MoviesDao
import org.michaelbel.data.local.model.MovieLocal
import org.michaelbel.data.remote.Api
import org.michaelbel.data.remote.model.AccountStates
import org.michaelbel.data.remote.model.Fave
import org.michaelbel.data.remote.model.Mark
import org.michaelbel.data.remote.model.Movie
import org.michaelbel.data.remote.model.Watch
import org.michaelbel.data.remote.model.base.Result
import retrofit2.Response

class MoviesRepository(private val api: Api, private val dao: MoviesDao) {

    //region Remote

    suspend fun movies(list: String, apiKey: String, language: String, page: Int): Response<Result<Movie>> {
        return api.movies(list, apiKey, language, page)
    }

    suspend fun moviesById(movieId: Long, list: String, apiKey: String, language: String, page: Int): Response<Result<Movie>> {
        return api.moviesById(movieId, list, apiKey, language, page)
    }

    suspend fun moviesByKeyword(keywordId: Long, apiKey: String, language: String, adult: Boolean, page: Int): Response<Result<Movie>>  {
        return api.moviesByKeyword(keywordId, apiKey, language, adult, page)
    }

    suspend fun moviesSearch(apiKey: String, language: String, query: String, page: Int, adult: Boolean): Response<Result<Movie>> {
        return api.searchMovies(apiKey, language, query, page, adult, "")
    }

    suspend fun moviesWatchlist(accountId: Long, apiKey: String, sessionId: String, language: String, sort: String, page: Int): Response<Result<Movie>> {
        return api.moviesWatchlist(accountId, apiKey, sessionId, language, sort, page)
    }

    suspend fun moviesFavorite(accountId: Long, apiKey: String, sessionId: String, language: String, sort: String, page: Int): Response<Result<Movie>> {
        return api.moviesFavorite(accountId, apiKey, sessionId, language, sort, page)
    }

    suspend fun movie(movieId: Long, apiKey: String, language: String, addToResponse: String): Response<Movie> {
        return api.movie(movieId, apiKey, language, addToResponse)
    }

    suspend fun markFavorite(contentType: String, accountId: Long, apiKey: String, sessionId: String, mediaId: Long, favorite: Boolean): Response<Mark> {
        return api.markAsFavorite(contentType, accountId, apiKey, sessionId, Fave(Movie.MOVIE, mediaId, favorite))
    }

    suspend fun addWatchlist(contentType: String, accountId: Long, sessionId: String, apiKey: String, mediaId: Long, watchlist: Boolean): Response<Mark> {
        return api.addToWatchlist(contentType, accountId, apiKey, sessionId, Watch(Movie.MOVIE, mediaId, watchlist))
    }

    suspend fun accountStates(movieId: Long, apiKey: String, sessionId: String): Response<AccountStates> {
        return api.accountStates(movieId, apiKey, sessionId, "")
    }

    //endregion

    // region Local

    suspend fun addAll(items: List<Movie>) {
        val movies = ArrayList<MovieLocal>()
        items.forEach {
            val movie = MovieLocal(id = it.id.toLong(), title = it.title)
            movies.add(movie)
        }
        dao.insert(movies)
    }

    //endregion
}