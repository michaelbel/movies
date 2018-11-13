package org.michaelbel.moviemade.modules_beta.view.cell;

import android.content.Context;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.Theme;
import org.michaelbel.moviemade.utils.ScreenUtils;

public class DividerCell extends FrameLayout {

    public DividerCell(Context context) {
        super(context);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
        params.topMargin = ScreenUtils.dp(8);
        params.bottomMargin = ScreenUtils.dp(8);

        View view = new View(context);
        view.setBackgroundColor(ContextCompat.getColor(context, R.color.divider));
        view.setLayoutParams(params);
        addView(view);
    }
}