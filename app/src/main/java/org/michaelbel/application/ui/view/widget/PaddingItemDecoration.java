package org.michaelbel.application.ui.view.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

@SuppressWarnings("all")
public class PaddingItemDecoration extends RecyclerView.ItemDecoration {

    private int itemOffset;

    public PaddingItemDecoration() {
    }

    public PaddingItemDecoration(int itemOffset) {
        this.itemOffset = itemOffset;
    }

    public PaddingItemDecoration setOffset(int itemOffset) {
        this.itemOffset = itemOffset;
        return this;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(itemOffset, itemOffset, itemOffset, itemOffset);
    }
}