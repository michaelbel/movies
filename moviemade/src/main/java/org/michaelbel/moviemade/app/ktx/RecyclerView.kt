package org.michaelbel.moviemade.app.ktx

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

val RecyclerView.ViewHolder.context: Context
    get() = itemView.context

/**
 * Моментальный скролл списка до n-го элемента сверху [RecyclerView],
 * а затем плавный скролл до первого.
 */
fun RecyclerView.smartScrollToTop() {
    val smoothScrollCount = 10
    val scrollLambda = { lastVisiblePosition: Int ->
        if (lastVisiblePosition <= smoothScrollCount) {
            smoothScrollToPosition(0)
        } else {
            scrollToPosition(smoothScrollCount)
            setOnGlobalLayoutListenerSingle {
                smoothScrollToPosition(0)
            }
        }
    }

    when (layoutManager) {
        is LinearLayoutManager -> {
            val lm: LinearLayoutManager = layoutManager as LinearLayoutManager
            scrollLambda(lm.findLastVisibleItemPosition())
        }
        is StaggeredGridLayoutManager -> {
            val lm: StaggeredGridLayoutManager = layoutManager as StaggeredGridLayoutManager
            val visiblePositions: IntArray = lm.findLastVisibleItemPositions(null)
            scrollLambda(visiblePositions.maxOrNull() ?: 0)
        }
        else -> { /* not implemented */ }
    }
}