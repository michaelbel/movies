package org.michaelbel.movies.shared

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform