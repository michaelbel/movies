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
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.LayoutHelper;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.utils.AndroidUtils;

import java.util.Locale;

public class MovieViewListBig extends FrameLayout {

    private ImageView posterImage;
    private ProgressBar progressBar;

    private TextView titleText;
    private TextView ratingText;
    private TextView voteCountText;
    private TextView releaseDateText;
    private TextView overviewText;

    private Paint paint;
    private boolean divider;
    private Rect rect = new Rect();

    public MovieViewListBig(Context context) {
        super(context);

        setForeground(Theme.selectableItemBackgroundDrawable());
        setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));

        if (paint == null) {
            paint = new Paint();
            paint.setStrokeWidth(1);
            paint.setColor(ContextCompat.getColor(context, Theme.dividerColor()));
        }

        FrameLayout posterLayout = new FrameLayout(context);
        posterLayout.setLayoutParams(LayoutHelper.makeFrame(100, 150, Gravity.START, 3, 3, 0, 3));
        addView(posterLayout);

        posterImage = new ImageView(context);
        posterImage.setScaleType(ImageView.ScaleType.FIT_XY);
        posterImage.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        posterLayout.addView(posterImage);

        progressBar = new ProgressBar(new ContextThemeWrapper(context, R.style.WhiteProgressBar));
        progressBar.setVisibility(VISIBLE);
        progressBar.setLayoutParams(LayoutHelper.makeFrame(13, 13, Gravity.CENTER));
        posterLayout.addView(progressBar);

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, 150 - 8 - 8, 100 + 12, 8, 12, 8));
        addView(linearLayout);

        titleText = new TextView(context);
        titleText.setLines(1);
        titleText.setMaxLines(1);
        titleText.setSingleLine();
        titleText.setEllipsize(TextUtils.TruncateAt.END);
        titleText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18.5F);
        titleText.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        titleText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        titleText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 0));
        linearLayout.addView(titleText);

        FrameLayout infoLayout = new FrameLayout(context);
        infoLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 4, 0, 0));
        linearLayout.addView(infoLayout);

        LinearLayout ratingLayout = new LinearLayout(context);
        ratingLayout.setOrientation(LinearLayout.HORIZONTAL);
        ratingLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 0, 0, 0, 0));
        infoLayout.addView(ratingLayout);

        LinearLayout averageLayout = new LinearLayout(context);
        averageLayout.setOrientation(LinearLayout.HORIZONTAL);
        averageLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT));
        ratingLayout.addView(averageLayout);

        ImageView averageIcon = new ImageView(context);
        averageIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_star_circle, ContextCompat.getColor(context, Theme.iconActiveColor())));
        averageIcon.setLayoutParams(LayoutHelper.makeLinear(15, 15, Gravity.START | Gravity.CENTER_VERTICAL));
        averageLayout.addView(averageIcon);

        ratingText = new TextView(context);
        ratingText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13.5F);
        ratingText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        ratingText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        ratingText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 2, 0, 0, 0));
        averageLayout.addView(ratingText);

        View dotDivider = new View(context);
        dotDivider.setBackgroundResource(R.drawable.dot_oval);
        dotDivider.setLayoutParams(LayoutHelper.makeLinear(4, 4, Gravity.CENTER_VERTICAL, 5, 0, 5, 0));
        ratingLayout.addView(dotDivider);

        LinearLayout countLayout = new LinearLayout(context);
        countLayout.setOrientation(LinearLayout.HORIZONTAL);
        countLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT));
        ratingLayout.addView(countLayout);

        voteCountText = new TextView(context);
        voteCountText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13.5F);
        voteCountText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        voteCountText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        voteCountText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.BOTTOM));
        countLayout.addView(voteCountText);

        ImageView voteCountIcon = new ImageView(context);
        voteCountIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_account_multiple, ContextCompat.getColor(context, Theme.iconActiveColor())));
        voteCountIcon.setLayoutParams(LayoutHelper.makeLinear(13, 13, Gravity.START | Gravity.BOTTOM, 2, 0, 0, 1));
        countLayout.addView(voteCountIcon);

        releaseDateText = new TextView(context);
        releaseDateText.setLines(1);
        releaseDateText.setMaxLines(1);
        releaseDateText.setSingleLine();
        releaseDateText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        releaseDateText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        releaseDateText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        releaseDateText.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.END,0, 0, 0, 0));
        infoLayout.addView(releaseDateText);

        overviewText = new TextView(context);
        overviewText.setLines(1);
        overviewText.setMaxLines(5);
        overviewText.setEllipsize(TextUtils.TruncateAt.END);
        overviewText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        overviewText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        overviewText.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        overviewText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 0, 4, 0, 0));
        linearLayout.addView(overviewText);
    }

    public MovieViewListBig setPoster(@NonNull String posterPath) {
        Picasso.with(getContext())
               .load(String.format(Locale.US, Url.TMDB_IMAGE, AndroidUtils.posterSize(), posterPath))
               .placeholder(R.drawable.movie_placeholder_old)
               .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
               .into(posterImage, new Callback() {
                   @Override
                   public void onSuccess() {
                       progressBar.setVisibility(GONE);
                   }

                   @Override
                   public void onError() {
                       progressBar.setVisibility(GONE);
                   }
               });

        return this;
    }

    public MovieViewListBig setTitle(String title) {
        if (title != null) {
            titleText.setText(title);
        }

        return this;
    }

    public MovieViewListBig setRating(String voteAverage) {
        ratingText.setText(voteAverage);
        return this;
    }

    public MovieViewListBig setVoteCount(String count) {
        voteCountText.setText(count);
        return this;
    }

    public MovieViewListBig setReleaseDate(@NonNull String releaseDate) {
        releaseDateText.setText(releaseDate);
        return this;
    }

    public MovieViewListBig setOverview(String overview) {
        overviewText.setText(overview);
        return this;
    }

    public MovieViewListBig setDivider(boolean divider) {
        this.divider = divider;
        setWillNotDraw(!divider);
        return this;
    }

    public MovieViewListBig setDivider(boolean divider, int color) {
        this.divider = divider;
        paint.setColor(color);
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