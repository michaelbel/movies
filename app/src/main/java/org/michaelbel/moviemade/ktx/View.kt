@file:Suppress("nothing_to_inline", "unused")

package org.michaelbel.moviemade.ktx

import android.content.Context.INPUT_METHOD_SERVICE
import android.content.res.ColorStateList
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.os.Build
import android.view.View
import android.view.View.MeasureSpec
import android.view.View.OnAttachStateChangeListener
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.Px
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.view.*
import timber.log.Timber

inline fun View.createGradientDrawable(
    @ColorRes color: Int = 0,
    topStartRadiusDimen: Float = 0F,
    topEndRadiusDimen: Float = 0F,
    bottomStartRadiusDimen: Float = 0F,
    bottomEndRadiusDimen: Float = 0F
) {
    val gradientDrawable = GradientDrawable()
    if (color != 0) {
        gradientDrawable.setColor(ContextCompat.getColor(context, color))
    }
    gradientDrawable.cornerRadii = floatArrayOf(
            topStartRadiusDimen.toDp(context).toFloat(),
            topStartRadiusDimen.toDp(context).toFloat(),
            topEndRadiusDimen.toDp(context).toFloat(),
            topEndRadiusDimen.toDp(context).toFloat(),
            bottomStartRadiusDimen.toDp(context).toFloat(),
            bottomStartRadiusDimen.toDp(context).toFloat(),
            bottomEndRadiusDimen.toDp(context).toFloat(),
            bottomEndRadiusDimen.toDp(context).toFloat()
    )
    this.background = gradientDrawable
}

inline fun View.getIcon(@DrawableRes resource: Int, @ColorRes color: Int): Drawable? {
    val iconDrawable = ContextCompat.getDrawable(context, resource) ?: context.getDrawable(resource)
    val colorDrawable = ContextCompat.getColor(context, color)

    iconDrawable?.clearColorFilter()
    iconDrawable?.mutate()
    if (Build.VERSION.SDK_INT >= 29) {
        iconDrawable?.colorFilter = BlendModeColorFilter(colorDrawable, BlendMode.COLOR)
    } else {
        iconDrawable?.setColorFilter(colorDrawable, PorterDuff.Mode.MULTIPLY)
    }

    return iconDrawable
}

inline fun View.showKeyboard() {
    val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, SHOW_IMPLICIT)
}

inline fun View.hideKeyboard(): Boolean {
    try {
        val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        return imm.hideSoftInputFromWindow(windowToken, 0)
    } catch (e: Exception) {
        Timber.e(e)
    }

    return false
}

inline fun View.toggleKeyboard(state: Boolean) {
    val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    if (state) {
        imm.showSoftInput(this, SHOW_IMPLICIT)
    } else {
        if (!imm.isActive) {
            return
        }
        imm.hideSoftInputFromWindow(this.windowToken, 0)
    }
}

inline val View.isKeyboardShown: Boolean
    get() {
        val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        return imm.isActive(this)
    }

var View.startPadding: Int
    inline get() = paddingLeft
    set(value) = setPadding(value, paddingTop, paddingRight, paddingBottom)

var View.topPadding: Int
    inline get() = paddingTop
    set(value) = setPadding(paddingLeft, value, paddingRight, paddingBottom)

var View.endPadding: Int
    inline get() = paddingRight
    set(value) = setPadding(paddingLeft, paddingTop, value, paddingBottom)

var View.bottomPadding: Int
    inline get() = paddingBottom
    set(value) = setPadding(paddingLeft, paddingTop, paddingRight, value)

/*var View.topMargin: Int
    inline get() = marginTop
    set(value) {
        val lp = layoutParams as? ViewGroup.MarginLayoutParams ?: return
        lp.let {
            lp.topMargin = value
            layoutParams = lp
        }
    }*/

inline fun View.setWidth(@Px value: Int) {
    val lp = layoutParams
    lp?.let {
        lp.width = value
        layoutParams = lp
    }
}

