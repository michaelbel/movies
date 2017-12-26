package org.michaelbel.application.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.michaelbel.application.R;
import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.util.ScreenUtils;

public class MoviePageView extends FrameLayout {

    private TextView textView;
    private ImageView iconView;

    private Paint paint;
    private boolean divider;

    public MoviePageView(Context context) {
        super(context);

        setForeground(Theme.selectableItemBackgroundBorderlessDrawable());
        setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));

        if (paint == null) {
            paint = new Paint();
            paint.setStrokeWidth(1);
            paint.setColor(ContextCompat.getColor(context, Theme.dividerColor()));
        }

        iconView = new ImageView(context);
        iconView.setScaleType(ImageView.ScaleType.FIT_XY);
        iconView.setLayoutParams(LayoutHelper.makeFrame(24, 24,
                Gravity.START | Gravity.CENTER_VERTICAL, 16, 0, 0, 0));
        //addView(iconView);

        textView = new AppCompatTextView(context);
        textView.setLines(1);
        textView.setMaxLines(1);
        textView.setSingleLine();
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        textView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT,
                Gravity.START | Gravity.CENTER_VERTICAL, 16, 0, 56, 0));
        addView(textView);

        ImageView iconView2 = new ImageView(context);
        iconView2.setImageDrawable(Theme.getIcon(R.drawable.ic_chevron_right,
                ContextCompat.getColor(getContext(), Theme.iconActiveColor())));
        iconView2.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT,
                Gravity.END | Gravity.CENTER_VERTICAL, 0, 0, 12, 0));
        addView(iconView2);
    }

    public MoviePageView setText(@NonNull String text) {
        textView.setText(text);
        return this;
    }

    public MoviePageView setIcon(@NonNull Drawable icon) {
        iconView.setImageDrawable(icon);
        return this;
    }

    public MoviePageView setDivider(boolean divider) {
        this.divider = divider;
        setWillNotDraw(!divider);
        return this;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.EXACTLY);
        int height = ScreenUtils.dp(48) + (divider ? 1 : 0);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (divider) {
            canvas.drawLine(getPaddingLeft(), getHeight() - 1, getWidth() - getPaddingRight(), getHeight() - 1, paint);
        }
    }
}