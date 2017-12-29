package org.michaelbel.application.ui.view;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import org.michaelbel.application.moviemade.LayoutHelper;

@SuppressWarnings("all")
public class LoadingView extends FrameLayout {

    public LoadingView(Context context) {
        super(context);

        ProgressBar progressBar = new ProgressBar(context);
        progressBar.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));
        addView(progressBar);
    }
}