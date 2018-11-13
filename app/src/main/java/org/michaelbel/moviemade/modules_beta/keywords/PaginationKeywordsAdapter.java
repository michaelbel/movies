package org.michaelbel.moviemade.modules_beta.keywords;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;

import org.michaelbel.material.widget.Holder;
import org.michaelbel.moviemade.data.TmdbObject;
import org.michaelbel.moviemade.rest.model.v3.Keyword;
import org.michaelbel.moviemade.ui.base.PaginationAdapter;
import org.michaelbel.moviemade.modules_beta.view.cell.TextCell;

import java.util.List;

public class PaginationKeywordsAdapter extends PaginationAdapter {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == ITEM_BACKDROP) {
            viewHolder = new Holder(new TextCell(parent.getContext()));
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        /*Keyword keyword = (Keyword) objectList.get(position);

        if (getItemViewType(position) == ITEM_BACKDROP) {
            TextCell cell = (TextCell) ((Holder) holder).itemView;
            cell.setText(keyword.name);
            cell.setDivider(true);
        }*/
    }

    public void addAll(List<TmdbObject> keywords) {
        /*for (TmdbObject keyword : keywords) {
            add(keyword);
        }*/
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        //add(new Keyword());
    }
}