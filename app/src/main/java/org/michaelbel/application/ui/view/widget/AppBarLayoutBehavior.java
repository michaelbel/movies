package org.michaelbel.application.ui.view.widget;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

@SuppressWarnings("all")
public class AppBarLayoutBehavior extends AppBarLayout.Behavior {

    private boolean enabled;

    public AppBarLayoutBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout parent, AppBarLayout child, View targetChild, View target, int nestedScrollAxes) {
        return enabled && super.onStartNestedScroll(parent, child, targetChild, target, nestedScrollAxes);
    }
}