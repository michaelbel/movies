package org.michaelbel.moviemade.modules_beta.company;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;

import org.michaelbel.material.widget.Holder;
import org.michaelbel.tmdb.TmdbObject;
import org.michaelbel.moviemade.rest.model.v3.Company;
import org.michaelbel.moviemade.ui.base.PaginationAdapter;
import org.michaelbel.moviemade.modules_beta.view.cell.TextCell;

import java.util.List;

public class PaginationCompaniesAdapter extends PaginationAdapter {

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
        Company company = (Company) objectList.get(position);

        if (getItemViewType(position) == ITEM_BACKDROP) {
            TextCell cell = (TextCell) ((Holder) holder).itemView;
            cell.setText(company.name);
            cell.setDivider(true);
        }
    }

    public void addAll(List<TmdbObject> companies) {
        for (TmdbObject company : companies) {
            add(company);
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Company());
    }
}