inline fun View.setHeight(@Px value: Int) {
    val lp = layoutParams
    lp?.let {
        lp.height = value
        layoutParams = lp
    }
}

inline fun View.setViewHeight(@Px value: Int) {
    val lp = layoutParams
    lp?.let {
        lp.height = value
        layoutParams = lp
    }
}

inline fun View.resize(@Px width: Int, @Px height: Int) {
    val lp = layoutParams
    lp?.let {
        lp.width = width
        lp.height = height
        layoutParams = lp
    }
}

inline fun View.createRippleBackground(@ColorRes rippleColor: Int) {
    if (Build.VERSION.SDK_INT < 21) {
        this.createSelectableItemBackgroundDrawable()
        return
    }

    val rippleDrawable = background as RippleDrawable
    val states = arrayOf(intArrayOf(android.R.attr.state_enabled))
    val colors = intArrayOf(ContextCompat.getColor(context, rippleColor))
    val colorStateList = ColorStateList(states, colors)
    rippleDrawable.setColor(colorStateList)
    this.background = rippleDrawable
}

@RequiresApi(23)
inline fun View.createRippleForeground(@ColorRes rippleColor: Int) {
    val rippleDrawable = background as RippleDrawable
    val states = arrayOf(intArrayOf(android.R.attr.state_enabled))
    val colors = intArrayOf(ContextCompat.getColor(context, rippleColor))
    val colorStateList = ColorStateList(states, colors)
    rippleDrawable.setColor(colorStateList)
    foreground = rippleDrawable
}

inline fun View.createSelectableItemBackgroundDrawable() {
    val attrs = intArrayOf(android.R.attr.selectableItemBackground)
    val typedArray = context.obtainStyledAttributes(attrs)
    val drawableFromTheme = typedArray.getDrawable(0)
    typedArray.recycle()
    this.background = drawableFromTheme
}

inline fun View.selectableItemBackgroundBorderlessDrawable() {
    if (Build.VERSION.SDK_INT >= 21) {
        val attrs = intArrayOf(android.R.attr.selectableItemBackgroundBorderless)
        val typedArray = context.obtainStyledAttributes(attrs)
        val drawableFromTheme = typedArray.getDrawable(0)
        typedArray.recycle()
        this.background = drawableFromTheme
    } else {
        this.createSelectableItemBackgroundDrawable()
    }
}

@RequiresApi(23)
inline fun View.createSelectableItemForegroundDrawable() {
    if (Build.VERSION.SDK_INT >= 23) {
        val attrs = intArrayOf(android.R.attr.selectableItemBackground)
        val typedArray = context.obtainStyledAttributes(attrs)
        val drawableFromTheme = typedArray.getDrawable(0)
        typedArray.recycle()
        foreground = drawableFromTheme
    }
}

@RequiresApi(23)
inline fun View.createSelectableItemForegroundBorderlessDrawable() {
    if (Build.VERSION.SDK_INT >= 23) {
        val attrs = intArrayOf(android.R.attr.selectableItemBackgroundBorderless)
        val typedArray = context.obtainStyledAttributes(attrs)
        val drawableFromTheme = typedArray.getDrawable(0)
        typedArray.recycle()
        foreground = drawableFromTheme
    }
}

inline fun View.setMarginStart(@Px value: Int) {
    val lp = layoutParams as? ViewGroup.MarginLayoutParams ?: return
    lp.let {
        lp.marginStart = value
        layoutParams = lp
    }
}

inline fun View.setMarginTop(@Px value: Int) {
    val lp = layoutParams as? ViewGroup.MarginLayoutParams ?: return
    lp.let {
        lp.topMargin = value
        layoutParams = lp
    }
}

inline fun View.setMarginEnd(@Px value: Int) {
    val lp = layoutParams as? ViewGroup.MarginLayoutParams ?: return
    lp.let {
        lp.marginEnd = value
        layoutParams = lp
    }
}

