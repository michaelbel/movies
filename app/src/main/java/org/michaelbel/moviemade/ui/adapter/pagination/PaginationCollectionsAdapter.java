package org.michaelbel.moviemade.ui.adapter.pagination;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.model.v3.Collection;
import org.michaelbel.moviemade.ui.adapter.recycler.Holder;
import org.michaelbel.moviemade.ui.adapter.recycler.LoadingHolder;
import org.michaelbel.moviemade.ui.view.CollectionView;
import org.michaelbel.moviemade.ui.view.LoadingView;

import java.util.ArrayList;
import java.util.List;

public class PaginationCollectionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int ITEM = 0;
    private final int LOADING = 1;

    private List<TmdbObject> collections;
    private boolean isLoadingAdded = false;

    public PaginationCollectionsAdapter() {
        collections = new ArrayList<>();
    }

    public List<TmdbObject> getCollections() {
        return collections;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == ITEM) {
            viewHolder = new Holder(new CollectionView(parent.getContext()));
        } else if (viewType == LOADING) {
            viewHolder = new LoadingHolder(new LoadingView(parent.getContext()));
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Collection collection = (Collection) collections.get(position);

        if (getItemViewType(position) == ITEM) {
            CollectionView view = (CollectionView) ((Holder) holder).itemView;
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            view.addImage(collection.backdropPath);
            view.addName(collection.name);
        } else if (getItemViewType(position) == LOADING) {
            LoadingView view = (LoadingView) ((LoadingHolder) holder).itemView;
            view.setMode(LoadingView.MODE_DEFAULT);
        }
    }

    @Override
    public int getItemCount() {
        return collections != null ? collections.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == collections.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

//--------------------------------------------------------------------------------------------------

    public void add(TmdbObject collection) {
        collections.add(collection);
        notifyItemInserted(collections.size() - 1);
    }

    public void addAll(List<TmdbObject> collections) {
        for (TmdbObject collection : collections) {
            add(collection);
        }
    }

    public void remove(TmdbObject collection) {
        int position = collections.indexOf(collection);

        if (position > -1) {
            collections.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;

        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Collection());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = collections.size() - 1;
        TmdbObject result = getItem(position);

        if (result != null) {
            collections.remove(position);
            notifyItemRemoved(position);
        }
    }

    private TmdbObject getItem(int position) {
        return collections.get(position);
    }
}