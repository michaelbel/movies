package org.michaelbel.moviemade.utils

import io.reactivex.disposables.Disposable

object RxUtil {

    fun unsubscribe(subscription: Disposable?) {
        if (subscription != null && !subscription.isDisposed) {
            subscription.dispose()
        }
    }

    fun unsubscribe(vararg subscriptions: Disposable) {
        for (subscription in subscriptions) {
            unsubscribe(subscription)
        }
    }
}