package org.michaelbel.moviemade.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.michaelbel.moviemade.ConstantsKt;
import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.data.dao.Movie;
import org.michaelbel.moviemade.ui.base.PaginationAdapter;
import org.michaelbel.moviemade.ui.widgets.RecyclerListView;
import org.michaelbel.moviemade.utils.AdultUtil;
import org.michaelbel.moviemade.utils.DeviceUtil;

import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

@SuppressWarnings("ConstantConditions")
public class PaginationMoviesAdapter extends PaginationAdapter {

    private ImageView posterImage;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_poster, parent, false);
        posterImage = view.findViewById(R.id.poster_image);
        if (DeviceUtil.INSTANCE.isLandscape(parent.getContext()) || DeviceUtil.INSTANCE.isTablet(parent.getContext())) {
            view.getLayoutParams().height = (int) (parent.getWidth() / 2.5F);
        } else {
            view.getLayoutParams().height = (int) (parent.getWidth() / 2 * 1.5F);
        }
        return new RecyclerListView.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        Glide.with(holder.itemView.getContext()).load(String.format(Locale.US, ConstantsKt.TMDB_IMAGE, "w342", movie.getPosterPath())).thumbnail(0.1F).into(posterImage);
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