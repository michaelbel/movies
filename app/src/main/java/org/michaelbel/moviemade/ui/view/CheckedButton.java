package org.michaelbel.moviemade.ui.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.IntDef;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.LayoutHelper;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.util.ScreenUtils;

public class CheckedButton extends FrameLayout {

    public static final int FAVORITE = 0;
    public static final int WATCHING = 1;

    @IntDef({ FAVORITE, WATCHING })
    private @interface Style {}

    private CardView cardView;
    private ImageView iconView;

    private int style = FAVORITE;
    private Rect rect = new Rect();

    public CheckedButton(Context context) {
        super(context);

        cardView = new CardView(context);
        cardView.setCardElevation(0);
        cardView.setUseCompatPadding(true);
        cardView.setPreventCornerOverlap(false);
        cardView.setRadius(ScreenUtils.dp(8));
        cardView.setForeground(Theme.selectableItemBackgroundBorderlessDrawable());
        cardView.setCardBackgroundColor(ContextCompat.getColor(context, Theme.backgroundColor()));
        cardView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        addView(cardView);

        iconView = new ImageView(context);
        iconView.setImageDrawable(Theme.getIcon(style == FAVORITE ? R.drawable.ic_heart_outline : R.drawable.ic_bookmark_outline, ContextCompat.getColor(getContext(), Theme.iconActiveColor())));
        iconView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 12.5F, 6.5F, 12.5F, 6.5F));
        cardView.addView(iconView);
    }

    public CheckedButton setStyle(@Style int style) {
        this.style = style;
        iconView.setImageDrawable(Theme.getIcon(style == FAVORITE ? R.drawable.ic_heart_outline : R.drawable.ic_bookmark_outline, ContextCompat.getColor(getContext(), Theme.iconActiveColor())));
        return this;
    }

    public CheckedButton setChecked(boolean state) {
        int color = state ? Theme.accentColor() : Theme.iconActiveColor();
        int icon = state ?
                style == FAVORITE ? R.drawable.ic_heart : R.drawable.ic_bookmark :
                style == FAVORITE ? R.drawable.ic_heart_outline : R.drawable.ic_bookmark_outline;

        iconView.setImageDrawable(Theme.getIcon(icon, ContextCompat.getColor(getContext(), color)));
        return this;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (cardView.getForeground() != null) {
            if (rect.contains((int) event.getX(), (int) event.getY())) {
                return true;
            }

            if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                cardView.getForeground().setHotspot(event.getX(), event.getY());
            }
        }

        return super.onTouchEvent(event);
    }
}