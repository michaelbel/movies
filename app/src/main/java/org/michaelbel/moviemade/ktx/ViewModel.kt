@file:Suppress("nothing_to_inline", "unused")

package org.michaelbel.moviemade.ktx

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import org.michaelbel.moviemade.presentation.common.base.BaseViewModelFactory

inline fun <reified T : ViewModel> Fragment.getViewModel(noinline creator: (() -> T)? = null): T {
    return if (creator == null) ViewModelProviders.of(this).get(T::class.java) else ViewModelProviders.of(this, BaseViewModelFactory(creator)).get(T::class.java)
}

inline fun <reified T : ViewModel> FragmentActivity.getViewModel(noinline creator: (() -> T)? = null): T {
    return if (creator == null) ViewModelProviders.of(this).get(T::class.java) else ViewModelProviders.of(this, BaseViewModelFactory(creator)).get(T::class.java)
}