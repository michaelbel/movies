package org.michaelbel.moviemade.presentation

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

open class BaseFragment(@LayoutRes containerResId: Int): Fragment(containerResId) {

    open fun onNewIntent(action: String?, data: String?) {}
}