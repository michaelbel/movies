package org.michaelbel.moviemade.modules_beta.view;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.LayoutHelper;

import androidx.core.content.ContextCompat;

public class ImagePageView extends FrameLayout {

    private ImageView posterImage;
    private ProgressBar progressBar;

    public ImagePageView(Context context) {
        super(context);

        progressBar = new ProgressBar(context);
        progressBar.setVisibility(VISIBLE);
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context, R.color.md_white), PorterDuff.Mode.MULTIPLY);
        progressBar.setLayoutParams(LayoutHelper.makeFrame(20, 20, Gravity.CENTER));
        addView(progressBar);

        posterImage = new ImageView(context);
        posterImage.setScaleType(ImageView.ScaleType.CENTER);
        posterImage.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        addView(posterImage);
    }

    public ImagePageView setImage(String imagePath) {
        /*Picasso.get()
               .load(String.format(Locale.US, Url.TMDB_IMAGE, AndroidUtils.posterSize(), imagePath))
               .placeholder(R.drawable.movie_placeholder_old)
               .into(posterImage, new Callback() {
                   @Override
                   public void onSuccess() {
                       progressBar.setVisibility(GONE);
                   }

                   @Override
                   public void onError(Exception e) {
                       progressBar.setVisibility(GONE);
                   }
               });*/

        return this;
    }

    public ImageView getPosterImage() {
        return posterImage;
    }
}