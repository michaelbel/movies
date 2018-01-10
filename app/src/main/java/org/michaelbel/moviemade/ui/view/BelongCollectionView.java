package org.michaelbel.moviemade.ui.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.LayoutHelper;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.util.AndroidUtils;
import org.michaelbel.moviemade.util.ScreenUtils;

import java.util.Locale;

public class BelongCollectionView extends FrameLayout {

    private ImageView imageView;
    private TextView nameText;

    public BelongCollectionView(@NonNull Context context) {
        super(context);

        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, 180));
        addView(imageView);

        FrameLayout imageLayout = new FrameLayout(context);
        imageLayout.setBackgroundColor(0x99000000);
        imageLayout.setPadding(ScreenUtils.dp(8), ScreenUtils.dp(8), ScreenUtils.dp(8), ScreenUtils.dp(8));
        imageLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.TOP, 16, 16, 16, 16));
        addView(imageLayout);

        nameText = new TextView(context);
        nameText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18.5F);
        nameText.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        nameText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        nameText.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT));
        imageLayout.addView(nameText);
    }

    public void addImage(String imagePath) {
        Picasso.with(getContext())
               .load(String.format(Locale.US, Url.TMDB_IMAGE, AndroidUtils.backdropSize(), imagePath))
               .placeholder(R.drawable.movie_placeholder_old)
               .into(imageView);
    }

    public void addName(String name) {
        nameText.setText("Part of the " + name);
    }
}