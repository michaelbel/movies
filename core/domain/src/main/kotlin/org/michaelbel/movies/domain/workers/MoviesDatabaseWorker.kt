package org.michaelbel.movies.domain.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import org.michaelbel.movies.common.dispatchers.MoviesDispatchers
import org.michaelbel.movies.domain.ktx.mapToMovieDb
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.persistence.database.dao.MovieDao
import org.michaelbel.movies.persistence.database.dao.ktx.isEmpty
import org.michaelbel.movies.persistence.database.entity.MovieDb

@HiltWorker
class MoviesDatabaseWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val dispatchers: MoviesDispatchers,
    private val movieDao: MovieDao
): CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return withContext(dispatchers.io) {
            try {
                val filename: String? = inputData.getString(KEY_FILENAME)
                if (filename != null && movieDao.isEmpty(MovieDb.MOVIES_LOCAL_LIST)) {
                    applicationContext.assets.open(filename).use { inputStream ->
                        val format = Json { ignoreUnknownKeys = true }
                        val moviesJsonData: List<MovieResponse> = format.decodeFromStream(inputStream)
                        val moviesDb: List<MovieDb> = moviesJsonData.mapIndexed { index, movieResponse ->
                            movieResponse.mapToMovieDb(
                                movieList = MovieDb.MOVIES_LOCAL_LIST,
                                position = index.plus(1)
                            )
                        }
                        movieDao.insertAllMovies(moviesDb)
                    }
                }
                Result.success()
            } catch (e: Exception) {
                Result.failure()
            }
        }
    }

    companion object {
        const val KEY_FILENAME = "MOVIES_DATA_FILENAME"
    }
}