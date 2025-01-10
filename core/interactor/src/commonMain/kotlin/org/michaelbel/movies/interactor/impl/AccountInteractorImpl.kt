package org.michaelbel.movies.interactor.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.michaelbel.movies.common.dispatchers.MoviesDispatchers
import org.michaelbel.movies.interactor.AccountInteractor
import org.michaelbel.movies.persistence.database.entity.pojo.AccountPojo
import org.michaelbel.movies.persistence.database.typealiases.AccountId
import org.michaelbel.movies.repository.AccountRepository

internal class AccountInteractorImpl(
    private val dispatchers: MoviesDispatchers,
    private val accountRepository: AccountRepository
): AccountInteractor {

    override val account: Flow<AccountPojo?> = accountRepository.account

    override suspend fun accountId(): AccountId {
        return withContext(dispatchers.io) { accountRepository.accountId() }
    }

    override suspend fun accountExpireTime(): Long? {
        return withContext(dispatchers.io) { accountRepository.accountExpireTime() }
    }

    override suspend fun accountDetails() {
        return withContext(dispatchers.io) { accountRepository.accountDetails() }
    }
}