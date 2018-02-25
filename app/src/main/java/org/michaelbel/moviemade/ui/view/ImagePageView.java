package org.michaelbel.moviemade.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.michaelbel.moviemade.R;
import org.michaelbel.core.widget.LayoutHelper;
import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.utils.AndroidUtils;

import java.util.Locale;

public class ImagePageView extends FrameLayout {

    private ImageView posterImage;
    private ProgressBar progressBar;

    public ImagePageView(@NonNull Context context) {
        super(context);

        progressBar = new ProgressBar(new ContextThemeWrapper(context, R.style.WhiteProgressBar));
        progressBar.setVisibility(VISIBLE);
        progressBar.setLayoutParams(LayoutHelper.makeFrame(20, 20, Gravity.CENTER));
        addView(progressBar);

        posterImage = new ImageView(context);
        //posterImage.setVisibility(INVISIBLE);
        posterImage.setScaleType(ImageView.ScaleType.CENTER);
        posterImage.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        addView(posterImage);
    }

    public ImagePageView setImage(String imagePath) {
        Picasso.with(getContext())
               .load(String.format(Locale.US, Url.TMDB_IMAGE, AndroidUtils.posterSize(), imagePath))
               .placeholder(R.drawable.movie_placeholder_old)
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

    public ImageView getPosterImage() {
        return posterImage;
    }
}