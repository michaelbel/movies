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

import org.michaelbel.core.widget.LayoutHelper;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.utils.AndroidUtils;
import org.michaelbel.moviemade.utils.ScreenUtils;

import java.util.Locale;

public class CollectionView extends FrameLayout {

    private TextView nameText;
    private ImageView imageView;

    public CollectionView(@NonNull Context context) {
        super(context);

        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, 200));
        addView(imageView);

        FrameLayout imageLayout = new FrameLayout(context);
        imageLayout.setBackgroundColor(0x99000000);
        imageLayout.setPadding(ScreenUtils.dp(8), ScreenUtils.dp(8), ScreenUtils.dp(8), ScreenUtils.dp(8));
        imageLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.TOP, 16, 16, 16, 16));
        addView(imageLayout);

        nameText = new TextView(context);
        nameText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18.5F);
        nameText.setTextColor(ContextCompat.getColor(context, R.color.md_white));
        nameText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        nameText.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT));
        imageLayout.addView(nameText);
    }

    public void addName(String name) {
        nameText.setText(name);
    }

    public void addImage(String imagePath) {
        if (imagePath == null || imagePath.isEmpty()) {
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setImageResource(R.drawable.movie_placeholder_old);
            return;
        }

        Picasso.with(getContext())
               .load(String.format(Locale.US, Url.TMDB_IMAGE, AndroidUtils.backdropSize(), imagePath))
               .into(imageView);

        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
    }
}