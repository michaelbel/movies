package org.michaelbel.moviemade.modules_beta.person;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;

import org.michaelbel.material.widget.Holder;
import org.michaelbel.tmdb.TmdbObject;
import org.michaelbel.moviemade.rest.model.v3.People;
import org.michaelbel.moviemade.ui.base.PaginationAdapter;
import org.michaelbel.moviemade.modules_beta.view.PersonView;
import org.michaelbel.moviemade.utils.AndroidUtils;

import java.util.List;

public class PaginationPeopleAdapter extends PaginationAdapter {

    //private PeekAndPop peekAndPop;

    public PaginationPeopleAdapter() {
    }

    /*public PaginationPeopleAdapter(PeekAndPop view) {
        peekAndPop = view;

        View peekView = peekAndPop.getPeekView();
        peekView.findViewById(R.id.arrow_down).setVisibility(View.VISIBLE);
        peekAndPop.addLongHoldView(R.id.arrow_down, true);
    }*/

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == ITEM_BACKDROP) {
            viewHolder = new Holder(new PersonView(parent.getContext()));
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        People p = (People) objectList.get(position);

        if (getItemViewType(position) == ITEM_BACKDROP) {
            PersonView view = (PersonView) ((Holder) holder).itemView;
            view.setName(p.name)
                .setCharacter(String.valueOf(p.popularity))
                .setProfile(p.profilePath)
                .setDivider(true);

            /*if (peekAndPop != null) {
                peekAndPop.addLongClickView(view, position);
            }*/
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