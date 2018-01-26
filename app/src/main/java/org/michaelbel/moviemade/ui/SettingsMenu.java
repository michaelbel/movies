package org.michaelbel.moviemade.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.alexvasilkov.android.commons.state.InstanceState;
import com.alexvasilkov.android.commons.state.InstanceStateManager;
import com.alexvasilkov.gestures.Settings;
import com.alexvasilkov.gestures.views.interfaces.GestureView;

import org.michaelbel.moviemade.app.annotation.Beta;

@Beta
public class SettingsMenu implements SettingsController {

    private static final float OVERSCROLL = 32f;
    private static final long SLOW_ANIMATIONS = 1500L;

    @InstanceState
    private boolean isRotationEnabled = false;
    @InstanceState
    private boolean isRestrictRotation = false;
    @InstanceState
    private boolean isOverscrollXEnabled = false;
    @InstanceState
    private boolean isOverscrollYEnabled = false;
    @InstanceState
    private boolean isOverzoomEnabled = true;
    @InstanceState
    private boolean isExitEnabled = true;
    @InstanceState
    private Settings.Fit fitMethod = Settings.Fit.INSIDE;
    @InstanceState
    private int gravity = Gravity.CENTER;
    @InstanceState
    private boolean isSlow = false;

    public void onSaveInstanceState(Bundle outState) {
        InstanceStateManager.saveInstanceState(this, outState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        InstanceStateManager.restoreInstanceState(this, savedInstanceState);
    }

    @Override
    public void apply(GestureView view) {
        Context context = ((View) view).getContext();
        float overscrollX = isOverscrollXEnabled ? OVERSCROLL : 0f;
        float overscrollY = isOverscrollYEnabled ? OVERSCROLL : 0f;
        float overzoom = isOverzoomEnabled ? Settings.OVERZOOM_FACTOR : 1f;

        boolean isPanEnabled = true;
        boolean isZoomEnabled = true;
        boolean isFillViewport = true;
        view.getController().getSettings()
                .setPanEnabled(isPanEnabled)
                .setZoomEnabled(isZoomEnabled)
                .setDoubleTapEnabled(isZoomEnabled)
                .setRotationEnabled(isRotationEnabled)
                .setRestrictRotation(isRestrictRotation)
                .setOverscrollDistance(context, overscrollX, overscrollY)
                .setOverzoomFactor(overzoom)
                .setExitEnabled(isExitEnabled)
                .setFillViewport(isFillViewport)
                .setFitMethod(fitMethod)
                .setGravity(gravity)
                .setAnimationsDuration(isSlow ? SLOW_ANIMATIONS : Settings.ANIMATIONS_DURATION);
    }
}