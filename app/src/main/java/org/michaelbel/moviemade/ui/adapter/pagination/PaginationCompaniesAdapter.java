package org.michaelbel.moviemade.ui.adapter.pagination;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.model.v3.Company;
import org.michaelbel.moviemade.ui.adapter.recycler.Holder;
import org.michaelbel.moviemade.ui.adapter.recycler.LoadingHolder;
import org.michaelbel.moviemade.ui.view.LoadingView;
import org.michaelbel.moviemade.ui.view.cell.TextCell;

import java.util.ArrayList;
import java.util.List;

public class PaginationCompaniesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int ITEM = 0;
    private final int LOADING = 1;

    private List<TmdbObject> companies;
    private boolean isLoadingAdded = false;

    public PaginationCompaniesAdapter() {
        companies = new ArrayList<>();
    }

    public List<TmdbObject> getCompanies() {
        return companies;
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
        Company company = (Company) companies.get(position);

        if (getItemViewType(position) == ITEM) {
            TextCell cell = (TextCell) ((Holder) holder).itemView;
            cell.setText(company.name);
            cell.setDivider(true);
        } else if (getItemViewType(position) == LOADING) {
            LoadingView view = (LoadingView) ((LoadingHolder) holder).itemView;
            view.setMode(LoadingView.MODE_DEFAULT);
        }
    }

    @Override
    public int getItemCount() {
        return companies != null ? companies.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == companies.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

//--------------------------------------------------------------------------------------------------

    public void add(TmdbObject company) {
        companies.add(company);
        notifyItemInserted(companies.size() - 1);
    }

    public void addAll(List<TmdbObject> companies) {
        for (TmdbObject company : companies) {
            add(company);
        }
    }

    public void remove(TmdbObject company) {
        int position = companies.indexOf(company);

        if (position > -1) {
            companies.remove(position);
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
        add(new Company());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = companies.size() - 1;
        TmdbObject result = getItem(position);

        if (result != null) {
            companies.remove(position);
            notifyItemRemoved(position);
        }
    }

    public TmdbObject getItem(int position) {
        return companies.get(position);
    }
}