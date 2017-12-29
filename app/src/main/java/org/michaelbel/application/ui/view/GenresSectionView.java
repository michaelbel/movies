package org.michaelbel.application.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;

import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.rest.model.Genre;
import org.michaelbel.application.ui.adapter.Holder;
import org.michaelbel.application.ui.view.widget.RecyclerListView;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
public class GenresSectionView extends FrameLayout {

    private ChipAdapter adapter;
    private List<Genre> genresList = new ArrayList<>();

    public GenresSectionView(@NonNull Context context) {
        super(context);

        ChipsLayoutManager manager = ChipsLayoutManager.newBuilder(context)
                .setChildGravity(Gravity.START)
                .setScrollingEnabled(false)
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .setRowStrategy(ChipsLayoutManager.STRATEGY_DEFAULT)
                .withLastRow(true)
                .build();

        adapter = new ChipAdapter();

        RecyclerListView recyclerView = new RecyclerListView(context);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        recyclerView.setOnItemClickListener((view, position) -> {
            int genreId = genresList.get(position).id;
            // todo
        });
        addView(recyclerView);
    }

    public GenresSectionView setGenresList(List<Genre> list) {
        genresList.addAll(list);
        adapter.notifyDataSetChanged();
        return this;
    }

    private class ChipAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(new GenreChip(getContext()));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            Genre genre = genresList.get(position);

            GenreChip view = (GenreChip) holder.itemView;
            view.setText(genre.name)
                .changeLayoutParams();
        }

        @Override
        public int getItemCount() {
            return genresList != null ? genresList.size() : 0;
        }
    }
}