inline fun View.setMarginBottom(@Px value: Int) {
    val lp = layoutParams as? ViewGroup.MarginLayoutParams ?: return
    lp.let {
        lp.bottomMargin = value
        layoutParams = lp
    }
}

inline fun View.createFrameParams(@Px width: Int, @Px height: Int) {
    layoutParams = FrameLayout.LayoutParams(width, height)
}

inline fun View.createFrameParams(@Px width: Int, @Px height: Int, gravity: Int) {
    layoutParams = FrameLayout.LayoutParams(width, height, gravity)
}

inline fun View.createFrameParams(
    @Px width: Int,
    @Px height: Int,
    @Px start: Int = marginLeft,
    @Px top: Int = marginTop,
    @Px end: Int = marginEnd,
    @Px bottom: Int = marginBottom
) {
    val params = FrameLayout.LayoutParams(width, height)
    params.setMargins(start, top, end, bottom)
    layoutParams = params
}

inline fun View.createFrameParams(@Px width: Int, @Px height: Int, gravity: Int, @Px start: Int = marginLeft, @Px top: Int = marginTop, @Px end: Int = marginEnd, @Px bottom: Int = marginBottom) {
    val params = FrameLayout.LayoutParams(width, height, gravity)
    params.setMargins(start, top, end, bottom)
    layoutParams = params
}

inline fun View.createLinearParams(@Px width: Int, @Px height: Int) {
    layoutParams = LinearLayoutCompat.LayoutParams(width, height)
}

inline fun View.createLinearParams(@Px width: Int, @Px height: Int, gravity: Int = -1, @Px start: Int = marginLeft, @Px top: Int = marginTop, @Px end: Int = marginEnd, @Px bottom: Int = marginBottom) {
    val params = LinearLayoutCompat.LayoutParams(width, height)
    if (gravity != -1) {
        params.gravity = gravity
    }
    params.setMargins(start, top, end, bottom)
    layoutParams = params
}

inline fun View.createCoordinatorParams(@Px width: Int, @Px height: Int, gravity: Int, @Px start: Int = marginLeft, @Px top: Int = marginTop, @Px end: Int = marginEnd, @Px bottom: Int = marginBottom) {
    val params = CoordinatorLayout.LayoutParams(width, height)
    params.gravity = gravity
    params.setMargins(start, top, end, bottom)
    layoutParams = params
}

inline val View.toBitmap: Bitmap
    get() {
        val bitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        layout(left, top, right, bottom)
        draw(canvas)
        return bitmap
    }

inline val View.toBitmapDrawable: BitmapDrawable
    get() {
        val measureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        measure(measureSpec, measureSpec)
        layout(0, 0, measuredWidth, measuredHeight)

        val bitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        background?.draw(canvas)
        draw(canvas)

        return BitmapDrawable(resources, bitmap)
    }

inline fun View.afterLayout(crossinline block: () -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            viewTreeObserver.removeOnGlobalLayoutListener(this)
            block()
        }
    })
}

fun View.doOnApplyWindowInsets(block: (View, insets: WindowInsetsCompat, initialPadding: Rect) -> WindowInsetsCompat) {
    val initialPadding = recordInitialPaddingForView(this)
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets -> block(v, insets, initialPadding) }
    requestApplyInsetsWhenAttached()
}

fun View.doOnApplyWindowInsets(block: (View, insets: WindowInsetsCompat) -> WindowInsetsCompat) {
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets -> block(v, insets) }
}

private fun recordInitialPaddingForView(view: View): Rect = Rect(view.paddingLeft, view.paddingTop, view.paddingRight, view.paddingBottom)

private fun View.requestApplyInsetsWhenAttached() {
    if (isAttachedToWindow) {
        ViewCompat.requestApplyInsets(this)
    } else {
        addOnAttachStateChangeListener(object: OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                v.removeOnAttachStateChangeListener(this)
                ViewCompat.requestApplyInsets(v)
            }

            override fun onViewDetachedFromWindow(v: View) = Unit
        })
    }
}