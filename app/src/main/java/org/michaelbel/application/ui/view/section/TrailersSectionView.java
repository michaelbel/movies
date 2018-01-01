package org.michaelbel.application.ui.view.section;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.michaelbel.application.R;
import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.rest.model.Trailer;
import org.michaelbel.application.ui.adapter.Holder;
import org.michaelbel.application.ui.view.trailer.TrailerView;
import org.michaelbel.application.ui.view.widget.RecyclerListView;
import org.michaelbel.application.util.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
public class TrailersSectionView extends FrameLayout {

    private TrailersAdapter adapter;
    private List<Trailer> trailersList = new ArrayList<>();
    private SectionTrailersListener sectionTrailersListener;

    private ProgressBar progressBar;
    private RecyclerListView recyclerView;

    public interface SectionTrailersListener {
        void onTrailerClick(View view, String trailerKey);
        boolean onTrailerLongClick(View view, String trailerKey);
    }

    public TrailersSectionView(Context context) {
        super(context);

        setPadding(0, 0, 0, ScreenUtils.dp(4));
        setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));

        TextView textView = new TextView(context);
        textView.setLines(1);
        textView.setMaxLines(1);
        textView.setSingleLine();
        textView.setText(R.string.Trailers);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        textView.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        textView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        textView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, 48, Gravity.START | Gravity.TOP, 16, 0, 56, 0));
        addView(textView);

        progressBar = new ProgressBar(context);
        progressBar.setLayoutParams(LayoutHelper.makeFrame(24, 24, Gravity.END | Gravity.TOP, 0, 12, 16, 12));
        addView(progressBar);

        adapter = new TrailersAdapter();

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView = new RecyclerListView(context);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 0, 48, 0, 0));
        recyclerView.setOnItemClickListener((view, position) -> {
            if (sectionTrailersListener != null) {
                Trailer trailer = trailersList.get(position);
                sectionTrailersListener.onTrailerClick(view, trailer.key);
            }
        });
        recyclerView.setOnItemLongClickListener((view, position) -> {
            if (sectionTrailersListener != null) {
                Trailer trailer = trailersList.get(position);
                sectionTrailersListener.onTrailerLongClick(view, trailer.key);
                return true;
            }
            return false;
        });
        addView(recyclerView);
    }

    public TrailersSectionView setListener(SectionTrailersListener listener) {
        sectionTrailersListener = listener;
        return this;
    }

    public TrailersSectionView setAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
        return this;
    }

    public TrailersSectionView setTrailersList(List<Trailer> list) {
        trailersList.addAll(list);
        adapter.notifyDataSetChanged();
        return this;
    }

    public RecyclerListView getRecyclerView() {
        return recyclerView;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public class TrailersAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(new TrailerView(getContext()));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            Trailer trailer = trailersList.get(position);

            TrailerView view = (TrailerView) holder.itemView;
            view.setTitle(trailer.name)
                .setQuality(trailer.size)
                .setSite(trailer.site)
                .setTrailerImage(trailer.key);

            if (position == 0) {
                view.changeLayoutParams(true);
            }

            if (position == trailersList.size() - 1) {
                view.changeLayoutParams(false);
            }
        }

        @Override
        public int getItemCount() {
            return trailersList != null ? trailersList.size() : 0;
        }
    }
}