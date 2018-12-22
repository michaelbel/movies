package org.michaelbel.moviemade.data.eventbus

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class RxBus {

    private val publishSubject = PublishSubject.create<Any>()

    fun send(`object`: Any) {
        publishSubject.onNext(`object`)
    }

    fun toObservable(): Observable<Any> {
        return publishSubject
    }
}