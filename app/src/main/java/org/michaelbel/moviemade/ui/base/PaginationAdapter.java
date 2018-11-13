package org.michaelbel.moviemade.ui.base;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;

import org.michaelbel.tmdb.TmdbObject;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ConstantConditions")
public class PaginationAdapter extends RecyclerView.Adapter {

    protected final int ITEM_BACKDROP = 0;

    protected List<TmdbObject> objectList;
    protected boolean isLoadingAdded = false;

    public PaginationAdapter() {
        objectList = new ArrayList<>();
    }

    public List<TmdbObject> getList() {
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

    public void add(TmdbObject object) {
        objectList.add(object);
        notifyItemInserted(objectList.size() - 1);
    }

    private void remove(TmdbObject collection) {
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

    private TmdbObject getItem(int position) {
        return objectList.get(position);
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = objectList.size() - 1;
        TmdbObject result = getItem(position);

        if (result != null) {
            objectList.remove(position);
            notifyItemRemoved(position);
        }
    }
}