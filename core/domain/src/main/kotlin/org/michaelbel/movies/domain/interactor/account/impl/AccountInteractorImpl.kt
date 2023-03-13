package org.michaelbel.movies.domain.interactor.account.impl

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.michaelbel.movies.common.coroutines.Dispatcher
import org.michaelbel.movies.common.coroutines.MoviesDispatchers
import org.michaelbel.movies.domain.data.entity.AccountDb
import org.michaelbel.movies.domain.interactor.account.AccountInteractor
import org.michaelbel.movies.domain.repository.account.AccountRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.delay
import org.michaelbel.movies.domain.usecase.DelayUseCase

@Singleton
internal class AccountInteractorImpl @Inject constructor(
    @Dispatcher(MoviesDispatchers.IO) private val dispatcher: CoroutineDispatcher,
    private val accountRepository: AccountRepository,
    private val delayUseCase: DelayUseCase
): AccountInteractor {

    override val account: Flow<AccountDb?> = accountRepository.account

    override suspend fun accountDetails() {
        delay(delayUseCase.networkRequestDelay())

        return withContext(dispatcher) { accountRepository.accountDetails() }
    }
}