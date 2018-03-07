package org.michaelbel.moviemade.ui.view.cell;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.widget.ImageView;

import org.michaelbel.core.widget.LayoutHelper;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.utils.ScreenUtils;

/**
 * Date: Sun, Mar 4 2018
 * Time: 15:52 MSK
 *
 * @author Michael Bel
 */

public class ThemeCell extends TextCell {

    private ImageView iconCheckView;

    public ThemeCell(Context context, int theme) {
        super(context);

        setHeight(ScreenUtils.dp(52));

        iconCheckView = new ImageView(context);
        iconCheckView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.END | Gravity.CENTER_VERTICAL, 0, 0, 16, 0));
        addView(iconCheckView);

        if (theme == Theme.THEME_LIGHT) {
            iconCheckView.setImageDrawable(Theme.getIcon(R.drawable.ic_check, ContextCompat.getColor(context, R.color.iconActiveColor)));
        } else if (theme == Theme.THEME_NIGHT_BLUE) {
            iconCheckView.setImageDrawable(Theme.getIcon(R.drawable.ic_check, ContextCompat.getColor(context, R.color.night_blue_iconActiveColor)));
        }
    }

    public void setIconChecked(boolean value) {
        iconCheckView.setVisibility(value ? VISIBLE : INVISIBLE);
    }

    public void changeTheme() {
        ArgbEvaluator evaluator = new ArgbEvaluator();

        ObjectAnimator foregroundAnimator = ObjectAnimator.ofObject(this, "backgroundColor", evaluator, 0, 0);
        ObjectAnimator textColorAnimator = ObjectAnimator.ofObject(textView, "textColor", evaluator, 0, 0);
        ObjectAnimator dividerColorAnimator = ObjectAnimator.ofObject(paint, "color", evaluator, 0, 0);

        if (Theme.getTheme() == Theme.THEME_LIGHT) {
            foregroundAnimator.setObjectValues(ContextCompat.getColor(getContext(), R.color.night_blue_foregroundColor), ContextCompat.getColor(getContext(), R.color.foregroundColor));
            textColorAnimator.setObjectValues(ContextCompat.getColor(getContext(), R.color.night_blue_primaryTextColor), ContextCompat.getColor(getContext(), R.color.primaryTextColor));
            dividerColorAnimator.setObjectValues(ContextCompat.getColor(getContext(), R.color.night_blue_dividerColor), ContextCompat.getColor(getContext(), R.color.dividerColor));
        } else if (Theme.getTheme() == Theme.THEME_NIGHT_BLUE) {
            foregroundAnimator.setObjectValues(ContextCompat.getColor(getContext(), R.color.foregroundColor), ContextCompat.getColor(getContext(), R.color.night_blue_foregroundColor));
            textColorAnimator.setObjectValues(ContextCompat.getColor(getContext(), R.color.primaryTextColor), ContextCompat.getColor(getContext(), R.color.night_blue_primaryTextColor));
            dividerColorAnimator.setObjectValues(ContextCompat.getColor(getContext(), R.color.dividerColor), ContextCompat.getColor(getContext(), R.color.night_blue_dividerColor));
        }

        AnimatorSet set = new AnimatorSet();
        set.playTogether(foregroundAnimator, textColorAnimator, dividerColorAnimator);
        set.setDuration(300);
        set.start();
    }
}