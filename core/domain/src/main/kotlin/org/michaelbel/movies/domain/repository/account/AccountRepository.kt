package org.michaelbel.movies.domain.repository.account

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.domain.data.entity.AccountDb

interface AccountRepository {

    val account: Flow<AccountDb?>

    suspend fun accountDetails()
}