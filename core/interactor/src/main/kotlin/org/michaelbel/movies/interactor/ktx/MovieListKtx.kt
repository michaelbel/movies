package org.michaelbel.movies.interactor.ktx

import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.network.isTmdbApiKeyEmpty
import org.michaelbel.movies.persistence.database.entity.MovieDb

val MovieList.nameOrLocalList: String
    get() = if (isTmdbApiKeyEmpty) MovieDb.MOVIES_LOCAL_LIST else name