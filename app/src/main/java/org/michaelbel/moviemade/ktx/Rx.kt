@file:Suppress("nothing_to_inline", "unused")

package org.michaelbel.moviemade.ktx

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

inline fun CompositeDisposable.put(disposable: Disposable) {
    delete(disposable)
    add(disposable)
}