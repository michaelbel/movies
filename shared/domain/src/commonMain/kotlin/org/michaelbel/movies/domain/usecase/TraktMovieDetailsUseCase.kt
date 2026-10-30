package org.michaelbel.movies.domain.usecase

import kotlinx.coroutines.CancellationException
import org.michaelbel.movies.common.dispatchers.SharedDispatchers
import org.michaelbel.movies.domain.usecase.MovieDetailsUseCase.MovieDetailsException
import org.michaelbel.movies.domain.usecase.TraktMovieDetailsUseCase.Params
import org.michaelbel.movies.network.TraktNetworkService
import org.michaelbel.movies.persistence.database.TraktMoviePersistence
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.persistence.database.ktx.moviePojo
import org.michaelbel.movies.persistence.database.ktx.traktMovieDb
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey

class TraktMovieDetailsUseCase(
    private val traktNetworkService: TraktNetworkService,
    private val traktMoviePersistence: TraktMoviePersistence,
    dispatchers: SharedDispatchers
): UseCase<Params, MoviePojo>(dispatchers.io) {

    override suspend fun execute(params: Params): MoviePojo {
        return try {
            traktMoviePersistence.movieById(params.traktList, params.traktId)?.moviePojo
                ?: traktNetworkService.movie(params.traktId).traktMovieDb(
                    traktList = params.traktList,
                    page = 0,
                    position = 0
                ).moviePojo
        } catch (exception: CancellationException) {
            throw exception
        } catch (exception: Exception) {
            throw MovieDetailsException(exception.message.orEmpty())
        }
    }

    data class Params(
        val traktList: PagingKey,
        val traktId: MovieId
    )
}
