package org.michaelbel.moviemade.modules_beta.company;

import android.view.ViewGroup;

import org.michaelbel.material.widget.Holder;
import org.michaelbel.moviemade.data.TmdbObject;
import org.michaelbel.moviemade.modules_beta.view.cell.TextCell;
import org.michaelbel.moviemade.ui.base.PaginationAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        //Company company = (Company) parts.get(position);

        if (getItemViewType(position) == ITEM_BACKDROP) {
            TextCell cell = (TextCell) ((Holder) holder).itemView;
          //  cell.setText(company.name);
            cell.setDivider(true);
        }
    }

    public void addAll(List<TmdbObject> companies) {
        for (TmdbObject company : companies) {
         //   add(company);
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
       // add(new Company());
    }
}