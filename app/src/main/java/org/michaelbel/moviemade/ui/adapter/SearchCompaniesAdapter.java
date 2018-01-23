package org.michaelbel.moviemade.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.model.v3.Company;
import org.michaelbel.moviemade.ui.adapter.recycler.Holder;
import org.michaelbel.moviemade.ui.view.cell.TextCell;

import java.util.ArrayList;
import java.util.List;

public class SearchCompaniesAdapter extends RecyclerView.Adapter {

    private List<TmdbObject> searches;

    public SearchCompaniesAdapter() {
        searches = new ArrayList<>();
    }

    public void addSearches(List<TmdbObject> results) {
        searches.addAll(results);
        notifyItemRangeInserted(searches.size() + 1, results.size());
    }

    public List<TmdbObject> getSearches() {
        return searches;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        return new Holder(new TextCell(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Company company = (Company) searches.get(position);

        TextCell cell = (TextCell) holder.itemView;
        cell.setText(company.name);
        cell.setDivider(true);
    }

    @Override
    public int getItemCount() {
        return searches != null ? searches.size() : 0;
    }
}