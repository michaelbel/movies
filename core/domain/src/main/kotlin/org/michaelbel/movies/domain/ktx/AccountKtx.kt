package org.michaelbel.movies.domain.ktx

import org.michaelbel.movies.entities.GRAVATAR_URL
import org.michaelbel.movies.entities.image.formatProfileImage
import org.michaelbel.movies.network.model.Account
import org.michaelbel.movies.persistence.database.entity.AccountDb
import java.util.Locale

internal val Account.mapToAccountDb: AccountDb
    get() = AccountDb(
        accountId = id,
        avatarUrl = when {
            avatar.tmdbAvatar != null -> avatar.tmdbAvatar?.avatarPath.orEmpty().formatProfileImage
            avatar.grAvatar != null -> String.format(Locale.US, GRAVATAR_URL, avatar.grAvatar?.hash)
            else -> ""
        },
        language = lang,
        country = country,
        name = name,
        adult = includeAdult,
        username = username
    )