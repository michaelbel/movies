package org.michaelbel.moviemade.presentation.base

import io.reactivex.disposables.CompositeDisposable

open class Presenter {
    protected var disposable = CompositeDisposable()
}