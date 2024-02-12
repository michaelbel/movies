package org.michaelbel.movies.analytics.model

import androidx.core.os.bundleOf

open class BaseEvent(
    val name: String
) {
    val params = bundleOf()

    protected open fun add(key: String, value: Any) {
        params.putString(key, value.toString())
    }
}