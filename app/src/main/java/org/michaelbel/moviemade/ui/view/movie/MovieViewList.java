package org.michaelbel.moviemade.ui.view.movie;

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

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.LayoutHelper;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.ui.view.RatingView;
import org.michaelbel.moviemade.utils.AndroidUtils;

import java.util.Locale;

import static android.widget.LinearLayout.HORIZONTAL;

public class MovieViewList extends FrameLayout {

    private ImageView posterImageView;
    private TextView titleTextView;
    private TextView yearTextView;
    private RatingView ratingView;
    private TextView ratingTextView;

    private Paint paint;
    private boolean divider;
    private Rect rect = new Rect();

    public MovieViewList(Context context) {
        super(context);

        setForeground(Theme.selectableItemBackgroundDrawable());
        setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));

        if (paint == null) {
            paint = new Paint();
            paint.setStrokeWidth(1);
            paint.setColor(ContextCompat.getColor(context, Theme.dividerColor()));
        }

        posterImageView = new ImageView(context);
        posterImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        posterImageView.setLayoutParams(LayoutHelper.makeFrame(60, 90, Gravity.START, 8, 8, 0, 8));
        addView(posterImageView);

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT, 72, 0, 0, 0));
        addView(linearLayout);

        titleTextView = new TextView(context);
        titleTextView.setLines(1);
        titleTextView.setMaxLines(2);
        titleTextView.setEllipsize(TextUtils.TruncateAt.END);
        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
        titleTextView.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        titleTextView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        titleTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.TOP, 16, 16, 16, 0));
        linearLayout.addView(titleTextView);

        yearTextView = new TextView(context);
        yearTextView.setLines(1);
        yearTextView.setMaxLines(1);
        yearTextView.setSingleLine();
        yearTextView.setEllipsize(TextUtils.TruncateAt.END);
        yearTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        yearTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        yearTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.TOP, 16, 0, 12, 0));
        linearLayout.addView(yearTextView);

        LinearLayout ratingLayout = new LinearLayout(context);
        ratingLayout.setOrientation(HORIZONTAL);
        ratingLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 0));
        linearLayout.addView(ratingLayout);

        ratingView = new RatingView(context);
        ratingView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL));
        //ratingLayout.addView(ratingView);

        ratingTextView = new TextView(context);
        ratingTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        ratingTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        ratingTextView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        ratingTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, /*12*/ 0, 0, 0, 0));
        ratingLayout.addView(ratingTextView);
    }

    public MovieViewList setPoster(@NonNull String posterPath) {
        Picasso.with(getContext())
               .load(String.format(Locale.US, Url.TMDB_IMAGE, AndroidUtils.posterSize(), posterPath))
               .placeholder(R.drawable.movie_placeholder_old)
               .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
               .into(posterImageView);

        return this;
    }

    public MovieViewList setTitle(@NonNull String title) {
        if (title != null) {
            titleTextView.setText(title);
        }

        return this;
    }

    public MovieViewList setYear(@NonNull String date) {
        if (date != null) {
            if (date.length() >= 4) {
                yearTextView.setText(date.substring(0, 4));
            }
        }

        return this;
    }

    public MovieViewList setVoteAverage(float voteAverage) {
        ratingView.setRating(voteAverage);
        ratingTextView.setText(String.valueOf(voteAverage));
        return this;
    }

    public MovieViewList setDivider(boolean divider) {
        this.divider = divider;
        setWillNotDraw(!divider);
        return this;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.EXACTLY);
        int height = getMeasuredHeight();
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