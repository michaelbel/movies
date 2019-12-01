package org.michaelbel.moviemade.presentation.widget

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.animation.DecelerateInterpolator
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.widget_fave_button.view.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.ktx.inflate

class FaveButton(context: Context, attrs: AttributeSet?): CardView(context, attrs) {

    data class Data(
            @DrawableRes val iconRes: Int,
            @StringRes val textRes: Int,
            val selected: Boolean = false)

    init {
        inflate(R.layout.widget_fave_button)
        setBackgroundColor(ContextCompat.getColor(context, R.color.backgroundColor))
    }

    fun setData(data: Data, animate: Boolean = false) {
        icon.setImageResource(data.iconRes)
        text.setText(data.textRes)

        if (animate) {
            changeColors(data.selected)
        } else {
            icon.setColorFilter(ContextCompat.getColor(context, if (data.selected) R.color.accent_blue else R.color.textColorPrimary))
            text.setTextColor(ContextCompat.getColor(context, if (data.selected) R.color.accent_blue else R.color.textColorPrimary))
        }
    }

    private fun changeColors(selected: Boolean) {
        val colorStart = ContextCompat.getColor(context, if (selected) R.color.textColorPrimary else R.color.accent_blue)
        val colorEnd = ContextCompat.getColor(context, if (selected) R.color.accent_blue else R.color.textColorPrimary)

        AnimatorSet().apply {
            playTogether(
                    ObjectAnimator.ofObject(icon, "colorFilter", ArgbEvaluator(), colorStart, colorEnd),
                    ObjectAnimator.ofObject(text, "textColor", ArgbEvaluator(), colorStart, colorEnd)
            )
            duration = 250L
            interpolator = DecelerateInterpolator(2F)
        }
    }
}