package org.michaelbel.movies.domain.interactor.account.impl

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.michaelbel.movies.common.dispatchers.MoviesDispatchers
import org.michaelbel.movies.domain.usecase.DelayUseCase
import org.michaelbel.movies.interactor.AccountInteractor
import org.michaelbel.movies.persistence.database.entity.AccountDb
import org.michaelbel.movies.repository.AccountRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class AccountInteractorImpl @Inject constructor(
    private val dispatchers: MoviesDispatchers,
    private val accountRepository: AccountRepository,
    private val delayUseCase: DelayUseCase
): AccountInteractor {

    override val account: Flow<AccountDb?> = accountRepository.account

    override suspend fun accountDetails() {
        delay(delayUseCase.networkRequestDelay())

        return withContext(dispatchers.io) { accountRepository.accountDetails() }
    }
}