@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.network.config

expect val String.formatPosterImage: String

expect val String.formatBackdropImage: String

expect val String.formatProfileImage: String

@Suppress("unused")
expect val String.original: String

expect val String.isNotOriginal: Boolean

expect fun String.formatImage(size: String): String