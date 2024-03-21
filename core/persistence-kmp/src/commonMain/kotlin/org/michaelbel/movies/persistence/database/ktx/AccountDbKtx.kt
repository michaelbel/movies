@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.persistence.database.ktx

import org.michaelbel.movies.persistence.database.entity.AccountDb

expect val AccountDb.isEmpty: Boolean

expect val AccountDb?.orEmpty: AccountDb

expect val AccountDb.letters: String