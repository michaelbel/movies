package org.michaelbel.moviemade.utils;

import android.widget.FrameLayout;
import android.widget.LinearLayout;

import org.michaelbel.moviemade.Moviemade;

public class LayoutHelper {

    public static final int WRAP_CONTENT = -2;

    private static int getSize(float size) {
        return (int) (size < 0 ? size : DeviceUtil.INSTANCE.dp(Moviemade.AppContext, size));
    }

    public static FrameLayout.LayoutParams makeFrame(int width, int height, int gravity, float start, float top, float end, float bottom) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(getSize(width), getSize(height));
        params.gravity = gravity;
        params.leftMargin = getSize(start);
        params.topMargin = getSize(top);
        params.rightMargin = getSize(end);
        params.bottomMargin = getSize(bottom);
        return params;
    }

    public static LinearLayout.LayoutParams makeLinear(int width, int height) {
        return new LinearLayout.LayoutParams(getSize(width), getSize(height));
    }
}