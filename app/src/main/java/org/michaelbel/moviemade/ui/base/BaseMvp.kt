package org.michaelbel.moviemade.ui.base

import android.view.View
import androidx.fragment.app.Fragment

interface BaseMvp {

    fun startFragment(fragment: Fragment, container: View)

    fun startFragment(fragment: Fragment, containerId: Int)

    fun startFragment(fragment: Fragment, container: View, tag: String)

    fun startFragment(fragment: Fragment, containerId: Int, tag: String)

    fun finishFragment()
}