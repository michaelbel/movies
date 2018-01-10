package org.michaelbel.moviemade.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.widget.FrameLayout;

import org.michaelbel.moviemade.app.Theme;

public class PlaceholderView extends FrameLayout {

    public PlaceholderView(@NonNull Context context) {
        super(context);

        setBackgroundColor(ContextCompat.getColor(context, Theme.backgroundColor()));
    }

    public void hide() {
        setVisibility(INVISIBLE);
    }

    public void show() {
        setVisibility(VISIBLE);
    }
}