package org.michaelbel.moviemade.ui.adapter.pagination;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.model.v3.Collection;
import org.michaelbel.moviemade.ui.adapter.pagination.base.PaginationAdapter;
import org.michaelbel.moviemade.ui.adapter.recycler.Holder;
import org.michaelbel.moviemade.ui.adapter.recycler.LoadingHolder;
import org.michaelbel.moviemade.ui.view.CollectionView;
import org.michaelbel.moviemade.ui.view.LoadingView;

import java.util.List;

public class PaginationCollectionsAdapter extends PaginationAdapter {

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
        Collection collection = (Collection) objectList.get(position);

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

    public void addAll(List<TmdbObject> objects) {
        for (TmdbObject object : objects) {
            add(object);
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Collection());
    }
}