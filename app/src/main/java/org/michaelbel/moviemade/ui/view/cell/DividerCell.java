package org.michaelbel.moviemade.ui.view.cell;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.util.ScreenUtils;

public class DividerCell extends FrameLayout {

    public DividerCell(Context context) {
        super(context);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
        params.topMargin = ScreenUtils.dp(8);
        params.bottomMargin = ScreenUtils.dp(8);

        View view = new View(context);
        view.setBackgroundColor(ContextCompat.getColor(context, Theme.dividerColor()));
        view.setLayoutParams(params);
        addView(view);
    }
}