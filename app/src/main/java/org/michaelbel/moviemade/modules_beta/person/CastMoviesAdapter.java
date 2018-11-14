package org.michaelbel.moviemade.modules_beta.person;

import android.view.ViewGroup;

import org.michaelbel.material.widget.Holder;
import org.michaelbel.moviemade.data.dao.Cast;
import org.michaelbel.moviemade.modules_beta.view.PersonView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CastMoviesAdapter extends RecyclerView.Adapter {

    private List<Cast> casts;

    public CastMoviesAdapter() {
        casts = new ArrayList<>();
    }

    public void addCasts(List<Cast> results) {
        casts.addAll(results);
        notifyItemRangeInserted(casts.size() + 1, results.size());
    }

    public List<Cast> getCasts() {
        return casts;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(new PersonView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Cast cast = casts.get(position);

        PersonView view = (PersonView) holder.itemView;
        view.setName(cast.getName())
            .setCharacter(cast.getCharacter())
            .setProfile(cast.getProfilePath())
            .setDivider(position != casts.size() - 1);
    }

    @Override
    public int getItemCount() {
        return casts != null ? casts.size() : 0;
    }
}