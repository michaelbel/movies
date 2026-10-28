package org.michaelbel.movies.interactor.impl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import org.michaelbel.movies.common.dispatchers.SharedDispatchers
import org.michaelbel.movies.common.exceptions.PageEmptyException
import org.michaelbel.movies.interactor.LocaleInteractor
import org.michaelbel.movies.interactor.MovieInteractor
import org.michaelbel.movies.interactor.remote.FeedMoviesRemoteMediator
import org.michaelbel.movies.interactor.remote.SearchMoviesRemoteMediator
import org.michaelbel.movies.network.ktx.isEmpty
import org.michaelbel.movies.network.ktx.nextPage
import org.michaelbel.movies.network.model.Movie
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.persistence.database.entity.mini.MovieDbMini
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.persistence.database.ktx.moviePojo
import org.michaelbel.movies.persistence.database.typealiases.Limit
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey
import org.michaelbel.movies.persistence.database.typealiases.Query
import org.michaelbel.movies.repository.MovieRepository
import org.michaelbel.movies.repository.PagingKeyRepository
import org.michaelbel.movies.repository.SearchRepository

@OptIn(ExperimentalPagingApi::class)
class MovieInteractorImpl(
    private val dispatchers: SharedDispatchers,
    private val localeInteractor: LocaleInteractor,
    private val movieRepository: MovieRepository,
    private val searchRepository: SearchRepository,
    private val pagingKeyRepository: PagingKeyRepository
): MovieInteractor {

    override fun favoriteMoviesPagingData(): Flow<PagingData<MoviePojo>> {
        return Pager(
            config = PagingConfig(
                pageSize = MovieResponse.DEFAULT_PAGE_SIZE,
                enablePlaceholders = true
            ),
            remoteMediator = FeedMoviesRemoteMediator(
                language = localeInteractor.language,
                movieRepository = movieRepository,
                pagingKeyRepository = pagingKeyRepository,
                pagingKey = Movie.FAVORITE
            ),
            pagingSourceFactory = { movieRepository.pagingSource(Movie.FAVORITE) }
        ).flow
    }

    override fun moviesPagingData(searchQuery: Query): Flow<PagingData<MoviePojo>> {
        return when (searchQuery.isEmpty()) {
            true -> flow { emit(PagingData.from(emptyList())) }
            false -> Pager(
                config = PagingConfig(
                    pageSize = MovieResponse.DEFAULT_PAGE_SIZE,
                    enablePlaceholders = true
                ),
                remoteMediator = SearchMoviesRemoteMediator(
                    language = localeInteractor.language,
                    movieRepository = movieRepository,
                    searchRepository = searchRepository,
                    pagingKeyRepository = pagingKeyRepository,
                    query = searchQuery
                ),
                pagingSourceFactory = { movieRepository.pagingSource(searchQuery) }
            ).flow
        }
    }

    override fun movieFlow(pagingKey: PagingKey, movieId: MovieId): Flow<MoviePojo?> {
        return movieRepository.movieFlow(pagingKey, movieId)
    }

    override fun moviesFlow(pagingKey: PagingKey, limit: Limit): Flow<List<MoviePojo>> {
        return movieRepository.moviesFlow(pagingKey, limit)
    }

    override suspend fun moviesWidget(): List<MovieDbMini> {
        return withContext(dispatchers.io) { movieRepository.moviesWidget(localeInteractor.language) }
    }

    override suspend fun movie(pagingKey: PagingKey, movieId: MovieId): MoviePojo {
        return withContext(dispatchers.io) { movieRepository.movie(pagingKey, movieId) }
    }

    override suspend fun movieDetails(pagingKey: PagingKey, movieId: MovieId): MoviePojo {
        return withContext(dispatchers.io) { movieRepository.movieDetails(pagingKey, localeInteractor.language, movieId) }
    }

    override suspend fun removeMovies(pagingKey: PagingKey) {
        return withContext(dispatchers.io) { movieRepository.removeMovies(pagingKey) }
    }

    override suspend fun removeMovie(pagingKey: PagingKey, movieId: MovieId) {
        return withContext(dispatchers.io) { movieRepository.removeMovie(pagingKey, movieId) }
    }

    override suspend fun insertMovie(pagingKey: PagingKey, movie: MoviePojo) {
        return withContext(dispatchers.io) { movieRepository.insertMovie(pagingKey, movie) }
    }

    override suspend fun updateFavorite(movieId: MovieId, favorite: Boolean) {
        return withContext(dispatchers.io) { movieRepository.updateFavorite(movieId, favorite) }
    }

    override suspend fun fetchAndInsertSearchMovies(query: Query) {
        return withContext(dispatchers.io) {
            if (query.isEmpty()) throw PageEmptyException()

            fetchAndStoreSearchMovies(query)
        }
    }

    override suspend fun moviesResult(pagingKey: PagingKey): List<MoviePojo> {
        return withContext(dispatchers.io) {
            val movieResult = movieRepository.moviesResult(pagingKey, localeInteractor.language, 1).results
            movieResult.mapIndexed { index, movieResponse -> movieResponse.moviePojo(pagingKey, index, 1) }
        }
    }

    private suspend fun fetchAndStoreSearchMovies(query: Query): List<MoviePojo> {
        val moviesResult = searchRepository.searchMoviesResult(query, localeInteractor.language, 1)
        movieRepository.removeMovies(query)
        pagingKeyRepository.removePagingKey(query)

        if (moviesResult.isEmpty) {
            throw PageEmptyException()
        }

        pagingKeyRepository.insertPagingKey(query, moviesResult.nextPage, moviesResult.totalPages)
        movieRepository.insertMovies(query, moviesResult.page, moviesResult.results)
        return movieRepository.moviesFlow(query, Int.MAX_VALUE).first()
    }
}
