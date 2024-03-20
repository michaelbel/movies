package org.michaelbel.movies.common.ktx

import org.michaelbel.movies.common_kmp.BuildConfig

actual fun printlnDebug(message: String) {
    if (BuildConfig.DEBUG) {
        println(message)
    }
}