package org.michaelbel.moviemade.ui.view.movie;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.michaelbel.core.extensions.Extensions;
import org.michaelbel.core.widget.LayoutHelper;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.utils.AndroidUtils;
import org.michaelbel.moviemade.utils.ScreenUtils;

import java.util.Locale;

public class MovieViewCard extends FrameLayout {

    private CardView cardView;
    private ImageView posterImage;
    private TextView titleText;
    private TextView yearText;

    private Rect rect = new Rect();

    public MovieViewCard(Context context) {
        super(context);

        cardView = new CardView(context);
        cardView.setUseCompatPadding(true);
        cardView.setPreventCornerOverlap(false);
        cardView.setRadius(ScreenUtils.dp(2));
        cardView.setCardElevation(ScreenUtils.dp(1.0F));
        cardView.setForeground(Extensions.selectableItemBackgroundBorderlessDrawable());
        cardView.setCardBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));
        cardView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        addView(cardView);

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        cardView.addView(linearLayout);

        posterImage = new ImageView(context);
        posterImage.setScaleType(ImageView.ScaleType.FIT_XY);
        posterImage.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        linearLayout.addView(posterImage);

        titleText = new TextView(context);
        titleText.setLines(1);
        titleText.setMaxLines(2);
        titleText.setEllipsize(TextUtils.TruncateAt.END);
        titleText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        titleText.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        titleText.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        titleText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.START, 8, 6, 8, 6));
        linearLayout.addView(titleText);

        yearText = new TextView(context);
        yearText.setLines(1);
        yearText.setMaxLines(1);
        yearText.setSingleLine();
        yearText.setEllipsize(TextUtils.TruncateAt.END);
        yearText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        yearText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        yearText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.START, 8, 0, 8, 6));
        //linearLayout.addView(yearText);
    }

    public MovieViewCard setPoster(@NonNull String posterPath) {
        Picasso.with(getContext())
               .load(String.format(Locale.US, Url.TMDB_IMAGE, AndroidUtils.posterSize(), posterPath))
               .placeholder(R.drawable.movie_placeholder_old)
               .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
               .into(posterImage);

        return this;
    }

    public MovieViewCard setTitle(@NonNull String title) {
        titleText.setText(title.isEmpty() ? "" : title);
        return this;
    }

    /*public MovieViewCard setYear(@NonNull String releaseDate) {
        if (releaseDate.length() >= 4) {
            yearText.setText(releaseDate.substring(0, 4));
        }
        return this;
    }*/

    public MovieViewCard changeLayoutParams(boolean gravity) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (gravity) {
            params.leftMargin = ScreenUtils.dp(3F);
        } else {
            params.rightMargin = ScreenUtils.dp(3F);
        }

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
}