package org.michaelbel.moviemade.presentation.features.main.appbar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout

abstract class VerticalScrollingBehavior<V: View>: CoordinatorLayout.Behavior<V> {

    companion object {
        const val SCROLL_DIRECTION_UP = 1
        const val SCROLL_DIRECTION_DOWN = -1
        const val SCROLL_NONE = 0
    }

    private var totalDyUnconsumed = -1
    private var totalDyConsumed = -1
    private var totalDy = -1

    /**
     * @return Scroll direction: SCROLL_DIRECTION_UP, SCROLL_DIRECTION_DOWN, SCROLL_NONE
     */
    private var scrollDirection = SCROLL_NONE

    /**
     * @return PreScroll direction: SCROLL_DIRECTION_UP, SCROLL_DIRECTION_DOWN, SCROLL_NONE
     */
    private var preScrollDirection = SCROLL_NONE

    /**
     * @return ConsumedScroll direction: SCROLL_DIRECTION_UP, SCROLL_DIRECTION_DOWN, SCROLL_NONE
     */
    private var consumedScrollDirection = SCROLL_NONE

    constructor(context: Context, attrs: AttributeSet): super(context, attrs)

    constructor(): super()

    override fun onStartNestedScroll(
            coordinatorLayout: CoordinatorLayout,
            child: V,
            directTargetChild: View,
            target: View,
            nestedScrollAxes: Int
    ): Boolean {
        return nestedScrollAxes and View.SCROLL_AXIS_VERTICAL != 0
    }

    override fun onNestedScroll(
            coordinatorLayout: CoordinatorLayout,
            child: V,
            target: View,
            dxConsumed: Int,
            dyConsumed: Int,
            dxUnconsumed: Int,
            dyUnconsumed: Int
    ) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed)
        if (dyUnconsumed > 0 && totalDyUnconsumed < 0) {
            totalDyUnconsumed = 0
            scrollDirection = SCROLL_DIRECTION_UP
            onNestedVerticalScrollUnconsumed(coordinatorLayout, child, scrollDirection, dyConsumed, totalDyUnconsumed)
        } else if (dyUnconsumed < 0 && totalDyUnconsumed > 0) {
            totalDyUnconsumed = 0
            scrollDirection = SCROLL_DIRECTION_DOWN
            onNestedVerticalScrollUnconsumed(coordinatorLayout, child, scrollDirection, dyConsumed, totalDyUnconsumed)
        }
        totalDyUnconsumed += dyUnconsumed

        if (dyConsumed > 0 && totalDyConsumed < 0) {
            totalDyConsumed = 0
            consumedScrollDirection = SCROLL_DIRECTION_UP
            onNestedVerticalScrollConsumed(coordinatorLayout, child, consumedScrollDirection, dyConsumed, totalDyConsumed)
        } else if (dyConsumed < 0 && totalDyConsumed > 0) {
            totalDyConsumed = 0
            consumedScrollDirection = SCROLL_DIRECTION_DOWN
            onNestedVerticalScrollConsumed(coordinatorLayout, child, consumedScrollDirection, dyConsumed, totalDyConsumed)
        }
        totalDyConsumed += dyConsumed
    }

    override fun onNestedPreScroll(
            coordinatorLayout: CoordinatorLayout,
            child: V,
            target: View,
            dx: Int,
            dy: Int,
            consumed: IntArray
    ) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed)
        if (dy > 0 && totalDy < 0) {
            totalDy = 0
            preScrollDirection = SCROLL_DIRECTION_UP
            onNestedVerticalPreScroll(coordinatorLayout, child, target, dx, dy, consumed, preScrollDirection)
        } else if (dy < 0 && totalDy > 0) {
            totalDy = 0
            preScrollDirection = SCROLL_DIRECTION_DOWN
            onNestedVerticalPreScroll(coordinatorLayout, child, target, dx, dy, consumed, preScrollDirection)
        }
        totalDy += dy
    }


    override fun onNestedFling(
            coordinatorLayout: CoordinatorLayout,
            child: V,
            target: View,
            velocityX: Float,
            velocityY: Float,
            consumed: Boolean
    ): Boolean {
        super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed)
        return onNestedDirectionFling(coordinatorLayout, child, target, velocityX, velocityY,
                consumed, if (velocityY > 0) SCROLL_DIRECTION_UP else SCROLL_DIRECTION_DOWN)
    }

    /**
     * @param coordinatorLayout the CoordinatorLayout parent of the view this Behavior is
     * associated with
     * @param child             the child view of the CoordinatorLayout this Behavior is associated with
     * @param scrollDirection   Direction of the scroll: SCROLL_DIRECTION_UP, SCROLL_DIRECTION_DOWN
     * @param currentOverScroll Unconsumed value, negative or positive based on the direction;
     * @param totalScroll       Cumulative value for current direction (Unconsumed)
     */
    abstract fun onNestedVerticalScrollUnconsumed(
            coordinatorLayout: CoordinatorLayout,
            child: V,
            scrollDirection: Int,
            currentOverScroll: Int,
            totalScroll: Int
    )

    /**
     * @param coordinatorLayout   the CoordinatorLayout parent of the view this Behavior is
     * associated with
     * @param child               the child view of the CoordinatorLayout this Behavior is associated with
     * @param scrollDirection     Direction of the scroll: SCROLL_DIRECTION_UP, SCROLL_DIRECTION_DOWN
     * @param currentOverScroll   Unconsumed value, negative or positive based on the direction;
     * @param totalConsumedScroll Cumulative value for current direction (Unconsumed)
     */
    abstract fun onNestedVerticalScrollConsumed(
            coordinatorLayout: CoordinatorLayout,
            child: V,
            scrollDirection: Int,
            currentOverScroll: Int,
            totalConsumedScroll: Int
    )

    /**
     * @param coordinatorLayout the CoordinatorLayout parent of the view this Behavior is
     * associated with
     * @param child             the child view of the CoordinatorLayout this Behavior is associated with
     * @param target            the descendant view of the CoordinatorLayout performing the nested scroll
     * @param dx                the raw horizontal number of pixels that the user attempted to scroll
     * @param dy                the raw vertical number of pixels that the user attempted to scroll
     * @param consumed          out parameter. consumed[0] should be set to the distance of dx that
     * was consumed, consumed[1] should be set to the distance of dy that
     * was consumed
     * @param scrollDirection   Direction of the scroll: SCROLL_DIRECTION_UP, SCROLL_DIRECTION_DOWN
     */
    abstract fun onNestedVerticalPreScroll(
            coordinatorLayout: CoordinatorLayout,
            child: V,
            target: View,
            dx: Int,
            dy: Int,
            consumed: IntArray,
            scrollDirection: Int
    )

    /**
     * @param coordinatorLayout the CoordinatorLayout parent of the view this Behavior is
     * associated with
     * @param child             the child view of the CoordinatorLayout this Behavior is associated with
     * @param target            the descendant view of the CoordinatorLayout performing the nested scroll
     * @param velocityX         horizontal velocity of the attempted fling
     * @param velocityY         vertical velocity of the attempted fling
     * @param consumed          true if the nested child view consumed the fling
     * @param scrollDirection   Direction of the scroll: SCROLL_DIRECTION_UP, SCROLL_DIRECTION_DOWN
     * @return true if the Behavior consumed the fling
     */
    protected abstract fun onNestedDirectionFling(
            coordinatorLayout: CoordinatorLayout,
            child: V,
            target: View,
            velocityX: Float,
            velocityY: Float,
            consumed: Boolean,
            scrollDirection: Int
    ): Boolean
}