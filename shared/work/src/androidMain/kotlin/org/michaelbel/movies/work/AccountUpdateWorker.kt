package org.michaelbel.movies.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import org.michaelbel.movies.common.ktx.isTimePasses
import org.michaelbel.movies.domain.usecase.AccountDetailsUseCase
import org.michaelbel.movies.domain.usecase.AccountExpireTimeUseCase
import org.michaelbel.movies.domain.usecase.AccountIdUseCase
import org.michaelbel.movies.network.config.isTmdbApiKeyEmpty
import org.michaelbel.movies.persistence.database.ktx.isEmpty
import java.util.concurrent.TimeUnit
import kotlin.time.Clock

class AccountUpdateWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val accountIdUseCase: AccountIdUseCase,
    private val accountExpireTimeUseCase: AccountExpireTimeUseCase,
    private val accountDetailsUseCase: AccountDetailsUseCase
): CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val accountId = accountIdUseCase(Unit).getOrThrow()
            if (isTmdbApiKeyEmpty || accountId.isEmpty) return Result.success()
            val accountExpireTime = accountExpireTimeUseCase(Unit).getOrThrow()
            val currentTime = Clock.System.now().toEpochMilliseconds()
            if (isTimePasses(ONE_DAY_MILLS, accountExpireTime, currentTime)) {
                accountDetailsUseCase(Unit).getOrThrow()
            }
            Result.success()
        } catch (_: Exception) {
            Result.failure()
        }
    }

    private companion object {
        private val ONE_DAY_MILLS = TimeUnit.DAYS.toMillis(1)
    }
}
