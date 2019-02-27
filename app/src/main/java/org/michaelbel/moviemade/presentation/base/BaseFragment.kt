package org.michaelbel.moviemade.presentation.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import butterknife.ButterKnife
import butterknife.Unbinder

abstract class BaseFragment: Fragment() {

    private var unbinder: Unbinder? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        unbinder = ButterKnife.bind(this, view)
    }

    override fun onDestroy() {
        super.onDestroy()
        unbinder!!.unbind()
    }
}