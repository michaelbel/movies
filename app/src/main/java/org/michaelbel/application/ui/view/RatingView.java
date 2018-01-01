package org.michaelbel.application.ui.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.widget.LinearLayout;

import org.michaelbel.application.R;
import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.moviemade.Theme;

@SuppressWarnings("all")
public class RatingView extends LinearLayout {

    private StarView[] stars = new StarView[5];

    public RatingView(Context context) {
        super(context);

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

        /*for(StarView star: stars) {
            for (int i = 0; i < a; i++) {
                star.setIcon(StarView.ICON_STAR);
            }

            if (c == 5) {
                star.setIcon(StarView.ICON_STAR_HALF);
            } else {
                star.setIcon(StarView.ICON_STAR_BORDER);
            }

            star.setIcon(StarView.ICON_STAR_BORDER);
        }*/

        /*Log.e("Проверочка", "Число перед запятой: " + a); // 3
        Log.e("Проверочка", "Число после запятой: " + b); // 9

        for (int i = 0; i < stars.length; i++) {
            for (int j = 0; j < a; j++) {
                stars[i].setIcon(StarView.ICON_STAR);
            }

            if (b == 0) {
                c = 5 - a;
                for (int k = 0; k < c; k++) {
                    stars[i].setIcon(StarView.ICON_STAR_BORDER);
                }
            } else {
                if (b <= 5) {
                    c = 5 - a;
                    for (int k = 0; k < c; k++) {
                        stars[i].setIcon(StarView.ICON_STAR_BORDER);
                    }
                } else {
                    stars[i].setIcon(StarView.ICON_STAR_HALF);

                }
            }
        }*/
    }

    public class StarView extends AppCompatImageView {

        public static final int ICON_STAR = 0;
        public static final int ICON_STAR_HALF = 1;
        public static final int ICON_STAR_BORDER = 2;

        private Drawable icon;

        public StarView(Context context) {
            super(context);

            //icon = Theme.getIcon(R.drawable.ic_star, ContextCompat.getColor(context, Theme.iconActiveColor()));
            setImageDrawable(icon);
        }

        public void setIcon(int style) {
            if (style == ICON_STAR) {
                icon = Theme.getIcon(R.drawable.ic_star, ContextCompat.getColor(getContext(), Theme.accentColor()));
            } else if (style == ICON_STAR_HALF) {
                icon = Theme.getIcon(R.drawable.ic_star_half, ContextCompat.getColor(getContext(), Theme.accentColor()));
            } else if (style == ICON_STAR_BORDER) {
                icon = Theme.getIcon(R.drawable.ic_star_border, ContextCompat.getColor(getContext(), Theme.accentColor()));
            }

            setImageDrawable(icon);
        }
    }
}