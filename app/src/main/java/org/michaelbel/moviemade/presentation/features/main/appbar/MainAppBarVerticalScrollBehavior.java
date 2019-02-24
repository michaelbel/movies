package org.michaelbel.moviemade.presentation.features.main.appbar;

import android.view.View;
import android.view.animation.Interpolator;

import com.google.android.material.snackbar.Snackbar;

import org.michaelbel.moviemade.presentation.common.bottombar.behaviour.VerticalScrollingBehavior;

import java.lang.ref.WeakReference;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

public class MainAppBarVerticalScrollBehavior<V extends View> extends VerticalScrollingBehavior<V> {

    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
    private int mBottomNavHeight;
    private WeakReference<MainAppBar> mViewRef;

    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull final V child, int layoutDirection) {
        parent.onLayoutChild(child, layoutDirection);
        if (child instanceof MainAppBar) {
            mViewRef = new WeakReference<>((MainAppBar) child);
        }

        child.post(() -> mBottomNavHeight = child.getHeight());
        updateSnackBarPosition(parent, child, getSnackBarInstance(parent, child));
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull V child, @NonNull View dependency) {
        return isDependent(dependency) || super.layoutDependsOn(parent, child, dependency);
    }

    private boolean isDependent(View dependency) {
        return dependency instanceof Snackbar.SnackbarLayout;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull V child, @NonNull View dependency) {
        if (isDependent(dependency)) {
            updateSnackBarPosition(parent, child, dependency);
            return false;
        }

        return super.onDependentViewChanged(parent, child, dependency);
    }

    private void updateSnackBarPosition(CoordinatorLayout parent, V child, View dependency) {
        updateSnackBarPosition(parent, child, dependency, child.getTranslationY() - child.getHeight());
    }

    private void updateSnackBarPosition(CoordinatorLayout parent, V child, View dependency, float translationY) {
        if (dependency instanceof Snackbar.SnackbarLayout) {
            ViewCompat.animate(dependency).setInterpolator(INTERPOLATOR).setDuration(80).setStartDelay(0).translationY(translationY).start();
        }
    }

    private Snackbar.SnackbarLayout getSnackBarInstance(CoordinatorLayout parent, V child) {
        final List<View> dependencies = parent.getDependencies(child);

        for (int i = 0, z = dependencies.size(); i < z; i++) {
            final View view = dependencies.get(i);
            if (view instanceof Snackbar.SnackbarLayout) {
                return (Snackbar.SnackbarLayout) view;
            }
        }

        return null;
    }

    @Override
    public void onNestedVerticalScrollUnconsumed(CoordinatorLayout coordinatorLayout, V child, @ScrollDirection int scrollDirection, int currentOverScroll, int totalScroll) {}

    @Override
    public void onNestedVerticalPreScroll(CoordinatorLayout coordinatorLayout, V child, View target, int dx, int dy, int[] consumed, @ScrollDirection int scrollDirection) {
        //handleDirection(child, scrollDirection);
    }

    @Override
    protected boolean onNestedDirectionFling(CoordinatorLayout coordinatorLayout, V child, View target, float velocityX, float velocityY, boolean consumed, @ScrollDirection int scrollDirection) {
        /*if (consumed) {
            handleDirection(child, scrollDirection);
        }*/
        return consumed;
    }

    @Override
    public void onNestedVerticalScrollConsumed(CoordinatorLayout coordinatorLayout, V child, @ScrollDirection int scrollDirection, int currentOverScroll, int totalConsumedScroll) {
        handleDirection(coordinatorLayout, child, scrollDirection);
    }

    private void handleDirection(CoordinatorLayout parent, V child, int scrollDirection) {
        MainAppBar appBar = mViewRef.get();

        if (appBar != null && appBar.isAutoHideEnabled()) {
            if (scrollDirection == ScrollDirection.SCROLL_DIRECTION_DOWN && appBar.isHidden()) {
                updateSnackBarPosition(parent, child, getSnackBarInstance(parent, child), -mBottomNavHeight);
                appBar.show();
            } else if (scrollDirection == ScrollDirection.SCROLL_DIRECTION_UP && !appBar.isHidden()) {
                updateSnackBarPosition(parent, child, getSnackBarInstance(parent, child), 0);
                appBar.hide();
            }
        }
    }
}