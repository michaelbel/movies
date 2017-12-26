package org.michaelbel.application.ui.view;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.moviemade.annotation.Beta;

@Beta
public class LoadingView extends FrameLayout {

    private ProgressBar progressBar;

    public LoadingView(Context context) {
        super(context);

        progressBar = new ProgressBar(context);
        progressBar.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT,
                LayoutHelper.WRAP_CONTENT, Gravity.CENTER));
        addView(progressBar);
    }
}