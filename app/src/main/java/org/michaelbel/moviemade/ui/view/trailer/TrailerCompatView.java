package org.michaelbel.moviemade.ui.view.trailer;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.LayoutHelper;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.utils.ScreenUtils;

import java.util.Locale;

public class TrailerCompatView extends FrameLayout {

    private CardView cardView;
    private ImageView trailerImage;
    private ImageView playerImage;
    private TextView titleText;
    private TextView qualityText;

    private Rect rect = new Rect();

    public TrailerCompatView(Context context) {
        super(context);

        cardView = new CardView(context);
        cardView.setUseCompatPadding(true);
        cardView.setPreventCornerOverlap(false);
        cardView.setForeground(Theme.selectableItemBackgroundBorderlessDrawable());
        cardView.setCardBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));
        cardView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        addView(cardView);

        trailerImage = new ImageView(context);
        trailerImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        trailerImage.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, 180, Gravity.TOP));
        cardView.addView(trailerImage);

        ImageView playImageView = new ImageView(context);
        playImageView.setImageResource(R.drawable.ic_button_play);
        playImageView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 0, 0, 0, 24));
        cardView.addView(playImageView);

        FrameLayout layout = new FrameLayout(context);
        layout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, 48, Gravity.TOP, 0, 185, 0, 0));
        cardView.addView(layout);

        playerImage = new ImageView(context);
        playerImage.setVisibility(INVISIBLE);
        playerImage.setImageDrawable(Theme.getIcon(R.drawable.ic_youtube, ContextCompat.getColor(context, R.color.youtubeColor)));
        playerImage.setLayoutParams(LayoutHelper.makeFrame(24, 24, Gravity.START | Gravity.CENTER_VERTICAL, 12, 0, 0, 0));
        layout.addView(playerImage);

        titleText = new TextView(context);
        titleText.setMaxLines(1);
        titleText.setEllipsize(TextUtils.TruncateAt.END);
        titleText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        titleText.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        titleText.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 12, 0, 12, 0));
        layout.addView(titleText);

        qualityText = new TextView(context);
        qualityText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
        qualityText.setTextColor(ContextCompat.getColor(context, Theme.primaryColor()));
        qualityText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        qualityText.setBackground(ContextCompat.getDrawable(context, R.drawable.rect_quality));
        qualityText.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.END | Gravity.CENTER_VERTICAL, 0, 0, 12, 0));
        layout.addView(qualityText);
    }

    public TrailerCompatView setTitle(@NonNull String title) {
        titleText.setText(title);
        return this;
    }

    public TrailerCompatView setQuality(@NonNull String size) {
        qualityText.setText(getContext().getString(R.string.VideoSize, size));
        return this;
    }

    public TrailerCompatView setSite(String site) {
        if (site != null) {
            if (site.equals("YouTube")) {
                playerImage.setVisibility(VISIBLE);
                titleText.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 48, 0, 64, 0));
            } else {
                playerImage.setVisibility(INVISIBLE);
                titleText.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 12, 0, 50, 0));
            }
        } else {
            playerImage.setVisibility(INVISIBLE);
            titleText.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 12, 0, 50, 0));
        }

        return this;
    }

    public TrailerCompatView setTrailerImage(@NonNull String trailerKey) {
        Picasso.with(getContext())
               .load(String.format(Locale.US, Url.YOUTUBE_IMAGE, trailerKey))
               .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
               .into(trailerImage);
        return this;
    }

    public TrailerCompatView changeLayoutParams() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = ScreenUtils.dp(4F);
        params.rightMargin = ScreenUtils.dp(4F);
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        setMeasuredDimension(width, height);
    }
}