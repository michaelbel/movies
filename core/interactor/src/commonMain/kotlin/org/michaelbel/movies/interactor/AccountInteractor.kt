package org.michaelbel.movies.interactor

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.entity.pojo.AccountPojo
import org.michaelbel.movies.persistence.database.typealiases.AccountId

interface AccountInteractor {

    val account: Flow<AccountPojo?>

    suspend fun accountId(): AccountId?

    suspend fun accountExpireTime(): Long?

    suspend fun accountDetails()
}