package org.michaelbel.movies.details.ktx

import androidx.lifecycle.SavedStateHandle

fun <T> SavedStateHandle.require(key: String): T = requireNotNull(this[key])