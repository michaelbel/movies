package org.michaelbel.application.ui.view.section;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.michaelbel.application.R;
import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.moviemade.Url;

@SuppressWarnings("all")
public class ImagesSectionView extends FrameLayout {

    private ImageView posterImageView;
    private ImageView backdropImageView;
    private TextView postersCountTextView;
    private TextView backdropsCountTextView;

    public ImagesSectionView(Context context) {
        super(context);

        setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));

        TextView textView = new TextView(context);
        textView.setLines(1);
        textView.setMaxLines(1);
        textView.setSingleLine();
        textView.setText(R.string.Images);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        textView.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        textView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        textView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, 48,Gravity.START | Gravity.TOP, 16, 0, 16, 0));
        addView(textView);

        LinearLayout imagesLayout = new LinearLayout(context);
        imagesLayout.setOrientation(LinearLayout.HORIZONTAL);
        imagesLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, 150, Gravity.TOP, 12, 48, 12, 12));
        addView(imagesLayout);

        FrameLayout postersLayout = new FrameLayout(context);
        postersLayout.setLayoutParams(LayoutHelper.makeLinear(0, LayoutHelper.MATCH_PARENT, Gravity.CENTER, 1F));
        imagesLayout.addView(postersLayout);

        posterImageView = new ImageView(context);
        posterImageView.setScaleType(ImageView.ScaleType.CENTER);
        posterImageView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        postersLayout.addView(posterImageView);

        FrameLayout postersTitleLayout = new FrameLayout(context);
        postersTitleLayout.setBackgroundColor(0x99000000);
        postersTitleLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.BOTTOM));
        postersLayout.addView(postersTitleLayout);

        postersCountTextView = new TextView(context);
        postersCountTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        postersCountTextView.setTextColor(ContextCompat.getColor(context, Theme.foregroundColor()));
        postersCountTextView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 8, 6, 8, 6));
        postersTitleLayout.addView(postersCountTextView);

//--------------------------------------------------------------------------------------------------

        FrameLayout backdropsLayout = new FrameLayout(context);
        backdropsLayout.setLayoutParams(LayoutHelper.makeLinear(0, LayoutHelper.MATCH_PARENT, Gravity.CENTER,2F, 12, 0, 0, 0));
        imagesLayout.addView(backdropsLayout);

        backdropImageView = new ImageView(context);
        backdropImageView.setScaleType(ImageView.ScaleType.CENTER);
        backdropImageView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        backdropsLayout.addView(backdropImageView);

        FrameLayout backdropsTitleLayout = new FrameLayout(context);
        backdropsTitleLayout.setBackgroundColor(0x99000000);
        backdropsTitleLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.BOTTOM));
        backdropsLayout.addView(backdropsTitleLayout);

        backdropsCountTextView = new TextView(context);
        backdropsCountTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        backdropsCountTextView.setTextColor(ContextCompat.getColor(context, Theme.foregroundColor()));
        backdropsCountTextView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 8, 6, 8, 6));
        backdropsTitleLayout.addView(backdropsCountTextView);
    }

    public ImagesSectionView setPoster(@NonNull String posterPath) {
        SharedPreferences prefs = getContext().getSharedPreferences("main_config", Context.MODE_PRIVATE);
        String size = prefs.getString("image_quality_poster", "w342");

        Picasso.with(getContext())
               .load(Url.getImage(posterPath, size))
               .placeholder(R.drawable.movie_placeholder)
               .into(posterImageView);
        return this;
    }

    public ImagesSectionView setBackdrop(@NonNull String backdropPath) {
        SharedPreferences prefs = getContext().getSharedPreferences("mainconfig", Context.MODE_PRIVATE);
        String size = prefs.getString("image_quality_backdrop", "w780");

        Picasso.with(getContext())
               .load(Url.getImage(backdropPath, size))
               .placeholder(R.drawable.movie_placeholder)
               .into(backdropImageView);
        return this;
    }

    public ImagesSectionView setPostersCount(int count) {
        postersCountTextView.setText(getContext().getString(R.string.Posters, count));
        return this;
    }

    public ImagesSectionView setBackdropsCount(int count) {
        backdropsCountTextView.setText(getContext().getString(R.string.Backdrops, count));
        return this;
    }
}