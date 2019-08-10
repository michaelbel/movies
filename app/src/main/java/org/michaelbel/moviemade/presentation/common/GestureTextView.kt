package org.michaelbel.moviemade.presentation.common

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatTextView
import com.alexvasilkov.gestures.GestureController
import com.alexvasilkov.gestures.State
import com.alexvasilkov.gestures.views.interfaces.GestureView
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

open class GestureTextView: AppCompatTextView, GestureView {

    private val controller: GestureController = GestureController(this)

    private var origSize: Float = 0F
    private var size: Float = 0F

    init {
        controller.settings.setOverzoomFactor(1F).isPanEnabled = false
        controller.addOnStateChangeListener(object: GestureController.OnStateChangeListener {
            override fun onStateChanged(state: State) {
                applyState(state)
            }

            override fun onStateReset(oldState: State, newState: State) {
                applyState(newState)
            }
        })

        origSize = textSize
    }

    constructor(context: Context): super(context)

    constructor(context: Context, attrs: AttributeSet): super(context, attrs)

    override fun getController(): GestureController = controller

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean = controller.onTouch(this, event)

    override fun setTextSize(size: Float) {
        super.setTextSize(size)
        origSize = textSize
        applyState(controller.state)
    }

    override fun setTextSize(unit: Int, size: Float) {
        super.setTextSize(unit, size)
        origSize = textSize
        applyState(controller.state)
    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)
        controller.settings.setViewport(width, height).setImage(width, height)
        controller.updateState()
    }

    protected fun applyState(state: State) {
        var size = origSize * state.zoom
        val maxSize = origSize * controller.settings.maxZoom
        size = max(origSize, min(size, maxSize))

        // Bigger text size steps for smoother scaling.
        size = size.roundToInt().toFloat()

        if (!State.equals(this.size, size)) {
            this.size = size
            super.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
        }
    }
}