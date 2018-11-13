package org.michaelbel.moviemade.ui.adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;

import org.michaelbel.material.widget.Holder;
import org.michaelbel.moviemade.ConstantsKt;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.extensions.AndroidExtensions;
import org.michaelbel.moviemade.extensions.DeviceUtil;
import org.michaelbel.moviemade.ui.widgets.RecyclerListView;
import org.michaelbel.moviemade.ui.base.PaginationAdapter;
import org.michaelbel.moviemade.modules_beta.view.movie.MovieViewListBig;
import org.michaelbel.moviemade.utils.AndroidUtils;
import org.michaelbel.moviemade.data.dao.Movie;

import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;

@SuppressWarnings("ConstantConditions")
public class PaginationMoviesAdapter extends PaginationAdapter {

    private final int ITEM_POSTER = 2;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_BACKDROP) {
            return new RecyclerListView.ViewHolder(new MovieViewListBig(parent.getContext()));
        } else if (viewType == ITEM_POSTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_poster, parent, false);
            if (DeviceUtil.isLandscape(parent.getContext()) || DeviceUtil.isTablet(parent.getContext())) {
                view.getLayoutParams().height = (int) (parent.getWidth() / 2.5F);
            } else {
                view.getLayoutParams().height = (int) (parent.getWidth() / 2 * 1.5F);
            }
            return new RecyclerListView.ViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Movie movie = movies.get(position);

        if (getItemViewType(position) == ITEM_BACKDROP) {
            MovieViewListBig view = (MovieViewListBig) ((Holder) holder).itemView;
            view.setPoster(movie.getPosterPath())
                .setTitle(movie.getTitle())
                .setRating(String.valueOf(movie.getVoteAverage()))
                .setVoteCount(String.valueOf(movie.getVoteCount()))
                .setReleaseDate(movie.getReleaseDate() != null ? AndroidExtensions.formatReleaseDate(movie.getReleaseDate()) : "")
                .setOverview(movie.getOverview())
                .setDivider(true);
        } else if (getItemViewType(position) == ITEM_POSTER) {
            View view = holder.itemView;

            CardView cardView = view.findViewById(R.id.cardview);
            ImageView posterImage = view.findViewById(R.id.poster_image);

            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .priority(Priority.HIGH);

            Glide.with(view.getContext())
                 .asBitmap()
                 .load(String.format(Locale.US, ConstantsKt.TMDB_IMAGE, AndroidUtils.posterSize(), movie.getPosterPath()))
                 .apply(options)
                 .into(new BitmapImageViewTarget(posterImage) {
                     @Override
                     public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                         super.onResourceReady(bitmap, transition);
                         Palette.from(bitmap).generate(palette -> cardView.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.primary)));
                     }
                 });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM_POSTER;
    }

    public void addAll(List<Movie> movies) {
        for (Movie movie : movies) {
            if (AndroidUtils.includeAdult()) {
                add(movie);
            } else {
                if (!movie.getAdult()) {
                    add(movie);
                }
            }
        }
    }
}