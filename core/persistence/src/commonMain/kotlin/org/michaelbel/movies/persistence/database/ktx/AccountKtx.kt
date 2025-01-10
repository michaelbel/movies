package org.michaelbel.movies.persistence.database.ktx

import org.michaelbel.movies.network.config.GRAVATAR_URL
import org.michaelbel.movies.network.config.formatProfileImage
import org.michaelbel.movies.network.model.Account
import org.michaelbel.movies.persistence.database.entity.pojo.AccountPojo

val Account.accountPojo: AccountPojo
    get() = AccountPojo(
        accountId = id,
        avatarUrl = when {
            avatar.tmdbAvatar != null -> avatar.tmdbAvatar?.avatarPath.orEmpty().formatProfileImage
            avatar.grAvatar != null -> "$GRAVATAR_URL/${avatar.grAvatar?.hash}"
            else -> ""
        },
        language = lang,
        country = country,
        name = name,
        adult = includeAdult,
        username = username
    )