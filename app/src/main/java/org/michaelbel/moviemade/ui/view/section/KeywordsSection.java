package org.michaelbel.moviemade.ui.view.section;

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

import org.michaelbel.moviemade.app.LayoutHelper;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.rest.model.v3.Keyword;
import org.michaelbel.moviemade.ui.adapter.recycler.Holder;
import org.michaelbel.moviemade.ui.view.ChipView;
import org.michaelbel.moviemade.ui.view.widget.RecyclerListView;

import java.util.ArrayList;
import java.util.List;

public class KeywordsSection extends FrameLayout {

    private KeywordsAdapter adapter;
    private KeywordsSectionListener keywordsListener;
    private List<Keyword> keywords = new ArrayList<>();

    private ProgressBar progressBar;

    public interface KeywordsSectionListener {
        void onKeywordClick(View view, Keyword keyword);
    }

    public KeywordsSection(@NonNull Context context) {
        super(context);

        setClickable(false);
        setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));

        ChipsLayoutManager chipsLayoutManager = ChipsLayoutManager.newBuilder(context)
                .setChildGravity(Gravity.START)
                .setScrollingEnabled(false)
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .setRowStrategy(ChipsLayoutManager.STRATEGY_DEFAULT)
                .withLastRow(true)
                .build();

        adapter = new KeywordsAdapter();

        TextView textView = new TextView(context);
        textView.setLines(1);
        textView.setMaxLines(1);
        textView.setSingleLine();
        textView.setText("Keywords");
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
            Keyword keyword = keywords.get(position);
            keywordsListener.onKeywordClick(view, keyword);
        });
        addView(recyclerView);
    }

    public KeywordsSection setKeywords(List<Keyword> keywords) {
        this.keywords.addAll(keywords);
        adapter.notifyDataSetChanged();
        return this;
    }

    public KeywordsSection setListener(KeywordsSectionListener listener) {
        keywordsListener = listener;
        return this;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public List<Keyword> getKeywords() {
        return keywords;
    }

    private class KeywordsAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(new ChipView(getContext()));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            Keyword keyword = keywords.get(position);

            ChipView view = (ChipView) holder.itemView;
            view.setText(keyword.name)
                .changeLayoutParams();
        }

        @Override
        public int getItemCount() {
            return keywords != null ? keywords.size() : 0;
        }
    }
}