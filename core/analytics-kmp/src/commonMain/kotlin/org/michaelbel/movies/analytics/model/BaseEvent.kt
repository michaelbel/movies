package org.michaelbel.movies.analytics.model

open class BaseEvent internal constructor(
    val name: String
) {
    val params = hashMapOf<String, String>()

    protected open fun add(key: String, value: Any) {
        params[key] = value.toString()
    }
}