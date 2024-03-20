package org.michaelbel.movies.common.ktx

import androidx.lifecycle.SavedStateHandle

actual fun <T> SavedStateHandle.require(key: String): T = requireNotNull(this[key])