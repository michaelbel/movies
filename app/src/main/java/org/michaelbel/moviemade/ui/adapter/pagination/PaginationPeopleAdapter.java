package org.michaelbel.moviemade.ui.adapter.pagination;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.model.v3.People;
import org.michaelbel.moviemade.ui.adapter.recycler.Holder;
import org.michaelbel.moviemade.ui.adapter.recycler.LoadingHolder;
import org.michaelbel.moviemade.ui.view.LoadingView;
import org.michaelbel.moviemade.ui.view.PersonView;

import java.util.ArrayList;
import java.util.List;

public class PaginationPeopleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int ITEM = 0;
    private final int LOADING = 1;

    private List<TmdbObject> people;
    private boolean isLoadingAdded = false;

    public PaginationPeopleAdapter() {
        people = new ArrayList<>();
    }

    public List<TmdbObject> getPeople() {
        return people;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == ITEM) {
            viewHolder = new Holder(new PersonView(parent.getContext()));
        } else if (viewType == LOADING) {
            viewHolder = new LoadingHolder(new LoadingView(parent.getContext()));
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        People p = (People) people.get(position);

        if (getItemViewType(position) == ITEM) {
            PersonView view = (PersonView) ((Holder) holder).itemView;
            view.setName(p.name)
                .setCharacter(String.valueOf(p.popularity))
                .setProfile(p.profilePath)
                .setDivider(true);
        }
    }

    @Override
    public int getItemCount() {
        return people != null ? people.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == people.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

//--------------------------------------------------------------------------------------------------

    public void add(TmdbObject p) {
        people.add(p);
        notifyItemInserted(people.size() - 1);
    }

    public void addAll(List<TmdbObject> people) {
        for (TmdbObject movie : people) {
            add(movie);
        }
    }

    public void remove(TmdbObject p) {
        int position = people.indexOf(p);

        if (position > -1) {
            people.remove(position);
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
        add(new People());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = people.size() - 1;
        TmdbObject result = getItem(position);

        if (result != null) {
            people.remove(position);
            notifyItemRemoved(position);
        }
    }

    public TmdbObject getItem(int position) {
        return people.get(position);
    }
}