package org.michaelbel.moviemade.ui.modules.main.appbar;

import com.google.android.material.appbar.AppBarLayout;

public abstract class AppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {

    private AppBarState mCurrentState = AppBarState.IDLE;

    @Override
    public final void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (i == 0) {
            if (mCurrentState != AppBarState.EXPANDED) {
                onStateChanged(appBarLayout, AppBarState.EXPANDED);
            }
            mCurrentState = AppBarState.EXPANDED;
        } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
            if (mCurrentState != AppBarState.COLLAPSED) {
                onStateChanged(appBarLayout, AppBarState.COLLAPSED);
            }
            mCurrentState = AppBarState.COLLAPSED;
        } else {
            if (mCurrentState != AppBarState.IDLE) {
                onStateChanged(appBarLayout, AppBarState.IDLE);
            }
            mCurrentState = AppBarState.IDLE;
        }
    }

    public abstract void onStateChanged(AppBarLayout appBarLayout, AppBarState state);
}