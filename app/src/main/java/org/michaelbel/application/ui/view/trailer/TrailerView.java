package org.michaelbel.application.ui.view.trailer;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.michaelbel.application.R;
import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.util.ScreenUtils;

@SuppressWarnings("all")
public class TrailerView extends FrameLayout {

    private ImageView trailerImageView;
    private ImageView playerImageView;
    private TextView titleTextView;
    private TextView qualityTextView;

    public TrailerView(Context context) {
        super(context);

        setForeground(Theme.selectableItemBackgroundDrawable());
        setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));

        FrameLayout imageLayout = new FrameLayout(context);
        imageLayout.setLayoutParams(LayoutHelper.makeFrame(180, 100, Gravity.TOP, 8, 8, 8, 0));
        addView(imageLayout);

        trailerImageView = new ImageView(context);
        trailerImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        trailerImageView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        imageLayout.addView(trailerImageView);

        ImageView playImageView = new ImageView(context);
        playImageView.setImageResource(R.drawable.ic_button_play);
        playImageView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));
        imageLayout.addView(playImageView);

        qualityTextView = new TextView(context);
        qualityTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8);
        qualityTextView.setTextColor(ContextCompat.getColor(context, Theme.primaryColor()));
        qualityTextView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        qualityTextView.setBackground(ContextCompat.getDrawable(context, R.drawable.rect_quality_mini));
        qualityTextView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.END | Gravity.BOTTOM, 0, 0, 5, 5));
        imageLayout.addView(qualityTextView);

        LinearLayout titleLayout = new LinearLayout(context);
        titleLayout.setOrientation(LinearLayout.HORIZONTAL);
        titleLayout.setLayoutParams(LayoutHelper.makeFrame(180, LayoutHelper.WRAP_CONTENT, Gravity.TOP, 8, 112, 8, 0));
        addView(titleLayout);

        playerImageView = new ImageView(context);
        playerImageView.setImageDrawable(Theme.getIcon(R.drawable.ic_youtube, 0xFFF44336));
        playerImageView.setLayoutParams(LayoutHelper.makeLinear(24, 24, Gravity.START | Gravity.TOP, 0, 2, 0, 0));
        titleLayout.addView(playerImageView);

        titleTextView = new TextView(context);
        titleTextView.setLines(2);
        titleTextView.setMaxLines(2);
        titleTextView.setEllipsize(TextUtils.TruncateAt.END);
        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        titleTextView.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        titleTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.TOP, 5, 5, 0, 0));
        titleLayout.addView(titleTextView);
    }

    public TrailerView setTrailerImage(@NonNull String trailerKey) {
        Picasso.with(getContext())
               .load("http://img.youtube.com/vi/" + trailerKey + "/0.jpg")
               .into(trailerImageView);
        return this;
    }

    public TrailerView setTitle(@NonNull String title) {
        titleTextView.setText(title);
        return this;
    }

    public TrailerView setQuality(@NonNull String size) {
        qualityTextView.setText(getContext().getString(R.string.VideoSize, size));
        return this;
    }

    public TrailerView setSite(@NonNull String site) {
        if (site.equals("YouTube")) {
            playerImageView.setVisibility(VISIBLE);
        } else {
            playerImageView.setVisibility(INVISIBLE);
        }

        return this;
    }

    public void changeLayoutParams(boolean gravity) {
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );

        if (gravity) {
            params.leftMargin = ScreenUtils.dp(3F);
        } else {
            params.rightMargin = ScreenUtils.dp(3F);
        }

        setLayoutParams(params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        setMeasuredDimension(width, height);
    }
}