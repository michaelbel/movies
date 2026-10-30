package org.michaelbel.movies.persistence.database

import androidx.paging.PagingSource
import org.michaelbel.movies.persistence.database.entity.TraktMovieDb
import org.michaelbel.movies.persistence.database.typealiases.PagingKey

class TraktMoviePersistence(
    private val traktDatabase: TraktDatabase
) {

    fun pagingSource(traktList: PagingKey): PagingSource<Int, TraktMovieDb> {
        return traktDatabase.traktMovieDao.pagingSource(traktList)
    }

    suspend fun upsert(movies: List<TraktMovieDb>) {
        traktDatabase.traktMovieDao.upsert(movies)
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
