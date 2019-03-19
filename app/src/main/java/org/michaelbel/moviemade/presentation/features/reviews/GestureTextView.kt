package org.michaelbel.moviemade.presentation.features.reviews

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatTextView
import com.alexvasilkov.gestures.GestureController
import com.alexvasilkov.gestures.State
import com.alexvasilkov.gestures.views.interfaces.GestureView

open class GestureTextView
    @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
    ): AppCompatTextView(context, attrs, defStyle), GestureView {

    private val controller: GestureController = GestureController(this)

    private var origSize: Float = 0F
    private var size: Float = 0F

    init {
        controller.settings.setOverzoomFactor(1F).isPanEnabled = false
        controller.settings.initFromAttributes(context, attrs)
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

    override fun getController(): GestureController = controller

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
        size = Math.max(origSize, Math.min(size, maxSize))

        // Bigger text size steps for smoother scaling.
        size = Math.round(size).toFloat()

        if (!State.equals(this.size, size)) {
            this.size = size
            super.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
        }
    }
}