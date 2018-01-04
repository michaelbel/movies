package org.michaelbel.application.ui.view.section;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;

import org.michaelbel.application.R;
import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.rest.model.Genre;
import org.michaelbel.application.ui.adapter.Holder;
import org.michaelbel.application.ui.view.GenreChip;
import org.michaelbel.application.ui.view.widget.RecyclerListView;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
public class GenresSectionView extends FrameLayout {

    private GenresAdapter adapter;
    private List<Genre> genres = new ArrayList<>();
    private GenresSectionListener genresSectionListener;

    private ProgressBar progressBar;

    public interface GenresSectionListener {
        void onGenreButtonClick(View view, Genre genre);
    }

    public GenresSectionView(@NonNull Context context) {
        super(context);

        setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));

        ChipsLayoutManager chipsLayoutManager = ChipsLayoutManager.newBuilder(context)
                .setChildGravity(Gravity.START)
                .setScrollingEnabled(false)
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .setRowStrategy(ChipsLayoutManager.STRATEGY_DEFAULT)
                .withLastRow(true)
                .build();

        adapter = new GenresAdapter();

        TextView textView = new TextView(context);
        textView.setLines(1);
        textView.setMaxLines(1);
        textView.setSingleLine();
        textView.setText(R.string.Genres);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        textView.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        textView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        textView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, 48, Gravity.START | Gravity.TOP, 16, 0, 56, 0));
        addView(textView);

        progressBar = new ProgressBar(context);
        progressBar.setLayoutParams(LayoutHelper.makeFrame(24, 24, Gravity.END | Gravity.TOP, 0, 12, 16, 12));
        addView(progressBar);

        RecyclerListView recyclerView = new RecyclerListView(context);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(chipsLayoutManager);
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 13.5F, 48, 13.5F, 12));
        recyclerView.setOnItemClickListener((view, position) -> {
            Genre genre = genres.get(position);
            if (genresSectionListener != null) {
                genresSectionListener.onGenreButtonClick(view, genre);
            }
        });
        addView(recyclerView);
    }

    public GenresSectionView setGenres(List<Genre> list) {
        genres.addAll(list);
        adapter.notifyDataSetChanged();
        return this;
    }

    public GenresSectionView setListener(GenresSectionListener listener) {
        genresSectionListener = listener;
        return this;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    private class GenresAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(new GenreChip(getContext()));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            Genre genre = genres.get(position);

            GenreChip view = (GenreChip) holder.itemView;
            view.setText(genre.name)
                .changeLayoutParams();
        }

        @Override
        public int getItemCount() {
            return genres != null ? genres.size() : 0;
        }
    }
}