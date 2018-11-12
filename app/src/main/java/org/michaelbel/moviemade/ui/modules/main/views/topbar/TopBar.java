package org.michaelbel.moviemade.ui.modules.main.views.topbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;

import org.michaelbel.material.extensions.Extensions;
import org.michaelbel.moviemade.R;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

@CoordinatorLayout.DefaultBehavior(TopVerticalScrollBehavior.class)
public class TopBar extends FrameLayout {

    private static final Interpolator INTERPOLATOR = new LinearOutSlowInInterpolator();
    private ViewPropertyAnimatorCompat mTranslationAnimator;

    private static final int DEFAULT_ANIMATION_DURATION = 200;

    private int mRippleAnimationDuration = (int) (DEFAULT_ANIMATION_DURATION * 2.5);

    private boolean mAutoHideEnabled;
    private boolean mIsHidden = false;

    private AppCompatTextView toolbarTitle;

    public TopBar(Context context) {
        this(context, null);
    }

    public TopBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttrs(context, attrs);
        init();
    }

    private void parseAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.BottomNavigationBar, 0, 0);
            mAutoHideEnabled = true;
            mRippleAnimationDuration = (int) (DEFAULT_ANIMATION_DURATION * 2.5);
            typedArray.recycle();
        }
    }

    private void init() {
        setLayoutParams(new ViewGroup.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)));

        View view = LayoutInflater.from(getContext()).inflate(R.layout.topbar, this, true);
        toolbarTitle = view.findViewById(R.id.toolbar_title);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.setOutlineProvider(ViewOutlineProvider.BOUNDS);
        }

        setClipToPadding(false);
    }

    public void hide() {
        hide(true);
    }

    public void hide(boolean animate) {
        mIsHidden = true;
        setTranslationY(-(this.getHeight() + Extensions.getStatusBarHeight(getContext())), animate);
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

    public void setTitle(int textId) {
        toolbarTitle.setText(getResources().getText(textId));
    }
}