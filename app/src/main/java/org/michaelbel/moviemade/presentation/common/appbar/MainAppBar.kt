package org.michaelbel.moviemade.presentation.common.appbar

import android.content.Context
import android.util.AttributeSet
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorCompat
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import com.google.android.material.appbar.AppBarLayout
import org.michaelbel.moviemade.core.DeviceUtil

// fixme use AttachedBehavior
@CoordinatorLayout.DefaultBehavior(MainAppBarVerticalScrollBehavior::class)
class MainAppBar: AppBarLayout {

    companion object {
        private val INTERPOLATOR = LinearOutSlowInInterpolator()
        private const val DEFAULT_ANIMATION_DURATION = 200
    }

    private var translationAnimator: ViewPropertyAnimatorCompat? = null
    private val rippleAnimationDuration = (DEFAULT_ANIMATION_DURATION * 2.5).toInt()

    val isAutoHideEnabled = true
    var isHidden = false

    constructor(context: Context): super(context)

    constructor(context: Context, attrs: AttributeSet?): super(context, attrs)

    fun hide(animate: Boolean) {
        isHidden = true
        setTranslationY(-(this.height + DeviceUtil.statusBarHeight(context)), animate)
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
            translationAnimator = ViewCompat.animate(this)
            translationAnimator?.duration = rippleAnimationDuration.toLong()
            translationAnimator?.interpolator = INTERPOLATOR
        } else {
            translationAnimator?.cancel()
        }

        translationAnimator?.translationY(offset.toFloat())?.start()
    }
}