package org.michaelbel.moviemade.presentation.base

interface BasePresenter<T> {
    fun attach(view: T)
    fun destroy()
}