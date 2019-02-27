package org.michaelbel.moviemade.presentation.features.main.appbar

import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.google.android.material.snackbar.Snackbar
import java.lang.ref.WeakReference

class MainAppBarVerticalScrollBehavior<V : View> : VerticalScrollingBehavior<V>() {

    private var mBottomNavHeight: Int = 0
    private var mViewRef: WeakReference<MainAppBar>? = null

    override fun onLayoutChild(parent: CoordinatorLayout, child: V, layoutDirection: Int): Boolean {
        parent.onLayoutChild(child, layoutDirection)
        if (child is MainAppBar) {
            mViewRef = WeakReference(child as MainAppBar)
        }

        child.post { mBottomNavHeight = child.height }
        updateSnackBarPosition(parent, child, getSnackBarInstance(parent, child))
        return super.onLayoutChild(parent, child, layoutDirection)
    }

    override fun layoutDependsOn(parent: CoordinatorLayout, child: V, dependency: View): Boolean {
        return isDependent(dependency) || super.layoutDependsOn(parent, child, dependency)
    }

    private fun isDependent(dependency: View): Boolean {
        return dependency is Snackbar.SnackbarLayout
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: V, dependency: View): Boolean {
        if (isDependent(dependency)) {
            updateSnackBarPosition(parent, child, dependency)
            return false
        }

        return super.onDependentViewChanged(parent, child, dependency)
    }

    private fun updateSnackBarPosition(parent: CoordinatorLayout, child: V, dependency: View?, translationY: Float = child.translationY - child.height) {
        if (dependency is Snackbar.SnackbarLayout) {
            ViewCompat.animate(dependency).setInterpolator(INTERPOLATOR).setDuration(80).setStartDelay(0).translationY(translationY).start()
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

    override fun onNestedVerticalPreScroll(coordinatorLayout: CoordinatorLayout, child: V, target: View, dx: Int, dy: Int, consumed: IntArray, scrollDirection: Int) {
        //handleDirection(child, scrollDirection);
    }

    override fun onNestedDirectionFling(coordinatorLayout: CoordinatorLayout, child: V, target: View, velocityX: Float, velocityY: Float, consumed: Boolean, scrollDirection: Int): Boolean {
        /*if (consumed) {
            handleDirection(child, scrollDirection);
        }*/
        return consumed
    }

    override fun onNestedVerticalScrollConsumed(coordinatorLayout: CoordinatorLayout, child: V, scrollDirection: Int, currentOverScroll: Int, totalConsumedScroll: Int) {
        handleDirection(coordinatorLayout, child, scrollDirection)
    }

    private fun handleDirection(parent: CoordinatorLayout, child: V, scrollDirection: Int) {
        val appBar = mViewRef!!.get()

        if (appBar != null && appBar.isAutoHideEnabled) {
            if (scrollDirection == VerticalScrollingBehavior.SCROLL_DIRECTION_DOWN && appBar.isHidden) {
                updateSnackBarPosition(parent, child, getSnackBarInstance(parent, child), (-mBottomNavHeight).toFloat())
                appBar.show(true)
            } else if (scrollDirection == VerticalScrollingBehavior.SCROLL_DIRECTION_UP && !appBar.isHidden) {
                updateSnackBarPosition(parent, child, getSnackBarInstance(parent, child), 0f)
                appBar.hide(true)
            }
        }
    }

    companion object {

        private val INTERPOLATOR = FastOutSlowInInterpolator()
    }
}