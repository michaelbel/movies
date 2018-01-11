package org.michaelbel.moviemade.ui.view.section;

import android.content.Context;
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

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.LayoutHelper;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.ui.interfaces.ImageSectionListener;
import org.michaelbel.moviemade.util.AndroidUtils;

import java.util.Locale;

public class ImagesSectionView extends FrameLayout {

    private ImageView posterImage;
    private ImageView backdropImage;
    private TextView postersCountText;
    private TextView backdropsCountText;

    private ImageSectionListener imageSectionListener;

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

//------POSTERS-------------------------------------------------------------------------------------

        FrameLayout postersLayout = new FrameLayout(context);
        postersLayout.setOnClickListener(v -> {
            if (imageSectionListener != null) {
                imageSectionListener.onPostersClick(v);
            }
        });
        postersLayout.setLayoutParams(LayoutHelper.makeLinear(0, LayoutHelper.MATCH_PARENT, Gravity.CENTER, 1F));
        imagesLayout.addView(postersLayout);

        posterImage = new ImageView(context);
        posterImage.setScaleType(ImageView.ScaleType.CENTER);
        posterImage.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        postersLayout.addView(posterImage);

        FrameLayout postersTitleLayout = new FrameLayout(context);
        postersTitleLayout.setBackgroundColor(0x99000000);
        postersTitleLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.BOTTOM));
        postersLayout.addView(postersTitleLayout);

        postersCountText = new TextView(context);
        postersCountText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        postersCountText.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        postersCountText.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 8, 6, 8, 6));
        postersTitleLayout.addView(postersCountText);

//------BACKDROPS-----------------------------------------------------------------------------------

        FrameLayout backdropsLayout = new FrameLayout(context);
        backdropsLayout.setOnClickListener(v -> {
            if (imageSectionListener != null) {
                imageSectionListener.onBackdropClick(v);
            }
        });
        backdropsLayout.setLayoutParams(LayoutHelper.makeLinear(0, LayoutHelper.MATCH_PARENT, Gravity.CENTER,2F, 12, 0, 0, 0));
        imagesLayout.addView(backdropsLayout);

        backdropImage = new ImageView(context);
        backdropImage.setScaleType(ImageView.ScaleType.CENTER);
        backdropImage.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        backdropsLayout.addView(backdropImage);

        FrameLayout backdropsTitleLayout = new FrameLayout(context);
        backdropsTitleLayout.setBackgroundColor(0x99000000);
        backdropsTitleLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.BOTTOM));
        backdropsLayout.addView(backdropsTitleLayout);

        backdropsCountText = new TextView(context);
        backdropsCountText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        backdropsCountText.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        backdropsCountText.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 8, 6, 8, 6));
        backdropsTitleLayout.addView(backdropsCountText);
    }

    public void addImageSectionListener(ImageSectionListener listener) {
        this.imageSectionListener = listener;
    }

    public ImagesSectionView setPoster(@NonNull String posterPath) {
        Picasso.with(getContext())
               .load(String.format(Locale.US, Url.TMDB_IMAGE, AndroidUtils.posterSize(), posterPath))
               .placeholder(R.drawable.movie_placeholder_old)
               .into(posterImage);

        return this;
    }

    public ImagesSectionView setBackdrop(@NonNull String backdropPath) {
        Picasso.with(getContext())
               .load(String.format(Locale.US, Url.TMDB_IMAGE, AndroidUtils.backdropSize(), backdropPath))
               .placeholder(R.drawable.movie_placeholder_old)
               .into(backdropImage);

        return this;
    }

    public ImagesSectionView setPostersCount(int count) {
        postersCountText.setText(getResources().getQuantityString(R.plurals.Posters, count, count));
        return this;
    }

    public ImagesSectionView setBackdropsCount(int count) {
        backdropsCountText.setText(getResources().getQuantityString(R.plurals.Backdrops, count, count));
        return this;
    }
}