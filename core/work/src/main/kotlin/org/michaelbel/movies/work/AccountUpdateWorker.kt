package org.michaelbel.movies.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.concurrent.TimeUnit
import org.michaelbel.movies.common.ktx.isTimePasses
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.network.isTmdbApiKeyEmpty

@HiltWorker
class AccountUpdateWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val interactor: Interactor
): CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val accountId = interactor.accountId()
            if (isTmdbApiKeyEmpty || accountId == 0) {
                return Result.success()
            }

            val expireTime = interactor.accountExpireTime() ?: 0L
            val currentTime = System.currentTimeMillis()
            if (isTimePasses(ONE_DAY_MILLS, expireTime, currentTime)) {
                interactor.accountDetails()
            }
            Result.success()
        } catch (ignored: Exception) {
            Result.failure()
        }
    }

    private companion object {
        private val ONE_DAY_MILLS = TimeUnit.DAYS.toMillis(1)
    }
}