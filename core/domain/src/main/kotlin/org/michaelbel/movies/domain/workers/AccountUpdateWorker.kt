package org.michaelbel.movies.domain.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import org.michaelbel.movies.common.ktx.isTimePasses
import org.michaelbel.movies.entities.isTmdbApiKeyEmpty
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.persistence.datastore.MoviesPreferences
import java.util.concurrent.TimeUnit

@HiltWorker
class AccountUpdateWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val interactor: Interactor,
    private val preferences: MoviesPreferences
): CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val accountId: Int? = preferences.getAccountId()
            if (isTmdbApiKeyEmpty || accountId == null) {
                return Result.success()
            }

            val expireTime: Long = preferences.getAccountExpireTime() ?: 0L
            val currentTime: Long = System.currentTimeMillis()
            if (isTimePasses(ONE_DAY_MILLS, expireTime, currentTime)) {
                interactor.accountDetails()
            }
            Result.success()
        } catch (ignored: Exception) {
            Result.failure()
        }
    }

    private companion object {
        private val ONE_DAY_MILLS: Long = TimeUnit.DAYS.toMillis(1)
    }
}