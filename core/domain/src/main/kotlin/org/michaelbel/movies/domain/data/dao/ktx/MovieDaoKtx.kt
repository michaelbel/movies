package org.michaelbel.movies.domain.data.dao.ktx

import org.michaelbel.movies.domain.data.dao.MovieDao

internal suspend fun MovieDao.isEmpty(movieList: String): Boolean {
    return moviesCount(movieList) == 0
}