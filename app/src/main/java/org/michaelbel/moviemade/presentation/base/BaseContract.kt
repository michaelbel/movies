package org.michaelbel.moviemade.presentation.base

interface BaseContract {

    interface Presenter<T> {
        fun attach(view: T)
        fun destroy()
    }
}