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
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.util.ScreenUtils;

@SuppressWarnings("all")
public class GenreChip extends FrameLayout {

    private CardView cardView;
    private TextView textText;

    private Rect rect = new Rect();

    public GenreChip(Context context) {
        super(context);

        cardView = new CardView(context);
        cardView.setCardElevation(0);
        cardView.setUseCompatPadding(true);
        cardView.setRadius(ScreenUtils.dp(15));
        cardView.setPreventCornerOverlap(false);
        cardView.setForeground(Theme.selectableItemBackgroundBorderlessDrawable());
        cardView.setCardBackgroundColor(ContextCompat.getColor(context, Theme.accentColor()));
        cardView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        addView(cardView);

        textText = new TextView(context);
        textText.setMaxLines(1);
        textText.setTextColor(ContextCompat.getColor(context, Theme.foregroundColor()));
        textText.setGravity(Gravity.CENTER_VERTICAL);
        textText.setEllipsize(TextUtils.TruncateAt.END);
        textText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        textText.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER_VERTICAL, 14, 6, 14, 6));
        cardView.addView(textText);
    }

    public GenreChip setText(String text) {
        textText.setText(text);
        return this;
    }

    public GenreChip setText(@StringRes int textId) {
        textText.setText(getContext().getText(textId).toString().toUpperCase());
        return this;
    }

    public GenreChip changeLayoutParams() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.rightMargin = ScreenUtils.dp(2F);
        setLayoutParams(params);
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