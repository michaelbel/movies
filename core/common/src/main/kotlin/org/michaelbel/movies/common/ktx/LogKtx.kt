package org.michaelbel.movies.common.ktx

import org.michaelbel.movies.common.BuildConfig

fun printlnDebug(message: String) {
    if (BuildConfig.DEBUG) {
        println(message)
    }
}