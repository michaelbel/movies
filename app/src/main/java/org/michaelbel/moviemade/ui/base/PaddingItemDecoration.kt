package org.michaelbel.moviemade.ui.base

import android.graphics.Rect
import android.view.View

import androidx.recyclerview.widget.RecyclerView

class PaddingItemDecoration : RecyclerView.ItemDecoration {

    private var itemOffset: Int = 0

    constructor()

    constructor(itemOffset: Int) {
        this.itemOffset = itemOffset
    }

    fun setOffset(itemOffset: Int): PaddingItemDecoration {
        this.itemOffset = itemOffset
        return this
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(itemOffset, itemOffset, itemOffset, itemOffset)
    }
}