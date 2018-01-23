package org.michaelbel.moviemade.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.model.v3.People;
import org.michaelbel.moviemade.ui.adapter.recycler.Holder;
import org.michaelbel.moviemade.ui.view.CastView;

import java.util.ArrayList;
import java.util.List;

public class SearchPeopleAdapter extends RecyclerView.Adapter {

    private List<TmdbObject> searches;

    public SearchPeopleAdapter() {
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
        return new Holder(new CastView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        People people = (People) searches.get(position);

        CastView view = (CastView) holder.itemView;
        view.setProfile(people.profilePath)
            .setName(people.name)
            .setCharacter(String.valueOf(people.popularity))
            .setDivider(true);
    }

    @Override
    public int getItemCount() {
        return searches != null ? searches.size() : 0;
    }
}