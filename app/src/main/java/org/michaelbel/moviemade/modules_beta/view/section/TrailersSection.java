package org.michaelbel.moviemade.modules_beta.view.section;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.michaelbel.material.widget.Holder;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.Theme;
import org.michaelbel.moviemade.rest.model.v3.Trailer;
import org.michaelbel.moviemade.ui.widgets.RecyclerListView;
import org.michaelbel.moviemade.LayoutHelper;
import org.michaelbel.moviemade.modules_beta.view.trailer.TrailerView;
import org.michaelbel.moviemade.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TrailersSection extends FrameLayout {

    private TrailersAdapter adapter;
    private SectionTrailersListener sectionListener;
    private List<Trailer> trailers = new ArrayList<>();

    private ProgressBar progressBar;
    private RecyclerListView recyclerView;

    public interface SectionTrailersListener {
        void onTrailerClick(View view, String trailerKey);
        void onTrailerLongClick(View view, String trailerKey);
    }

    public TrailersSection(Context context) {
        super(context);

        setClickable(false);
        setPadding(0, 0, 0, ScreenUtils.dp(4));
        setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));

        TextView textView = new TextView(context);
        textView.setLines(1);
        textView.setMaxLines(1);
        textView.setSingleLine();
        textView.setText(R.string.trailers);
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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView = new RecyclerListView(context);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 0, 48, 0, 0));
        recyclerView.setOnItemClickListener((view, position) -> {
            Trailer trailer = trailers.get(position);
            sectionListener.onTrailerClick(view, trailer.key);
        });
        recyclerView.setOnItemLongClickListener((view, position) -> {
            Trailer trailer = trailers.get(position);
            sectionListener.onTrailerLongClick(view, trailer.key);
            return true;
        });
        addView(recyclerView);
    }

    public TrailersSection setListener(SectionTrailersListener listener) {
        sectionListener = listener;
        return this;
    }

    public TrailersSection setAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
        return this;
    }

    public TrailersSection setTrailers(List<Trailer> list) {
        trailers.clear();
        trailers.addAll(list);
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

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Holder(new TrailerView(parent.getContext()));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
            Trailer trailer = trailers.get(position);

            TrailerView view = (TrailerView) holder.itemView;
            view.setTitle(trailer.name)
                .setQuality(trailer.size)
                .setSite(trailer.site)
                .setTrailerImage(trailer.key);

            if (position == 0) {
                view.changeLayoutParams(true);
            }

            if (position == trailers.size() - 1) {
                view.changeLayoutParams(false);
            }
        }

        @Override
        public int getItemCount() {
            return trailers != null ? trailers.size() : 0;
        }
    }
}