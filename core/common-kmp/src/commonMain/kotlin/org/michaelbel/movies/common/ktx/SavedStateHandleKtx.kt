@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.common.ktx

import androidx.lifecycle.SavedStateHandle

expect fun <T> SavedStateHandle.require(key: String): T