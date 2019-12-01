package org.michaelbel.moviemade.presentation.common.appbar

import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.google.android.material.snackbar.Snackbar
import java.lang.ref.WeakReference

class MainAppBarVerticalScrollBehavior<V: View>: VerticalScrollingBehavior<V>() {

    private var bottomNavHeight: Int = 0
    private var viewRef: WeakReference<MainAppBar>? = null

    override fun onLayoutChild(parent: CoordinatorLayout, child: V, layoutDirection: Int): Boolean {
        parent.onLayoutChild(child, layoutDirection)
        if (child is MainAppBar) {
            viewRef = WeakReference(child as MainAppBar)
        }

        child.post { bottomNavHeight = child.height }
        updateSnackBarPosition(child, getSnackBarInstance(parent, child))
        return super.onLayoutChild(parent, child, layoutDirection)
    }

    override fun layoutDependsOn(parent: CoordinatorLayout, child: V, dependency: View): Boolean {
        return isDependent(dependency) || super.layoutDependsOn(parent, child, dependency)
    }

    private fun isDependent(dependency: View): Boolean = dependency is Snackbar.SnackbarLayout

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: V, dependency: View): Boolean {
        if (isDependent(dependency)) {
            updateSnackBarPosition(child, dependency)
            return false
        }

        return super.onDependentViewChanged(parent, child, dependency)
    }

    private fun updateSnackBarPosition(child: V, dependency: View?, translationY: Float = child.translationY - child.height) {
        if (dependency is Snackbar.SnackbarLayout) {
            ViewCompat.animate(dependency).setInterpolator(FastOutSlowInInterpolator()).setDuration(80).setStartDelay(0).translationY(translationY).start()
        }
    }

    private fun getSnackBarInstance(parent: CoordinatorLayout, child: V): Snackbar.SnackbarLayout? {
        val dependencies = parent.getDependencies(child)

        var i = 0
        val z = dependencies.size
        while (i < z) {
            val view = dependencies[i]
            if (view is Snackbar.SnackbarLayout) {
                return view
            }
            i++
        }

        return null
    }

    override fun onNestedVerticalScrollUnconsumed(coordinatorLayout: CoordinatorLayout, child: V, scrollDirection: Int, currentOverScroll: Int, totalScroll: Int) {}

    override fun onNestedVerticalPreScroll(coordinatorLayout: CoordinatorLayout, child: V, target: View, dx: Int, dy: Int, consumed: IntArray, scrollDirection: Int) {}

    override fun onNestedDirectionFling(coordinatorLayout: CoordinatorLayout, child: V, target: View, velocityX: Float, velocityY: Float, consumed: Boolean, scrollDirection: Int): Boolean {
        return consumed
    }

    override fun onNestedVerticalScrollConsumed(coordinatorLayout: CoordinatorLayout, child: V, scrollDirection: Int, currentOverScroll: Int, totalConsumedScroll: Int) {
        handleDirection(coordinatorLayout, child, scrollDirection)
    }

    private fun handleDirection(parent: CoordinatorLayout, child: V, scrollDirection: Int) {
        val appBar = viewRef?.get()

        if (appBar != null && appBar.isAutoHideEnabled) {
            if (scrollDirection == SCROLL_DIRECTION_DOWN && appBar.isHidden) {
                updateSnackBarPosition(child, getSnackBarInstance(parent, child), (-bottomNavHeight).toFloat())
                appBar.show(true)
            } else if (scrollDirection == SCROLL_DIRECTION_UP && !appBar.isHidden) {
                updateSnackBarPosition(child, getSnackBarInstance(parent, child), 0F)
                appBar.hide(true)
            }
        }
    }
}