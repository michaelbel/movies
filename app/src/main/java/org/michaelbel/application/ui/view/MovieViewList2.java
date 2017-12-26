package org.michaelbel.application.ui.view;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.bumptech.glide.Glide;

import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.moviemade.Theme;

public class MovieViewList2 extends FrameLayout {

    public int movieId;

    private ImageView posterImage;
    private TextView titleText;
    private TextView yearText;
    private TextView ratingText;

    private Paint paint;
    private boolean divider;
    private Rect rect = new Rect();

    public MovieViewList2(Context context) {
        super(context);

        setForeground(Theme.selectableItemBackgroundBorderlessDrawable());
        setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));

        if (paint == null) {
            paint = new Paint();
            paint.setStrokeWidth(1);
            paint.setColor(ContextCompat.getColor(context, Theme.dividerColor()));
        }

        posterImage = new ImageView(context);
        posterImage.setScaleType(ImageView.ScaleType.FIT_XY);
        posterImage.setLayoutParams(LayoutHelper.makeFrame(45, 65,
                Gravity.START | Gravity.CENTER_VERTICAL, 6, 6, 0, 6));
        addView(posterImage);

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT,
                LayoutHelper.MATCH_PARENT, 45 + 16, 12, 0, 12));
        addView(layout);

        titleText = new TextView(context);
        titleText.setMaxLines(1);
        titleText.setGravity(Gravity.CENTER_VERTICAL);
        titleText.setEllipsize(TextUtils.TruncateAt.END);
        titleText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        titleText.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        titleText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        titleText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT,
                LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.TOP, 0, 0, 12, 0));
        layout.addView(titleText);

        yearText = new TextView(context);
        yearText.setMaxLines(1);
        yearText.setGravity(Gravity.CENTER_VERTICAL);
        yearText.setEllipsize(TextUtils.TruncateAt.END);
        yearText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        yearText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        yearText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT,
                LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.TOP, 0, 0, 12, 0));
        layout.addView(yearText);

        ratingText = new TextView(context);
        ratingText.setMaxLines(1);
        ratingText.setGravity(Gravity.CENTER_VERTICAL);
        ratingText.setEllipsize(TextUtils.TruncateAt.END);
        ratingText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        ratingText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        ratingText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT,
                LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.TOP, 0, 0, 12, 0));
        layout.addView(ratingText);
    }

    public void setPoster(@NonNull String posterPath) {
        SharedPreferences prefs = getContext().getSharedPreferences("main_config", Context.MODE_PRIVATE);
        String imageQualityPoster = prefs.getString("image_quality_poster", "w342");

        Glide.with(getContext())
                .load("http://image.tmdb.org/t/p/" + imageQualityPoster + "/" + posterPath)
                //.placeholder(R.drawable.movie_placeholder)
                .into(posterImage);
    }

    public void setTitle(@NonNull String title) {
        titleText.setText(title);
    }

    public void setYear(@NonNull String date) {
        if (date.length() >= 4) {
            yearText.setText(date.substring(0, 4));
        }
    }

    public void setRating(float rating) {
        ratingText.setText(String.valueOf(rating));
    }

    public void setDivider(boolean divider) {
        this.divider = divider;
        setWillNotDraw(!divider);
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