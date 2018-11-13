package org.michaelbel.moviemade.ui.base;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;

import org.michaelbel.moviemade.data.TmdbObject;
import org.michaelbel.moviemade.data.dao.Movie;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ConstantConditions")
public class PaginationAdapter extends RecyclerView.Adapter {

    protected final int ITEM_BACKDROP = 0;

    protected List<Movie> objectList;
    protected boolean isLoadingAdded = false;

    public PaginationAdapter() {
        objectList = new ArrayList<>();
    }

    public List<Movie> getList() {
        return objectList;
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
        return objectList != null ? objectList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM_BACKDROP;
    }

    public void add(Movie object) {
        objectList.add(object);
        notifyItemInserted(objectList.size() - 1);
    }

    private void remove(Movie collection) {
        int position = objectList.indexOf(collection);

        if (position > -1) {
            objectList.remove(position);
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
        return objectList.get(position);
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = objectList.size() - 1;
        Movie result = getItem(position);

        if (result != null) {
            objectList.remove(position);
            notifyItemRemoved(position);
        }
    }
}