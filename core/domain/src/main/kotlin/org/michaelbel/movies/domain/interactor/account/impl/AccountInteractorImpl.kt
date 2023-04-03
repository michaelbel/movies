package org.michaelbel.movies.domain.interactor.account.impl

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.michaelbel.movies.common.dispatchers.MoviesDispatchers
import org.michaelbel.movies.domain.data.entity.AccountDb
import org.michaelbel.movies.domain.interactor.account.AccountInteractor
import org.michaelbel.movies.domain.repository.account.AccountRepository
import org.michaelbel.movies.domain.usecase.DelayUseCase
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