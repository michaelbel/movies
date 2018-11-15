package org.michaelbel.moviemade.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.michaelbel.moviemade.utils.DrawableUtil;
import org.michaelbel.moviemade.utils.LayoutHelper;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.utils.Theme;

public class ReviewView extends FrameLayout {

    private TextView authorText;
    private TextView reviewText;

    private Paint paint;
    private boolean divider;
    private Rect rect = new Rect();

    public ReviewView(Context context) {
        super(context);

        setForeground(DrawableUtil.INSTANCE.selectableItemBackgroundDrawable(context));
        setBackgroundColor(ContextCompat.getColor(context, R.color.foreground));

        if (paint == null) {
            paint = new Paint();
            paint.setStrokeWidth(1);
            paint.setColor(ContextCompat.getColor(context, R.color.divider));
        }

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT, 12, 12, 12, 12));
        addView(layout);

        LinearLayout layout1 = new LinearLayout(context);
        layout1.setOrientation(LinearLayout.HORIZONTAL);
        layout.addView(layout1);

        ImageView userIcon = new ImageView(context);
        userIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_account, ContextCompat.getColor(context, R.color.primaryText)));
        userIcon.setLayoutParams(LayoutHelper.makeLinear(18, 18, Gravity.START | Gravity.CENTER_VERTICAL));
        layout1.addView(userIcon);

        authorText = new TextView(context);
        authorText.setMaxLines(1);
        authorText.setEllipsize(TextUtils.TruncateAt.END);
        authorText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        authorText.setTextColor(ContextCompat.getColor(context, R.color.primaryText));
        authorText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        authorText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 4, 0, 0, 0));
        layout1.addView(authorText);

        reviewText = new TextView(context);
        reviewText.setMaxLines(5);
        reviewText.setEllipsize(TextUtils.TruncateAt.END);
        reviewText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        reviewText.setTextColor(ContextCompat.getColor(context, R.color.secondaryText));
        reviewText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.TOP, 0, 2, 0, 0));
        layout.addView(reviewText);
    }

    public ReviewView setAuthor(@NonNull String name) {
        authorText.setText(name);
        return this;
    }

    public ReviewView setContent(@NonNull String text) {
        reviewText.setText(text);
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