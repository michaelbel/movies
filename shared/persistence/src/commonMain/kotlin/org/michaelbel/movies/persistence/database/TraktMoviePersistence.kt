package org.michaelbel.movies.persistence.database

import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.entity.TraktMovieDb
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey

class TraktMoviePersistence(
    private val traktDatabase: TraktDatabase
) {

    fun pagingSource(traktList: PagingKey): PagingSource<Int, TraktMovieDb> {
        return traktDatabase.traktMovieDao.pagingSource(traktList)
    }

    fun movieFlow(traktList: PagingKey, traktId: MovieId): Flow<TraktMovieDb?> {
        return traktDatabase.traktMovieDao.movieFlow(traktList, traktId)
    }

    fun moviesFlow(traktList: PagingKey): Flow<List<TraktMovieDb>> {
        return traktDatabase.traktMovieDao.moviesFlow(traktList)
    }

    suspend fun movieById(traktList: PagingKey, traktId: MovieId): TraktMovieDb? {
        return traktDatabase.traktMovieDao.movieById(traktList, traktId)
    }

    suspend fun upsert(movie: TraktMovieDb) {
        traktDatabase.traktMovieDao.upsert(movie)
    }

    suspend fun upsert(movies: List<TraktMovieDb>) {
        traktDatabase.traktMovieDao.upsert(movies)
    }

    suspend fun removeMovie(traktList: PagingKey, traktId: MovieId) {
        traktDatabase.traktMovieDao.removeMovie(traktList, traktId)
    }

    suspend fun removeMovies(traktList: PagingKey) {
        traktDatabase.traktMovieDao.removeMovies(traktList)
    }

    suspend fun maxPosition(traktList: PagingKey): Int {
        return traktDatabase.traktMovieDao.maxPosition(traktList)
    }

    suspend fun isEmpty(traktList: PagingKey): Boolean {
        return traktDatabase.traktMovieDao.isEmpty(traktList)
    }
}
