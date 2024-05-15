package org.michaelbel.movies.repository.impl

import androidx.paging.PagingSource
import org.michaelbel.movies.persistence.database.MovieBlockingPersistence
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.persistence.database.typealiases.PagingKey
import org.michaelbel.movies.repository.MovieBlockingRepository

internal class MovieBlockingRepositoryImpl(
    private val movieBlockingPersistence: MovieBlockingPersistence
): MovieBlockingRepository {

    override fun moviesPagingSource(
        pagingKey: PagingKey
    ): PagingSource<Int, MoviePojo> {
        return movieBlockingPersistence.pagingSource(pagingKey)
    }
}