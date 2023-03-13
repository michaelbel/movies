package org.michaelbel.movies.domain.ktx

import org.michaelbel.movies.domain.data.entity.AccountDb
import org.michaelbel.movies.entities.GRAVATAR_URL
import org.michaelbel.movies.network.model.Account
import java.util.Locale

internal val Account.mapToAccountDb: AccountDb
    get() = AccountDb(
        accountId = id,
        avatarUrl = when {
            avatar.tmdbAvatar != null -> formatImageUrl(avatar.tmdbAvatar?.avatarPath.orEmpty())
            avatar.grAvatar != null -> String.format(Locale.US, GRAVATAR_URL, avatar.grAvatar?.hash)
            else -> ""
        },
        language = lang,
        country = country,
        name = name,
        adult = includeAdult,
        username = username
    )