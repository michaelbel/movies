package org.michaelbel.moviemade.ui.base;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;

import org.michaelbel.moviemade.data.dao.Movie;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ConstantConditions")
public class PaginationAdapter extends RecyclerView.Adapter {

    protected final int ITEM_BACKDROP = 0;

    protected List<Movie> movies;
    protected boolean isLoadingAdded = false;

    public PaginationAdapter() {
        movies = new ArrayList<>();
    }

    public List<Movie> getList() {
        return movies;
    }

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

    @Override
    public int getItemViewType(int position) {
        return ITEM_BACKDROP;
    }

    public void add(Movie object) {
        movies.add(object);
        notifyItemInserted(movies.size() - 1);
    }

    private void remove(Movie collection) {
        int position = movies.indexOf(collection);

        if (position > -1) {
            movies.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;

        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    private Movie getItem(int position) {
        return movies.get(position);
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = movies.size() - 1;
        Movie result = getItem(position);

        if (result != null) {
            movies.remove(position);
            notifyItemRemoved(position);
        }
    }
}