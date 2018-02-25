package org.michaelbel.moviemade.ui.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.widget.LinearLayout;

import org.michaelbel.moviemade.R;
import org.michaelbel.core.widget.LayoutHelper;
import org.michaelbel.moviemade.app.Theme;

public class RatingViewDev extends LinearLayout {

    private StarView[] stars = new StarView[5];

    public RatingViewDev(Context context) {
        super(context);

        setOrientation(HORIZONTAL);

        int starCount = 5;
        for (int i = 0; i < starCount; i++) {
            StarView view = new StarView(context);
            view.setLayoutParams(LayoutHelper.makeLinear(14, 14));
            addView(view);
            stars[i] = view;
        }
    }

    public void setRating(int rating) {
        switch (rating) {
            case 1:
                stars[0].setIcon(StarView.ICON_STAR);
                stars[1].setIcon(StarView.ICON_STAR_BLANK);
                stars[2].setIcon(StarView.ICON_STAR_BLANK);
                stars[3].setIcon(StarView.ICON_STAR_BLANK);
                stars[4].setIcon(StarView.ICON_STAR_BLANK);
                break;
            case 2:
                stars[0].setIcon(StarView.ICON_STAR);
                stars[1].setIcon(StarView.ICON_STAR);
                stars[2].setIcon(StarView.ICON_STAR_BLANK);
                stars[3].setIcon(StarView.ICON_STAR_BLANK);
                stars[4].setIcon(StarView.ICON_STAR_BLANK);
                break;
            case 3:
                stars[0].setIcon(StarView.ICON_STAR);
                stars[1].setIcon(StarView.ICON_STAR);
                stars[2].setIcon(StarView.ICON_STAR);
                stars[3].setIcon(StarView.ICON_STAR_BLANK);
                stars[4].setIcon(StarView.ICON_STAR_BLANK);
                break;
            case 4:
                stars[0].setIcon(StarView.ICON_STAR);
                stars[1].setIcon(StarView.ICON_STAR);
                stars[2].setIcon(StarView.ICON_STAR);
                stars[3].setIcon(StarView.ICON_STAR);
                stars[4].setIcon(StarView.ICON_STAR_BLANK);
                break;
            case 5:
                stars[0].setIcon(StarView.ICON_STAR);
                stars[1].setIcon(StarView.ICON_STAR);
                stars[2].setIcon(StarView.ICON_STAR);
                stars[3].setIcon(StarView.ICON_STAR);
                stars[4].setIcon(StarView.ICON_STAR);
                break;
        }
    }

    public class StarView extends AppCompatImageView {

        public static final int ICON_STAR = 0;
        public static final int ICON_STAR_BLANK = 1;

        private Drawable icon;

        public StarView(Context context) {
            super(context);
            setImageDrawable(icon);
        }

        public void setIcon(int style) {
            if (style == ICON_STAR) {
                icon = Theme.getIcon(R.drawable.ic_star, ContextCompat.getColor(getContext(), Theme.iconActiveColor()));
            } else if (style == ICON_STAR_BLANK) {
                icon = Theme.getIcon(R.drawable.ic_star, ContextCompat.getColor(getContext(), Theme.foregroundColor()));
            }

            setImageDrawable(icon);
        }
    }
}