package org.michaelbel.moviemade.presentation.common.appbar

import android.content.Context
import android.util.AttributeSet
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorCompat
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import com.google.android.material.appbar.AppBarLayout
import org.michaelbel.moviemade.ktx.statusBarHeight

// fixme use AttachedBehavior
@CoordinatorLayout.DefaultBehavior(MainAppBarVerticalScrollBehavior::class)
class MainAppBar(context: Context, attrs: AttributeSet?): AppBarLayout(context, attrs) {

    private var translationAnimator: ViewPropertyAnimatorCompat? = null
    private val rippleAnimationDuration: Int = (200 * 2.5).toInt()

    val isAutoHideEnabled = true
    var isHidden = false

    fun hide(animate: Boolean) {
        isHidden = true
        setTranslationY(-(this.height + context.statusBarHeight), animate)
    }

    fun show(animate: Boolean) {
        isHidden = false
        setTranslationY(0, animate)
    }

    private fun setTranslationY(offset: Int, animate: Boolean) {
        if (animate) {
            animateOffset(offset)
        } else {
            translationAnimator?.cancel()
            translationY = offset.toFloat()
        }
    }

    private fun animateOffset(offset: Int) {
        if (translationAnimator == null) {
            translationAnimator = ViewCompat.animate(this).apply {
                duration = rippleAnimationDuration.toLong()
                interpolator = LinearOutSlowInInterpolator()
            }
        } else {
            translationAnimator?.cancel()
        }

        translationAnimator?.translationY(offset.toFloat())?.start()
    }
}