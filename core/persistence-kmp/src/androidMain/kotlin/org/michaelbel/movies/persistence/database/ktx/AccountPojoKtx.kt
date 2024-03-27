package org.michaelbel.movies.persistence.database.ktx

import org.michaelbel.movies.persistence.database.entity.AccountDb
import org.michaelbel.movies.persistence.database.entity.AccountPojo

internal val AccountPojo.accountDb: AccountDb
    get() = AccountDb(
        accountId = accountId,
        avatarUrl = avatarUrl,
        language = language,
        country = country,
        name = name,
        adult = adult,
        username = username
    )