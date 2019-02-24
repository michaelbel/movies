package org.michaelbel.moviemade.presentation.features.main.appbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

import com.google.android.material.appbar.AppBarLayout;

import org.michaelbel.moviemade.core.utils.DeviceUtil;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

@CoordinatorLayout.DefaultBehavior(MainAppBarVerticalScrollBehavior.class)
public class MainAppBar extends AppBarLayout {

    private static final Interpolator INTERPOLATOR = new LinearOutSlowInInterpolator();
    private ViewPropertyAnimatorCompat mTranslationAnimator;

    private static final int DEFAULT_ANIMATION_DURATION = 200;

    private int mRippleAnimationDuration = (int) (DEFAULT_ANIMATION_DURATION * 2.5);

    private boolean mAutoHideEnabled = true;
    private boolean mIsHidden = false;

    public MainAppBar(Context context) {
        super(context);
    }

    public MainAppBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void hide() {
        hide(true);
    }

    public void hide(boolean animate) {
        mIsHidden = true;
        setTranslationY(-(this.getHeight() + DeviceUtil.INSTANCE.getStatusBarHeight(getContext())), animate);
    }

    public void show() {
        show(true);
    }

    public void show(boolean animate) {
        mIsHidden = false;
        setTranslationY(0, animate);
    }

    private void setTranslationY(int offset, boolean animate) {
        if (animate) {
            animateOffset(offset);
        } else {
            if (mTranslationAnimator != null) {
                mTranslationAnimator.cancel();
            }
            this.setTranslationY(offset);
        }
    }

    private void animateOffset(final int offset) {
        if (mTranslationAnimator == null) {
            mTranslationAnimator = ViewCompat.animate(this);
            mTranslationAnimator.setDuration(mRippleAnimationDuration);
            mTranslationAnimator.setInterpolator(INTERPOLATOR);
        } else {
            mTranslationAnimator.cancel();
        }
        mTranslationAnimator.translationY(offset).start();
    }

    public boolean isHidden() {
        return mIsHidden;
    }

    public boolean isAutoHideEnabled() {
        return mAutoHideEnabled;
    }
}