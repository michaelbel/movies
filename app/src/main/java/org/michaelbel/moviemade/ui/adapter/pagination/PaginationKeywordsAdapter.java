package org.michaelbel.moviemade.ui.adapter.pagination;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.model.v3.Keyword;
import org.michaelbel.moviemade.ui.adapter.recycler.Holder;
import org.michaelbel.moviemade.ui.adapter.recycler.LoadingHolder;
import org.michaelbel.moviemade.ui.view.LoadingView;
import org.michaelbel.moviemade.ui.view.cell.TextCell;

import java.util.ArrayList;
import java.util.List;

public class PaginationKeywordsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int ITEM = 0;
    private final int LOADING = 1;

    private List<TmdbObject> keywords;
    private boolean isLoadingAdded = false;

    public PaginationKeywordsAdapter() {
        keywords = new ArrayList<>();
    }

    public List<TmdbObject> getKeywords() {
        return keywords;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == ITEM) {
            viewHolder = new Holder(new TextCell(parent.getContext()));
        } else if (viewType == LOADING) {
            viewHolder = new LoadingHolder(new LoadingView(parent.getContext()));
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Keyword keyword = (Keyword) keywords.get(position);

        if (getItemViewType(position) == ITEM) {
            TextCell cell = (TextCell) ((Holder) holder).itemView;
            cell.setText(keyword.name);
            cell.setDivider(true);
        }
    }

    @Override
    public int getItemCount() {
        return keywords != null ? keywords.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == keywords.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

//--------------------------------------------------------------------------------------------------

    public void add(TmdbObject keyword) {
        keywords.add(keyword);
        notifyItemInserted(keywords.size() - 1);
    }

    public void addAll(List<TmdbObject> keywords) {
        for (TmdbObject keyword : keywords) {
            add(keyword);
        }
    }

    public void remove(TmdbObject keyword) {
        int position = keywords.indexOf(keyword);

        if (position > -1) {
            keywords.remove(position);
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
        add(new Keyword());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = keywords.size() - 1;
        TmdbObject result = getItem(position);

        if (result != null) {
            keywords.remove(position);
            notifyItemRemoved(position);
        }
    }

    public TmdbObject getItem(int position) {
        return keywords.get(position);
    }
}