package org.michaelbel.moviemade.ui.modules.main.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.data.entity.Movie;
import org.michaelbel.moviemade.utils.DeviceUtil;
import org.michaelbel.moviemade.utils.TmdbConfigKt;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private OnMovieClickListener movieClickListener;
    private ArrayList<Movie> movies = new ArrayList<>();

    @Deprecated
    public MoviesAdapter() {}

    public MoviesAdapter(OnMovieClickListener listener) {
        movieClickListener = listener;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void addAll(List<Movie> movies) {
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_poster, parent, false);
        MoviesViewHolder viewHolder = new MoviesViewHolder(view);
        view.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                int position = viewHolder.getAdapterPosition();
                movieClickListener.onMovieClick(movies.get(position), v);
            }
        });

        if (DeviceUtil.INSTANCE.isLandscape(parent.getContext()) || DeviceUtil.INSTANCE.isTablet(parent.getContext())) {
            view.getLayoutParams().height = (int) (parent.getWidth() / 2.5F);
        } else {
            view.getLayoutParams().height = (int) (parent.getWidth() / 2 * 1.5F);
        }

        return viewHolder;
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

        @BindView(R.id.poster) AppCompatImageView posterImage;

        private MoviesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}