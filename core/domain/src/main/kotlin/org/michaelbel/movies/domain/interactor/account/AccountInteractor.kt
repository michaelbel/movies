package org.michaelbel.movies.domain.interactor.account

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.domain.data.entity.AccountDb

interface AccountInteractor {

    val account: Flow<AccountDb?>

    suspend fun accountDetails()
}