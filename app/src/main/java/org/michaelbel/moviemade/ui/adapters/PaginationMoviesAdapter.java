package org.michaelbel.moviemade.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.michaelbel.material.widget.Holder;
import org.michaelbel.moviemade.ConstantsKt;
import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.extensions.AdultUtil;
import org.michaelbel.moviemade.extensions.AndroidExtensions;
import org.michaelbel.moviemade.extensions.DeviceUtil;
import org.michaelbel.moviemade.ui.widgets.RecyclerListView;
import org.michaelbel.moviemade.ui.base.PaginationAdapter;
import org.michaelbel.moviemade.modules_beta.view.movie.MovieViewListBig;
import org.michaelbel.moviemade.data.dao.Movie;

import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

@SuppressWarnings("ConstantConditions")
public class PaginationMoviesAdapter extends PaginationAdapter {

    private final int ITEM_POSTER = 2;

    private ImageView posterImage;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_BACKDROP) {
            return new RecyclerListView.ViewHolder(new MovieViewListBig(parent.getContext()));
        } else if (viewType == ITEM_POSTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_poster, parent, false);
            posterImage = view.findViewById(R.id.poster_image);
            if (DeviceUtil.INSTANCE.isLandscape(parent.getContext()) || DeviceUtil.INSTANCE.isTablet(parent.getContext())) {
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
            Glide.with(holder.itemView.getContext()).load(String.format(Locale.US, ConstantsKt.TMDB_IMAGE, "w342", movie.getPosterPath())).thumbnail(0.1F).into(posterImage);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM_POSTER;
    }

    public void addAll(List<Movie> movies) {
        for (Movie movie : movies) {
            if (AdultUtil.INSTANCE.includeAdult(Moviemade.AppContext)) {
                add(movie);
            } else {
                if (!movie.getAdult()) {
                    add(movie);
                }
            }
        }
    }
}