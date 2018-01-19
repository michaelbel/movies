package org.michaelbel.moviemade.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.model.Cast;
import org.michaelbel.moviemade.ui.view.CastView;

import java.util.List;

public class CastMoviesAdapter extends RecyclerView.Adapter {

    private List<TmdbObject> casts;

    public CastMoviesAdapter(List<TmdbObject> casts) {
        this.casts = casts;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(new CastView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Cast cast = (Cast) casts.get(position);

        CastView view = (CastView) holder.itemView;
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