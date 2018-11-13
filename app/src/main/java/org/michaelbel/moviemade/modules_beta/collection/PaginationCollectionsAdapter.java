package org.michaelbel.moviemade.modules_beta.collection;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;

import org.michaelbel.material.widget.Holder;
import org.michaelbel.tmdb.TmdbObject;
import org.michaelbel.moviemade.rest.model.v3.Collection;
import org.michaelbel.moviemade.ui.base.PaginationAdapter;
import org.michaelbel.moviemade.modules_beta.view.CollectionView;

import java.util.List;

public class PaginationCollectionsAdapter extends PaginationAdapter {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == ITEM_BACKDROP) {
            viewHolder = new Holder(new CollectionView(parent.getContext()));
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Collection collection = (Collection) objectList.get(position);

        if (getItemViewType(position) == ITEM_BACKDROP) {
            CollectionView view = (CollectionView) ((Holder) holder).itemView;
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            view.addImage(collection.backdropPath);
            view.addName(collection.name);
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