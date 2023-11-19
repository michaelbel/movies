package org.michaelbel.movies.platform.main.update

interface UpdateListener {
    fun onAvailable(result: Boolean)
}