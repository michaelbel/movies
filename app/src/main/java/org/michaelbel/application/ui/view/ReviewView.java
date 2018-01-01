package org.michaelbel.application.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.michaelbel.application.R;
import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.moviemade.Theme;

@SuppressWarnings("all")
public class ReviewView extends FrameLayout {

    private TextView authorTextView;
    private TextView contentTextView;

    private Paint paint;
    private boolean divider;
    private Rect rect = new Rect();

    public ReviewView(Context context) {
        super(context);

        setForeground(Theme.selectableItemBackgroundDrawable());
        setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));

        if (paint == null) {
            paint = new Paint();
            paint.setStrokeWidth(1);
            paint.setColor(ContextCompat.getColor(context, Theme.dividerColor()));
        }

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT, 12, 12, 12, 12));
        addView(layout);

        LinearLayout layout1 = new LinearLayout(context);
        layout1.setOrientation(LinearLayout.HORIZONTAL);
        layout.addView(layout1);

        ImageView userImageView = new ImageView(context);
        userImageView.setImageDrawable(Theme.getIcon(R.drawable.ic_account, ContextCompat.getColor(context, Theme.primaryTextColor())));
        userImageView.setLayoutParams(LayoutHelper.makeLinear(18, 18, Gravity.START | Gravity.CENTER_VERTICAL));
        layout1.addView(userImageView);

        authorTextView = new TextView(context);
        authorTextView.setMaxLines(1);
        authorTextView.setEllipsize(TextUtils.TruncateAt.END);
        authorTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        authorTextView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        authorTextView.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        authorTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 4, 0, 0, 0));
        layout1.addView(authorTextView);

        contentTextView = new TextView(context);
        contentTextView.setMaxLines(5);
        contentTextView.setEllipsize(TextUtils.TruncateAt.END);
        contentTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        contentTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        contentTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.TOP, 0, 2, 0, 0));
        layout.addView(contentTextView);
    }

    public ReviewView setAuthor(@NonNull String name) {
        authorTextView.setText(name);
        return this;
    }

    public ReviewView setContent(@NonNull String text) {
        contentTextView.setText(text);
        return this;
    }

    public ReviewView setDivider(boolean divider) {
        this.divider = divider;
        setWillNotDraw(!divider);
        return this;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.EXACTLY);
        int height = getMeasuredHeight() + (divider ? 1 : 0);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (divider) {
            canvas.drawLine(getPaddingLeft(), getHeight() - 1, getWidth() - getPaddingRight(), getHeight() - 1, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getForeground() != null) {
            if (rect.contains((int) event.getX(), (int) event.getY())) {
                return true;
            }

            if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                getForeground().setHotspot(event.getX(), event.getY());
            }
        }

        return super.onTouchEvent(event);
    }
}