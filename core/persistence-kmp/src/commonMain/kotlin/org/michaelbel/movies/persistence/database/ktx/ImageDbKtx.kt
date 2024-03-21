@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.persistence.database.ktx

import org.michaelbel.movies.persistence.database.entity.ImageDb

expect val ImageDb.image: String

expect val ImageDb.original: String