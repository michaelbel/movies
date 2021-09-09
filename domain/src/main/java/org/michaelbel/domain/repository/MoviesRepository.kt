package org.michaelbel.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.michaelbel.data.remote.Api
import org.michaelbel.data.remote.model.*
import org.michaelbel.domain.data.MoviesPagingSource
import retrofit2.Response
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val api: Api
) {

    fun moviesStream(
        list: String,
        apiKey: String,
        language: String
    ): Flow<PagingData<MovieResponse>> {
        return Pager(
            config = PagingConfig(pageSize = MOVIES_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { MoviesPagingSource(api, list, apiKey, language) }
        ).flow
    }

    suspend fun movies(
        list: String,
        apiKey: String,
        language: String,
        page: Int
    ): Response<Result<MovieResponse>> = api.movies(list, apiKey, language, page)

    suspend fun moviesById(
        movieId: Long,
        list: String,
        apiKey: String,
        language: String,
        page: Int
    ): Response<Result<Movie>> = api.moviesById(movieId, list, apiKey, language, page)

    suspend fun moviesByKeyword(
        keywordId: Long,
        apiKey: String,
        language: String,
        adult: Boolean,
        page: Int
    ): Response<Result<Movie>> = api.moviesByKeyword(keywordId, apiKey, language, adult, page)

    suspend fun moviesSearch(
        apiKey: String,
        language: String,
        query: String,
        page: Int,
        adult: Boolean
    ): Response<Result<Movie>> = api.searchMovies(apiKey, language, query, page, adult, "")

    suspend fun moviesWatchlist(
        accountId: Long,
        apiKey: String,
        sessionId: String,
        language: String,
        sort: String,
        page: Int
    ): Response<Result<Movie>> {
        return api.moviesWatchlist(accountId, apiKey, sessionId, language, sort, page)
    }

    suspend fun moviesFavorite(
        accountId: Long,
        apiKey: String,
        sessionId: String,
        language: String,
        sort: String,
        page: Int
    ): Response<Result<Movie>> {
        return api.moviesFavorite(accountId, apiKey, sessionId, language, sort, page)
    }

    suspend fun movie(
        movieId: Long,
        apiKey: String,
        language: String
    ): Response<Movie> = api.movie(movieId, apiKey, language)

    suspend fun movie(
        movieId: Long,
        apiKey: String,
        language: String,
        addToResponse: String
    ): Response<Movie> = api.movie(movieId, apiKey, language, addToResponse)

    suspend fun markFavorite(
        contentType: String,
        accountId: Long,
        apiKey: String,
        sessionId: String,
        mediaId: Long,
        favorite: Boolean
    ): Response<Mark> {
        return api.markAsFavorite(
            contentType,
            accountId,
            apiKey,
            sessionId,
            Fave(Movie.MOVIE, mediaId, favorite)
        )
    }

    suspend fun addWatchlist(
        contentType: String,
        accountId: Long,
        sessionId: String,
        apiKey: String,
        mediaId: Long,
        watchlist: Boolean
    ): Response<Mark> {
        return api.addToWatchlist(
            contentType,
            accountId,
            apiKey,
            sessionId,
            Watch(Movie.MOVIE, mediaId, watchlist)
        )
    }

    suspend fun accountStates(
        movieId: Long,
        apiKey: String,
        sessionId: String
    ): Response<AccountStates> = api.accountStates(movieId, apiKey, sessionId, "")

    companion object {
        const val MOVIES_PAGE_SIZE = 20
    }
}