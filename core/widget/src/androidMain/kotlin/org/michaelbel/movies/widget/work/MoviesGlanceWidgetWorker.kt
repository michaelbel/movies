package org.michaelbel.movies.widget.work

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.persistence.database.entity.mini.MovieDbMini
import org.michaelbel.movies.widget.MoviesGlanceWidget
import org.michaelbel.movies.widget.ktx.mapToMovieData
import org.michaelbel.movies.widget.R
import java.time.Duration

internal class MoviesGlanceWidgetWorker(
    private val context: Context,
    workerParams: WorkerParameters,
    private val interactor: Interactor
): CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val glanceAppWidgetManager = GlanceAppWidgetManager(context)
        val glanceIds = glanceAppWidgetManager.getGlanceIds(MoviesGlanceWidget::class.java)
        return try {
            setWidgetState(glanceIds, MoviesWidgetState.Loading)
            val movies = interactor.moviesWidget().map(MovieDbMini::mapToMovieData)
            if (movies.isNotEmpty()) {
                setWidgetState(glanceIds, MoviesWidgetState.Content(movies))
                Result.success()
            } else {
                setWidgetState(glanceIds, MoviesWidgetState.Failure(context.getString(R.string.appwidget_failure_empty)))
                Result.failure()
            }
        } catch (e: Exception) {
            setWidgetState(glanceIds, MoviesWidgetState.Failure(e.message.orEmpty()))
            if (runAttemptCount < 10) Result.retry() else Result.failure()
        }
    }

    private suspend fun setWidgetState(glanceIds: List<GlanceId>, newState: MoviesWidgetState) {
        glanceIds.forEach { glanceId ->
            updateAppWidgetState(
                context = context,
                definition = MoviesGlanceStateDefinition,
                glanceId = glanceId,
                updateState = { newState }
            )
        }
        MoviesGlanceWidget().updateAll(context)
    }

    companion object {
        private val uniqueWorkName = MoviesGlanceWidgetWorker::class.java.simpleName

        fun enqueue(context: Context, force: Boolean = false) {
            val requestBuilder = PeriodicWorkRequestBuilder<MoviesGlanceWidgetWorker>(Duration.ofMinutes(30))
            val workPolicy = if (force) ExistingPeriodicWorkPolicy.UPDATE else ExistingPeriodicWorkPolicy.KEEP
            WorkManager.getInstance(context).enqueueUniquePeriodicWork(uniqueWorkName, workPolicy, requestBuilder.build())
        }

        fun cancel(context: Context) {
            WorkManager.getInstance(context).cancelUniqueWork(uniqueWorkName)
        }
    }
}