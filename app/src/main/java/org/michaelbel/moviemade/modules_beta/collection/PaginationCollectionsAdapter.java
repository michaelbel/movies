package org.michaelbel.moviemade.modules_beta.collection;

import android.view.ViewGroup;

import org.michaelbel.material.widget.Holder;
import org.michaelbel.moviemade.data.dao.Collection;
import org.michaelbel.moviemade.modules_beta.view.CollectionView;
import org.michaelbel.moviemade.ui.base.PaginationAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        //Collection collection = (org.michaelbel.moviemade.data.dao.Movie) parts.get(position);

        if (getItemViewType(position) == ITEM_BACKDROP) {
            CollectionView view = (CollectionView) ((Holder) holder).itemView;
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            //view.addImage(collection.backdropPath);
            //view.addName(collection.name);
        }
    }

    public void addAll(List<Collection> objects) {
        for (Collection object : objects) {
            //add(object);
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        //add(new Collection());
    }
}