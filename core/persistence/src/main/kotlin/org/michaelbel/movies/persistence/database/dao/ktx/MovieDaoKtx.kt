package org.michaelbel.movies.persistence.database.dao.ktx

import org.michaelbel.movies.persistence.database.dao.MovieDao

suspend fun MovieDao.isEmpty(movieList: String): Boolean {
    return moviesCount(movieList) == 0
}