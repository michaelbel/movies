package org.michaelbel.moviemade.modules_beta.collection;

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
import org.michaelbel.moviemade.data.TmdbObject;
import org.michaelbel.moviemade.data.dao.Movie;
import org.michaelbel.moviemade.modules_beta.view.movie.MovieViewListBig;
import org.michaelbel.moviemade.utils.AndroidUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;

public class MoviesAdapter extends RecyclerView.Adapter {

    private List<Movie> movies;

    public MoviesAdapter() {
        movies = new ArrayList<>();
    }

    public void addMovies(List<Movie> results) {
        movies.addAll(results);
        notifyItemRangeInserted(movies.size() + 1, results.size());
    }

    public List<Movie> getMovies() {
        return movies;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int type) {
        View view = null;

        if (type == 0) {
            view = new MovieViewListBig(parent.getContext());
        } else if (type == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_poster, parent, false);
            view.getLayoutParams().height = (int) (parent.getWidth() / 2 * 1.5f);
        }

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Movie movie = (Movie) movies.get(position);

        if (getItemViewType(position) == 0) {
            MovieViewListBig view = (MovieViewListBig) holder.itemView;
            movie.getReleaseDate();
            view.setPoster(movie.getPosterPath())
                .setTitle(movie.getTitle())
                .setRating(String.valueOf(movie.getVoteAverage()))
                .setVoteCount(String.valueOf(movie.getVoteCount()))
                .setReleaseDate(AndroidExtensions.formatReleaseDate(movie.getReleaseDate()))
                .setOverview(movie.getOverview())
                .setDivider(position != movies.size() - 1);
        } else if (getItemViewType(position) == 1) {
            View view = holder.itemView;

            CardView cardView = view.findViewById(R.id.cardview);
            ImageView posterImage = view.findViewById(R.id.poster_image);

            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .priority(Priority.HIGH);

            Glide.with(view.getContext())
                    .asBitmap()
                    .load(String.format(Locale.US, ConstantsKt.TMDB_IMAGE, "w342", movie.getPosterPath()))
                    .apply(options)
                    .into(new BitmapImageViewTarget(posterImage) {
                        @Override
                        public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                            super.onResourceReady(bitmap, transition);
                            Palette.from(bitmap).generate(palette -> cardView.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.accent)));
                            //Palette.from(bitmap).generate(palette -> setBackgroundColor(palette, holder));
                        }
                    });
        }
    }

    @Override
    public int getItemCount() {
        return movies != null ? movies.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return AndroidUtils.viewType();
    }
}