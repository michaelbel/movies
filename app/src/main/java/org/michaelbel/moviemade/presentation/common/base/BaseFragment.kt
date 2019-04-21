package org.michaelbel.moviemade.presentation.common.base

import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {

    open fun onNewIntent(action: String?, data: String?) {}

    open fun onScrollToTop() {}
}