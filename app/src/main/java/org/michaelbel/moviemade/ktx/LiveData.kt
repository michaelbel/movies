@file:Suppress("nothing_to_inline", "unused")

package org.michaelbel.moviemade.ktx

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

inline fun <T> LiveData<T>.reObserve(owner: LifecycleOwner, observer: Observer<T>) {
    removeObserver(observer)
    observe(owner, observer)
}

inline fun <T> MutableLiveData<T>.toLiveData(): LiveData<T> = this

/**
 * Example:
 *
 * onViewCreated() {
 *     observe(viewModel.stateLiveData, ::onStateChange)
 * }
 *
 * private fun onStateChange(stateLiveData)
 */
/*inline fun <T> LifecycleOwner.observe(liveData: LiveData<T>, body: (T) -> Unit = {}) {
    liveData.observe(this, Observer { it?.let { t -> body(t) } })
}*/