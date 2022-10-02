package org.michaelbel.movies.analytics.model

import android.os.Bundle
import androidx.core.os.bundleOf

open class BaseEvent(
    val name: String
) {

    val params: Bundle = bundleOf()

    fun add(key: String, value: Any) {
        params.putString(key, value.toString())
    }
}