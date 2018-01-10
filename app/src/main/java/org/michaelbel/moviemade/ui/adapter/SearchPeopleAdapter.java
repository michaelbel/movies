package org.michaelbel.moviemade.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.michaelbel.moviemade.rest.model.People;
import org.michaelbel.moviemade.ui.view.CastView;

import java.util.List;

public class SearchPeopleAdapter extends RecyclerView.Adapter {

    private List<People> searches;

    public SearchPeopleAdapter(List<People> searches) {
        this.searches = searches;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        return new Holder(new CastView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        People people = searches.get(position);

        CastView view = (CastView) holder.itemView;
        view.setProfile(people.profilePath)
            .setName(people.name)
            .setCharacter(String.valueOf(people.popularity))
            .setDivider(position != searches.size() - 1);
    }

    @Override
    public int getItemCount() {
        return searches != null ? searches.size() : 0;
    }
}