package org.michaelbel.moviemade.ui.view.trailer;

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

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.michaelbel.core.extensions.Extensions;
import org.michaelbel.core.widget.LayoutHelper;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.utils.ScreenUtils;

import java.util.Locale;

public class TrailerView extends FrameLayout {

    private ImageView trailerImage;
    private ImageView playerImage;
    private TextView titleText;
    private TextView qualityText;

    public TrailerView(Context context) {
        super(context);

        setForeground(Extensions.selectableItemBackgroundDrawable());
        setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));

        FrameLayout imageLayout = new FrameLayout(context);
        imageLayout.setLayoutParams(LayoutHelper.makeFrame(180, 100, Gravity.TOP, 8, 8, 8, 0));
        addView(imageLayout);

        trailerImage = new ImageView(context);
        trailerImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        trailerImage.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        imageLayout.addView(trailerImage);

        ImageView playIcon = new ImageView(context);
        playIcon.setImageResource(R.drawable.ic_button_play);
        playIcon.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));
        imageLayout.addView(playIcon);

        qualityText = new TextView(context);
        qualityText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8);
        qualityText.setTextColor(ContextCompat.getColor(context, Theme.primaryColor()));
        qualityText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        qualityText.setBackground(ContextCompat.getDrawable(context, R.drawable.rect_quality_mini));
        qualityText.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.END | Gravity.BOTTOM, 0, 0, 5, 5));
        imageLayout.addView(qualityText);

        LinearLayout titleLayout = new LinearLayout(context);
        titleLayout.setOrientation(LinearLayout.HORIZONTAL);
        titleLayout.setLayoutParams(LayoutHelper.makeFrame(180, LayoutHelper.WRAP_CONTENT, Gravity.TOP, 8, 112, 8, 0));
        addView(titleLayout);

        playerImage = new ImageView(context);
        playerImage.setImageDrawable(Theme.getIcon(R.drawable.ic_youtube, 0xFFF44336));
        playerImage.setLayoutParams(LayoutHelper.makeLinear(24, 24, Gravity.START | Gravity.TOP, 0, 2, 0, 0));
        titleLayout.addView(playerImage);

        titleText = new TextView(context);
        titleText.setLines(2);
        titleText.setMaxLines(2);
        titleText.setEllipsize(TextUtils.TruncateAt.END);
        titleText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        titleText.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        titleText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.TOP, 5, 5, 0, 0));
        titleLayout.addView(titleText);
    }

    public TrailerView setTrailerImage(@NonNull String trailerKey) {
        Picasso.with(getContext())
               .load(String.format(Locale.US, Url.YOUTUBE_IMAGE, trailerKey))
               .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
               .into(trailerImage);
        return this;
    }

    public TrailerView setTitle(@NonNull String title) {
        titleText.setText(title);
        return this;
    }

    public TrailerView setQuality(@NonNull String size) {
        qualityText.setText(getContext().getString(R.string.VideoSize, size));
        return this;
    }

    public TrailerView setSite(@NonNull String site) {
        if (site.equals("YouTube")) {
            playerImage.setVisibility(VISIBLE);
        } else {
            playerImage.setVisibility(INVISIBLE);
        }

        return this;
    }

    public void changeLayoutParams(boolean gravity) {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

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