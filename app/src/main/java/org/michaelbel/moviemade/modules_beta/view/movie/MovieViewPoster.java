package org.michaelbel.moviemade.modules_beta.view.movie;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.Url;
import org.michaelbel.moviemade.LayoutHelper;
import org.michaelbel.moviemade.utils.AndroidUtils;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;

public class MovieViewPoster extends FrameLayout {

    private CardView cardView;
    private ImageView posterImage;

    private Rect rect = new Rect();

    public MovieViewPoster(Context context) {
        super(context);

        cardView = new CardView(context);
        //cardView.setUseCompatPadding(false);
        //cardView.setPreventCornerOverlap(false);
        //cardView.setRadius(ScreenUtils.dp(2));
        //cardView.setCardElevation(ScreenUtils.dp(0));
        //cardView.setForeground(Extensions.selectableItemBackgroundBorderlessDrawable(context));
        //cardView.setCardBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));
        //cardView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        addView(cardView);

        posterImage = new ImageView(context);
        posterImage.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        posterImage.setScaleType(ImageView.ScaleType.FIT_XY);
        cardView.addView(posterImage);
    }

    public MovieViewPoster setPoster(@NonNull String posterPath) {
        RequestOptions options = new RequestOptions()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .priority(Priority.HIGH);

        Glide.with(getContext())
                .asBitmap()
                .load(String.format(Locale.US, Url.TMDB_IMAGE, AndroidUtils.posterSize(), posterPath))
                .apply(options)
                .into(new BitmapImageViewTarget(posterImage) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                        super.onResourceReady(bitmap, transition);
                        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(@NonNull Palette palette) {
                                cardView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.accent));
                            }
                        });

                        //Palette.from(bitmap).generate(palette -> setBackgroundColor(palette, holder));
                    }
                });

        /*Picasso.with(getContext())
               .load(String.format(Locale.US, Url.TMDB_IMAGE, AndroidUtils.posterSize(), posterPath))
               .placeholder(R.drawable.movie_placeholder_old)
               .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
               .into(posterImage);*/

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