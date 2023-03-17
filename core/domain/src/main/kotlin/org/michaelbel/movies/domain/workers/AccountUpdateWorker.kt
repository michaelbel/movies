package org.michaelbel.movies.domain.workers

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.flow.first
import org.michaelbel.movies.common.ktx.isTimePasses
import org.michaelbel.movies.domain.interactor.account.AccountInteractor
import org.michaelbel.movies.domain.preferences.constants.PREFERENCE_ACCOUNT_EXPIRE_TIME_KEY
import org.michaelbel.movies.domain.preferences.constants.PREFERENCE_ACCOUNT_ID_KEY
import org.michaelbel.movies.entities.isTmdbApiKeyEmpty

@HiltWorker
class AccountUpdateWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val dataStore: DataStore<Preferences>,
    private val accountInteractor: AccountInteractor
): CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val accountId: Int? = dataStore.data.first()[PREFERENCE_ACCOUNT_ID_KEY]
            if (isTmdbApiKeyEmpty || accountId == null) {
                return Result.success()
            }

            val expireTime: Long = dataStore.data.first()[PREFERENCE_ACCOUNT_EXPIRE_TIME_KEY] ?: 0L
            val currentTime: Long = System.currentTimeMillis()
            if (isTimePasses(ONE_DAY_MILLS, expireTime, currentTime)) {
                accountInteractor.accountDetails()
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private companion object {
        private val ONE_DAY_MILLS: Long = TimeUnit.DAYS.toMillis(1)
    }
}