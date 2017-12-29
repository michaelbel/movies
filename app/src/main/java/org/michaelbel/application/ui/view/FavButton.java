package org.michaelbel.application.ui.view;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.util.ScreenUtils;

@SuppressWarnings("all")
public class FavButton extends FrameLayout {

    private CardView cardView;
    private ImageView iconView;
    private TextView textText;

    private Rect rect = new Rect();

    public FavButton(Context context) {
        super(context);

        cardView = new CardView(context);
        cardView.setCardElevation(0);
        cardView.setUseCompatPadding(true);
        cardView.setRadius(ScreenUtils.dp(10));
        cardView.setPreventCornerOverlap(false);
        cardView.setForeground(Theme.selectableItemBackgroundBorderlessDrawable());
        cardView.setCardBackgroundColor(ContextCompat.getColor(context, Theme.accentColor()));
        cardView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        addView(cardView);

        iconView = new ImageView(context);
        iconView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 12, 6, 0, 6));
        cardView.addView(iconView);

        textText = new TextView(context);
        textText.setLines(1);
        textText.setMaxLines(1);
        textText.setSingleLine();
        textText.setGravity(Gravity.CENTER_VERTICAL);
        textText.setEllipsize(TextUtils.TruncateAt.END);
        textText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textText.setTextColor(ContextCompat.getColor(context, Theme.foregroundColor()));
        textText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        textText.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER_VERTICAL, 24 + 12 + 10, 0, 12, 0));
        cardView.addView(textText);
    }

    public FavButton setIcon(int icon) {
        iconView.setImageDrawable(Theme.getIcon(icon, ContextCompat.getColor(getContext(), Theme.foregroundColor())));
        return this;
    }

    public FavButton setText(@StringRes int textId) {
        textText.setText(getContext().getText(textId).toString().toUpperCase());
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