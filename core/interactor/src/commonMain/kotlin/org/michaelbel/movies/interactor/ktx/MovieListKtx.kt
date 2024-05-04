package org.michaelbel.movies.interactor.ktx

import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.network.config.isTmdbApiKeyEmpty
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo

val MovieList.nameOrLocalList: String
    get() = if (isTmdbApiKeyEmpty) MoviePojo.MOVIES_LOCAL_LIST else MovieList.name(this)