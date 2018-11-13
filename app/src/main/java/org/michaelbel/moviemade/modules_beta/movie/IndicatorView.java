package org.michaelbel.moviemade.modules_beta.movie;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import androidx.annotation.AnimatorRes;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

import org.michaelbel.material.extensions.Extensions;
import org.michaelbel.moviemade.R;

import static androidx.viewpager.widget.ViewPager.OnPageChangeListener;

public class IndicatorView extends LinearLayout {

    private final static int DEFAULT_INDICATOR_WIDTH = 5;

    private ViewPager mViewPager;
    private Animator mAnimatorIn;
    private Animator mAnimatorOut;
    private Animator mImmediateAnimatorIn;
    private Animator mImmediateAnimatorOut;

    private int mLastPosition = -1;
    private int mIndicatorMargin = -1;
    private int mIndicatorWidth = -1;
    private int mIndicatorHeight = -1;
    private int mAnimatorReverseResId = 0;
    private int mAnimatorResId = R.animator.indicator_view_anim;
    private int mIndicatorBackgroundResId = R.drawable.indicator_circle;
    private int mIndicatorUnselectedBackgroundResId = R.drawable.indicator_circle;

    private @ColorInt int mIndicatorColor;

    public IndicatorView(Context context) {
        super(context);
        initialize(context, null, 0);
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs, 0);
    }

    public IndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IndicatorView);
        mIndicatorWidth = a.getDimensionPixelSize(R.styleable.IndicatorView_indicator_width, -1);
        mIndicatorHeight = a.getDimensionPixelSize(R.styleable.IndicatorView_indicator_height, -1);
        mIndicatorMargin = a.getDimensionPixelSize(R.styleable.IndicatorView_indicator_margin, -1);
        mAnimatorResId = a.getResourceId(R.styleable.IndicatorView_indicator_animator, R.animator.indicator_view_anim);
        mAnimatorReverseResId = a.getResourceId(R.styleable.IndicatorView_indicator_animatorReverse, 0);
        mIndicatorBackgroundResId = a.getResourceId(R.styleable.IndicatorView_indicator_drawable, R.drawable.indicator_circle);
        mIndicatorUnselectedBackgroundResId = a.getResourceId(R.styleable.IndicatorView_indicator_drawableUnselected, mIndicatorBackgroundResId);

        int orientation = a.getInt(R.styleable.IndicatorView_indicator_orientation, -1);
        setOrientation(orientation == VERTICAL ? VERTICAL : HORIZONTAL);

        int gravity = a.getInt(R.styleable.IndicatorView_indicator_gravity, -1);
        setGravity(gravity >= 0 ? gravity : Gravity.CENTER);

        a.recycle();

        mIndicatorWidth = (mIndicatorWidth < 0) ? Extensions.dp(context, DEFAULT_INDICATOR_WIDTH) : mIndicatorWidth;
        mIndicatorHeight = (mIndicatorHeight < 0) ? Extensions.dp(context, DEFAULT_INDICATOR_WIDTH) : mIndicatorHeight;
        mIndicatorMargin = (mIndicatorMargin < 0) ? Extensions.dp(context, DEFAULT_INDICATOR_WIDTH) : mIndicatorMargin;
        mAnimatorResId = (mAnimatorResId == 0) ? R.animator.indicator_view_anim : mAnimatorResId;

        mAnimatorOut = createAnimatorOut(context);
        mImmediateAnimatorOut = createAnimatorOut(context);
        mImmediateAnimatorOut.setDuration(0);

        mAnimatorIn = createAnimatorIn(context);
        mImmediateAnimatorIn = createAnimatorIn(context);
        mImmediateAnimatorIn.setDuration(0);

        mIndicatorBackgroundResId = (mIndicatorBackgroundResId == 0) ? R.drawable.indicator_circle : mIndicatorBackgroundResId;
        mIndicatorUnselectedBackgroundResId = (mIndicatorUnselectedBackgroundResId == 0) ? mIndicatorBackgroundResId : mIndicatorUnselectedBackgroundResId;
    }

    public void configureIndicator(int indicatorWidth, int indicatorHeight, int indicatorMargin) {
        configureIndicator(indicatorWidth, indicatorHeight, indicatorMargin, R.animator.indicator_view_anim, 0, R.drawable.indicator_circle, R.drawable.indicator_circle);
    }

    public void configureIndicator(int indicatorWidth, int indicatorHeight, int indicatorMargin, @AnimatorRes int animatorId, @AnimatorRes int animatorReverseId,
                                   @DrawableRes int indicatorBackgroundId, @DrawableRes int indicatorUnselectedBackgroundId) {

        mIndicatorWidth = indicatorWidth;
        mIndicatorHeight = indicatorHeight;
        mIndicatorMargin = indicatorMargin;

        mAnimatorResId = animatorId;
        mAnimatorReverseResId = animatorReverseId;
        mIndicatorBackgroundResId = indicatorBackgroundId;
        mIndicatorUnselectedBackgroundResId = indicatorUnselectedBackgroundId;
    }

    private Animator createAnimatorOut(Context context) {
        return AnimatorInflater.loadAnimator(context, mAnimatorResId);
    }

    private Animator createAnimatorIn(Context context) {
        Animator animatorIn;

        if (mAnimatorReverseResId == 0) {
            animatorIn = AnimatorInflater.loadAnimator(context, mAnimatorResId);
            animatorIn.setInterpolator(new ReverseInterpolator());
        } else {
            animatorIn = AnimatorInflater.loadAnimator(context, mAnimatorReverseResId);
        }

        return animatorIn;
    }

    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;

        if (mViewPager != null && mViewPager.getAdapter() != null) {
            mLastPosition = -1;
            createIndicators();
            mViewPager.removeOnPageChangeListener(mInternalPageChangeListener);
            mViewPager.addOnPageChangeListener(mInternalPageChangeListener);
            mInternalPageChangeListener.onPageSelected(mViewPager.getCurrentItem());
        }
    }

    public IndicatorView setIndicatorDrawable(@DrawableRes int resId) {
        mIndicatorBackgroundResId = resId;
        return this;
    }

    public IndicatorView setIndicatorDrawableUnselected(@DrawableRes int resId) {
        mIndicatorUnselectedBackgroundResId = resId;
        return this;
    }

    private final OnPageChangeListener mInternalPageChangeListener = new OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {
            if (mViewPager.getAdapter() == null || mViewPager.getAdapter().getCount() <= 0) {
                return;
            }

            if (mAnimatorIn.isRunning()) {
                mAnimatorIn.end();
                mAnimatorIn.cancel();
            }

            if (mAnimatorOut.isRunning()) {
                mAnimatorOut.end();
                mAnimatorOut.cancel();
            }

            View currentIndicator;

            if (mLastPosition >= 0 && (currentIndicator = getChildAt(mLastPosition)) != null) {
                currentIndicator.setBackgroundResource(mIndicatorUnselectedBackgroundResId);
                mAnimatorIn.setTarget(currentIndicator);
                mAnimatorIn.start();
            }

            View selectedIndicator = getChildAt(position);

            if (selectedIndicator != null) {
                selectedIndicator.setBackgroundResource(mIndicatorBackgroundResId);
                mAnimatorOut.setTarget(selectedIndicator);
                mAnimatorOut.start();
            }

            mLastPosition = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {}
    };

    public DataSetObserver getDataSetObserver() {
        return mInternalDataSetObserver;
    }

    private DataSetObserver mInternalDataSetObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();

            if (mViewPager == null) {
                return;
            }

            int newCount = mViewPager.getAdapter().getCount();
            int currentCount = getChildCount();

            if (newCount == currentCount) {
                return;
            } else if (mLastPosition < newCount) {
                mLastPosition = mViewPager.getCurrentItem();
            } else {
                mLastPosition = -1;
            }

            createIndicators();
        }
    };

    private void createIndicators() {
        removeAllViews();
        int count = mViewPager.getAdapter().getCount();

        if (count <= 0) {
            return;
        }

        int currentItem = mViewPager.getCurrentItem();

        for (int i = 0; i < count; i++) {
            if (currentItem == i) {
                addIndicator(mIndicatorBackgroundResId, mImmediateAnimatorOut);
            } else {
                addIndicator(mIndicatorUnselectedBackgroundResId, mImmediateAnimatorIn);
            }
        }
    }

    private void addIndicator(@DrawableRes int backgroundDrawableId, Animator animator) {
        if (animator.isRunning()) {
            animator.end();
            animator.cancel();
        }

        View indicator = new View(getContext());
        indicator.setBackgroundResource(backgroundDrawableId);
        addView(indicator, mIndicatorWidth, mIndicatorHeight);
        LayoutParams params = (LayoutParams) indicator.getLayoutParams();
        params.leftMargin = mIndicatorMargin;
        params.rightMargin = mIndicatorMargin;
        indicator.setLayoutParams(params);

        animator.setTarget(indicator);
        animator.start();
    }

    private class ReverseInterpolator implements Interpolator {
        @Override
        public float getInterpolation(float value) {
            return Math.abs(1.0f - value);
        }
    }
}