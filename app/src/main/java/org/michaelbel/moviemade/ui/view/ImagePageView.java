package org.michaelbel.moviemade.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.LayoutHelper;
import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.util.AndroidUtils;

import java.util.Locale;

public class ImagePageView extends FrameLayout {

    private ImageView posterImage;
    //private ProgressBar progressBar;

    public ImagePageView(@NonNull Context context) {
        super(context);

        //progressBar = new ProgressBar(context);
        //progressBar.setVisibility(VISIBLE);
        //progressBar.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));
        //addView(progressBar);

        posterImage = new ImageView(context);
        posterImage.setVisibility(INVISIBLE);
        posterImage.setScaleType(ImageView.ScaleType.CENTER);
        posterImage.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        addView(posterImage);
    }

    public ImagePageView setImage(String imagePath) {
        Picasso.with(getContext())
               .load(String.format(Locale.US, Url.TMDB_IMAGE, AndroidUtils.posterSize(), imagePath))
               .placeholder(R.drawable.movie_placeholder_old)
               .into(posterImage);

        posterImage.setVisibility(VISIBLE);
        //progressBar.setVisibility(INVISIBLE);
        return this;
    }

    public ImageView getPosterImage() {
        return posterImage;
    }
}