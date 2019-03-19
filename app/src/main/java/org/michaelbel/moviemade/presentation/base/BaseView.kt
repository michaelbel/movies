package org.michaelbel.moviemade.presentation.base

interface BaseView<D> {
    fun loading(state: Boolean)
    fun content(results: D)
    fun error(code: Int)
}