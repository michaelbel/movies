package org.michaelbel.moviemade.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.model.Cast;
import org.michaelbel.moviemade.ui.adapter.recycler.Holder;
import org.michaelbel.moviemade.ui.view.PersonView;

import java.util.ArrayList;
import java.util.List;

public class CastMoviesAdapter extends RecyclerView.Adapter {

    private List<TmdbObject> casts;

    public CastMoviesAdapter() {
        casts = new ArrayList<>();
    }

    public void addCasts(List<TmdbObject> results) {
        casts.addAll(results);
        notifyItemRangeInserted(casts.size() + 1, results.size());
    }

    public List<TmdbObject> getCasts() {
        return casts;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(new PersonView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Cast cast = (Cast) casts.get(position);

        PersonView view = (PersonView) holder.itemView;
        view.setName(cast.name)
            .setCharacter(cast.character)
            .setProfile(cast.profilePath)
            .setDivider(position != casts.size() - 1);
    }

    @Override
    public int getItemCount() {
        return casts != null ? casts.size() : 0;
    }
}