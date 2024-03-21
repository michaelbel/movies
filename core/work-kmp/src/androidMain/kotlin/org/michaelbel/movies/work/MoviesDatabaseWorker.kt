@file:OptIn(ExperimentalSerializationApi::class)
@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import org.michaelbel.movies.common.dispatchers.MoviesDispatchers
import org.michaelbel.movies.persistence.database.MoviePersistence
import org.michaelbel.movies.persistence.database.entity.MovieDb
import org.michaelbel.movies.persistence.database.ktx.movieDb

@HiltWorker
actual class MoviesDatabaseWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val dispatchers: MoviesDispatchers,
    private val moviePersistence: MoviePersistence
): CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return withContext(dispatchers.io) {
            try {
                val filename = inputData.getString(KEY_FILENAME)
                if (filename != null && moviePersistence.isEmpty(MovieDb.MOVIES_LOCAL_LIST)) {
                    applicationContext.assets.open(filename).use { inputStream ->
                        val format = Json { ignoreUnknownKeys = true }
                        val moviesJsonData: List<MovieResponse> = format.decodeFromStream(inputStream)
                        val moviesDb = moviesJsonData.mapIndexed { index, movieResponse ->
                            movieResponse.movieDb(
                                movieList = MovieDb.MOVIES_LOCAL_LIST,
                                position = index.plus(1)
                            )
                        }
                        moviePersistence.insertMovies(moviesDb)
                    }
                }
                Result.success()
            } catch (ignored: Exception) {
                Result.failure()
            }
        }
    }

    companion object {
        const val KEY_FILENAME = "MOVIES_DATA_FILENAME"
    }
}