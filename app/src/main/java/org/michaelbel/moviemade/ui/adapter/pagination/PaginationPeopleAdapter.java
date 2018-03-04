package org.michaelbel.moviemade.ui.adapter.pagination;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.model.v3.People;
import org.michaelbel.moviemade.ui.adapter.pagination.base.PaginationAdapter;
import org.michaelbel.moviemade.ui.adapter.recycler.Holder;
import org.michaelbel.moviemade.ui.adapter.recycler.LoadingHolder;
import org.michaelbel.moviemade.ui.view.LoadingView;
import org.michaelbel.moviemade.ui.view.PersonView;
import org.michaelbel.moviemade.utils.AndroidUtils;

import java.util.List;

public class PaginationPeopleAdapter extends PaginationAdapter {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == ITEM) {
            viewHolder = new Holder(new PersonView(parent.getContext()));
        } else if (viewType == LOADING) {
            viewHolder = new LoadingHolder(new LoadingView(parent.getContext()));
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        People p = (People) objectList.get(position);

        if (getItemViewType(position) == ITEM) {
            PersonView view = (PersonView) ((Holder) holder).itemView;
            view.setName(p.name)
                .setCharacter(String.valueOf(p.popularity))
                .setProfile(p.profilePath)
                .setDivider(true);
        } else if (getItemViewType(position) == LOADING) {
            LoadingView view = (LoadingView) ((LoadingHolder) holder).itemView;
            view.setMode(LoadingView.MODE_DEFAULT);
        }
    }

    public void addAll(List<TmdbObject> people) {
        for (TmdbObject person : people) {
            if (AndroidUtils.includeAdult()) {
                add(person);
            } else {
                if (!((People) person).adult) {
                    add(person);
                }
            }
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new People());
    }
}