package org.michaelbel.moviemade.presentation.features.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.core.entity.Movie;
import org.michaelbel.moviemade.core.utils.DeviceUtil;
import org.michaelbel.moviemade.core.utils.TmdbConfigKt;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.internal.DebouncingOnClickListener;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    public interface Listener {
        void onMovieClick(Movie movie, View view);
    }

    private Listener movieClickListener;
    private List<Movie> movies = new ArrayList<>();

    public MoviesAdapter(Listener listener) {
        movieClickListener = listener;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void addMovies(List<Movie> movies) {
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_poster, parent, false);
        MoviesViewHolder holder = new MoviesViewHolder(view);
        view.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    movieClickListener.onMovieClick(movies.get(position), v);
                }
            }
        });

        if (DeviceUtil.INSTANCE.isLandscape(parent.getContext()) || DeviceUtil.INSTANCE.isTablet(parent.getContext())) {
            view.getLayoutParams().height = (int) (parent.getWidth() / 2.5F);
        } else {
            view.getLayoutParams().height = (int) (parent.getWidth() / 2 * 1.5F);
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        Movie movie = movies.get(position);
        Glide.with(holder.itemView.getContext()).load(String.format(Locale.US, TmdbConfigKt.TMDB_IMAGE, "w342", movie.getPosterPath())).thumbnail(0.1f).into(holder.posterImage);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView posterImage;

        private MoviesViewHolder(View view) {
            super(view);
            posterImage = view.findViewById(R.id.poster);
        }
    }
}