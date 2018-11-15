package org.michaelbel.moviemade.ui.base;

import android.view.ViewGroup;

import org.michaelbel.moviemade.data.dao.Movie;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

@SuppressWarnings("ConstantConditions")
public class PaginationAdapter extends RecyclerView.Adapter {

    public List<Movie> movies = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {}

    @Override
    public int getItemCount() {
        return movies != null ? movies.size() : 0;
    }

    public void add(Movie object) {
        movies.add(object);
        notifyItemInserted(movies.size() - 1);
    }
}