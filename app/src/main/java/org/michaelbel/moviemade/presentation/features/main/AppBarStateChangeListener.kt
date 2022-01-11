package org.michaelbel.moviemade.presentation.features.main

import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

abstract class AppBarStateChangeListener: AppBarLayout.OnOffsetChangedListener {

    enum class AppBarState {
        EXPANDED, COLLAPSED, IDLE
    }

    private var currentState = AppBarState.IDLE

    override fun onOffsetChanged(appBarLayout: AppBarLayout, i: Int) {
        when {
            i == 0 -> {
                if (currentState != AppBarState.EXPANDED) {
                    onStateChanged(appBarLayout, true)
                }
                currentState = AppBarState.EXPANDED
            }
            abs(i) >= appBarLayout.totalScrollRange -> {
                if (currentState != AppBarState.COLLAPSED) {
                    onStateChanged(appBarLayout, false)
                }
                currentState = AppBarState.COLLAPSED
            }
            else -> {
                if (currentState != AppBarState.IDLE) {
                    onStateChanged(appBarLayout, false)
                }
                currentState = AppBarState.IDLE
            }
        }
    }

    abstract fun onStateChanged(appBarLayout: AppBarLayout, expanded: Boolean)
}