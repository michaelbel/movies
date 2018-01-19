package org.michaelbel.moviemade.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.model.v3.Collection;
import org.michaelbel.moviemade.ui.view.CollectionView;

import java.util.List;

public class SearchCollectionsAdapter extends RecyclerView.Adapter {

    private List<TmdbObject> searches;

    public SearchCollectionsAdapter(List<TmdbObject> searches) {
        this.searches = searches;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        return new Holder(new CollectionView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Collection collection = (Collection) searches.get(position);

        CollectionView view = (CollectionView) holder.itemView;
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.addImage(collection.backdropPath);
        view.addName(collection.name);
    }

    @Override
    public int getItemCount() {
        return searches != null ? searches.size() : 0;
    }

    public List<TmdbObject> getCollections() {
        return searches;
    }
}