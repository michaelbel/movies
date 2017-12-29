package org.michaelbel.application.ui.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.michaelbel.application.R;
import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.util.ScreenUtils;

@SuppressWarnings("all")
public class CheckedButton extends FrameLayout {

    public static final int FAVORITE = 1;
    public static final int WATCHING = 2;

    private int style;

    private CardView cardView;
    private ImageView iconView;
    private TextView textText;

    private Rect rect = new Rect();

    public CheckedButton(Context context) {
        super(context);

        style = FAVORITE;

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

        /*textText = new TextView(context);
        textText.setLines(1);
        textText.setMaxLines(1);
        textText.setSingleLine();
        textText.setGravity(Gravity.CENTER_VERTICAL);
        textText.setEllipsize(TextUtils.TruncateAt.END);
        textText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textText.setTextColor(ContextCompat.getColor(context, Theme.foregroundColor()));
        textText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        textText.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER_VERTICAL, 24 + 12 + 10, 0, 12, 0));
        cardView.addView(textText);*/
    }

    public CheckedButton setStyle(int style) {
        this.style = style;
        return this;
    }

    public CheckedButton setChecked(boolean state) {
        int color = state ? Theme.accentColor() : Theme.primaryTextColor();
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