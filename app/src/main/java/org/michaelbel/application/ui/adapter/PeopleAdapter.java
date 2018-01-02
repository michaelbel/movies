package org.michaelbel.application.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.michaelbel.application.rest.model.People;
import org.michaelbel.application.ui.view.CastView;

import java.util.List;

public class PeopleAdapter extends RecyclerView.Adapter {

    private List<People> people;

    public PeopleAdapter(List<People> people) {
        this.people = people;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(new CastView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        People p = people.get(position);

        CastView view = (CastView) holder.itemView;
        view.setName(p.name)
            .setCharacter(String.valueOf(p.popularity))
            .setProfileImage(p.profilePath)
            .setDivider(true);
            //.setDivider(position != people.size() - 1);
    }

    @Override
    public int getItemCount() {
        return people != null ? people.size() : 0;
    }
}