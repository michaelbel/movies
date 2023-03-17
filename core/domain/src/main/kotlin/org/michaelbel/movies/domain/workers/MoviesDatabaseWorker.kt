package org.michaelbel.movies.domain.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import org.michaelbel.movies.common.coroutines.Dispatcher
import org.michaelbel.movies.common.coroutines.MoviesDispatchers
import org.michaelbel.movies.domain.data.dao.MovieDao
import org.michaelbel.movies.domain.data.dao.ktx.isEmpty
import org.michaelbel.movies.domain.data.entity.MovieDb
import org.michaelbel.movies.domain.ktx.mapToMovieDb
import org.michaelbel.movies.network.model.MovieResponse

@HiltWorker
class MoviesDatabaseWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    @Dispatcher(MoviesDispatchers.IO) private val dispatcher: CoroutineDispatcher,
    private val movieDao: MovieDao
): CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return withContext(dispatcher) {
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