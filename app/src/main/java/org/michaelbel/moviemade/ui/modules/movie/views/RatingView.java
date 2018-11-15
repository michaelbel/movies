package org.michaelbel.moviemade.ui.modules.movie.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.utils.Theme;
import org.michaelbel.moviemade.utils.LayoutHelper;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

public class RatingView extends LinearLayout {

    private StarView[] stars = new StarView[5];

    public RatingView(Context context) {
        super(context, null);
        initialize(context);
    }

    public RatingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        initialize(context);
    }

    public RatingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {
        setOrientation(HORIZONTAL);

        int starCount = 5;
        for (int i = 0; i < starCount; i++) {
            StarView view = new StarView(context);
            view.setLayoutParams(LayoutHelper.makeLinear(20, 20));
            addView(view);
            stars[i] = view;
        }
    }

    public void setRating(float rating) {
        float myRating = rating / 2;

        int a = (int) myRating;
        int b = (int) (10 * (myRating - a));
        int c;
        c = (b >= 5) ? 5 : 0;

        if (c == 0) {
            switch (a) {
                case 0:
                    for(StarView star : stars) {
                        star.setIcon(StarView.ICON_STAR_BORDER);
                    }
                    break;
                case 1:
                    stars[0].setIcon(StarView.ICON_STAR);
                    stars[1].setIcon(StarView.ICON_STAR_BORDER);
                    stars[2].setIcon(StarView.ICON_STAR_BORDER);
                    stars[3].setIcon(StarView.ICON_STAR_BORDER);
                    stars[4].setIcon(StarView.ICON_STAR_BORDER);
                    break;
                case 2:
                    stars[0].setIcon(StarView.ICON_STAR);
                    stars[1].setIcon(StarView.ICON_STAR);
                    stars[2].setIcon(StarView.ICON_STAR_BORDER);
                    stars[3].setIcon(StarView.ICON_STAR_BORDER);
                    stars[4].setIcon(StarView.ICON_STAR_BORDER);
                    break;
                case 3:
                    stars[0].setIcon(StarView.ICON_STAR);
                    stars[1].setIcon(StarView.ICON_STAR);
                    stars[2].setIcon(StarView.ICON_STAR);
                    stars[3].setIcon(StarView.ICON_STAR_BORDER);
                    stars[4].setIcon(StarView.ICON_STAR_BORDER);
                    break;
                case 4:
                    stars[0].setIcon(StarView.ICON_STAR);
                    stars[1].setIcon(StarView.ICON_STAR);
                    stars[2].setIcon(StarView.ICON_STAR);
                    stars[3].setIcon(StarView.ICON_STAR);
                    stars[4].setIcon(StarView.ICON_STAR_BORDER);
                    break;
                case 5:
                    for(StarView star : stars) {
                        star.setIcon(StarView.ICON_STAR);
                    }
                    break;
            }
        } else if (c == 5) {
            switch (a) {
                case 0:
                    stars[0].setIcon(StarView.ICON_STAR_HALF);
                    stars[1].setIcon(StarView.ICON_STAR_BORDER);
                    stars[2].setIcon(StarView.ICON_STAR_BORDER);
                    stars[3].setIcon(StarView.ICON_STAR_BORDER);
                    stars[4].setIcon(StarView.ICON_STAR_BORDER);
                    break;
                case 1:
                    stars[0].setIcon(StarView.ICON_STAR);
                    stars[1].setIcon(StarView.ICON_STAR_HALF);
                    stars[2].setIcon(StarView.ICON_STAR_BORDER);
                    stars[3].setIcon(StarView.ICON_STAR_BORDER);
                    stars[4].setIcon(StarView.ICON_STAR_BORDER);
                    break;
                case 2:
                    stars[0].setIcon(StarView.ICON_STAR);
                    stars[1].setIcon(StarView.ICON_STAR);
                    stars[2].setIcon(StarView.ICON_STAR_HALF);
                    stars[3].setIcon(StarView.ICON_STAR_BORDER);
                    stars[4].setIcon(StarView.ICON_STAR_BORDER);
                    break;
                case 3:
                    stars[0].setIcon(StarView.ICON_STAR);
                    stars[1].setIcon(StarView.ICON_STAR);
                    stars[2].setIcon(StarView.ICON_STAR);
                    stars[3].setIcon(StarView.ICON_STAR_HALF);
                    stars[4].setIcon(StarView.ICON_STAR_BORDER);
                    break;
                case 4:
                    stars[0].setIcon(StarView.ICON_STAR);
                    stars[1].setIcon(StarView.ICON_STAR);
                    stars[2].setIcon(StarView.ICON_STAR);
                    stars[3].setIcon(StarView.ICON_STAR);
                    stars[4].setIcon(StarView.ICON_STAR_HALF);
                    break;
            }
        }
    }

    private class StarView extends AppCompatImageView {

        public static final int ICON_STAR = 0;
        public static final int ICON_STAR_HALF = 1;
        public static final int ICON_STAR_BORDER = 2;

        private Drawable icon;

        public StarView(Context context) {
            super(context);
        }

        public void setIcon(int style) {
            if (style == ICON_STAR) {
                icon = Theme.getIcon(R.drawable.ic_star, ContextCompat.getColor(getContext(), R.color.accent_yellow));
            } else if (style == ICON_STAR_HALF) {
                icon = Theme.getIcon(R.drawable.ic_star_half, ContextCompat.getColor(getContext(), R.color.accent_yellow));
            } else if (style == ICON_STAR_BORDER) {
                icon = Theme.getIcon(R.drawable.ic_star_border, ContextCompat.getColor(getContext(), R.color.accent_yellow));
            }

            setImageDrawable(icon);
        }
    }
}