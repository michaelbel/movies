package org.michaelbel.moviemade.ktx

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlin.reflect.KProperty

/**
 * Function that simplify android.os.Bundle unpacking.
 * @param provideArguments block function, that expect android.os.Bundle from object
 *
 * Usage example:
 * private val wrapper = { f: Fragment -> a.arguments }
 *
 * @return ReadOnlyProperty, Usage example:
 * val argument: Int by argumentsDelegate(wrapper)
 */
inline fun <F, reified T> argumentDelegate(crossinline provideArguments: (F) -> Bundle?): LazyProvider<F, T> =
        object: LazyProvider<F, T> {
            override fun provideDelegate(thisRef: F, prop: KProperty<*>): Lazy<T> = lazy {
                val bundle = provideArguments(thisRef)
                bundle?.get(prop.name) as T
            }
        }

interface LazyProvider<A, T> {
    operator fun provideDelegate(thisRef: A, prop: KProperty<*>): Lazy<T>
}

/**
 * Extension function for android.app.Activity that simplify
 * android.os.Bundle unpacking.
 * @return ReadOnlyProperty
 *
 * Usage example:
 * val argument: Int by argumentsDelegate()
 */
inline fun <reified T> Activity.argumentDelegate(): LazyProvider<Activity, T> {
    return argumentDelegate { it.intent?.extras }
}

/**
 * Extension function for androidx.fragment.app.Fragment that simplify
 * {@link android.os.Bundle} unpacking.
 * @return ReadOnlyProperty
 *
 * Usage example:
 * val argument: Int by argumentsDelegate()
 */
inline fun <reified T> Fragment.argumentDelegate(): LazyProvider<Fragment, T> {
    return argumentDelegate { it.arguments }
}