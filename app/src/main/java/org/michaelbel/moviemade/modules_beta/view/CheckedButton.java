package org.michaelbel.moviemade.modules_beta.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;

import org.michaelbel.material.extensions.Extensions;
import org.michaelbel.moviemade.LayoutHelper;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.Theme;
import org.michaelbel.moviemade.extensions.DeviceUtil;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

public class CheckedButton extends FrameLayout {

    public static final int FAVORITE = 0, WATCHING = 1;

    @IntDef({ FAVORITE, WATCHING })
    private @interface Style {}

    private CardView cardView;
    private ImageView iconView;

    private int style = FAVORITE;
    private Rect rect = new Rect();

    public CheckedButton(Context context) {
        super(context);
        initialize(context);
    }

    public CheckedButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public CheckedButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {
        cardView = new CardView(context);
        cardView.setCardElevation(0);
        cardView.setUseCompatPadding(true);
        cardView.setPreventCornerOverlap(false);
        cardView.setRadius(DeviceUtil.INSTANCE.dp(context,8));
        cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.primary));
        cardView.setForeground(Extensions.selectableItemBackgroundBorderlessDrawable(context));
        cardView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        addView(cardView);

        iconView = new ImageView(context);
        iconView.setImageDrawable(Theme.getIcon(style == FAVORITE ? R.drawable.ic_heart_outline : R.drawable.ic_bookmark_outline, ContextCompat.getColor(getContext(), R.color.iconActive)));
        iconView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 12.5F, 6.5F, 12.5F, 6.5F));
        cardView.addView(iconView);
    }

    public CheckedButton setStyle(@Style int style) {
        this.style = style;
        iconView.setImageDrawable(Theme.getIcon(style == FAVORITE ? R.drawable.ic_heart_outline : R.drawable.ic_bookmark_outline, ContextCompat.getColor(getContext(), Theme.iconActiveColor())));
        return this;
    }

    public void setChecked(boolean state) {
        int color = state ? R.color.accent : R.color.iconActive;
        int icon = state ?
                style == FAVORITE ? R.drawable.ic_heart : R.drawable.ic_bookmark :
                style == FAVORITE ? R.drawable.ic_heart_outline : R.drawable.ic_bookmark_outline;
        iconView.setImageDrawable(Theme.getIcon(icon, ContextCompat.getColor(getContext(), color)));
    }

    @SuppressLint("ClickableViewAccessibility")
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