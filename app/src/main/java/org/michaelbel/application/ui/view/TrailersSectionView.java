package org.michaelbel.application.ui.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.michaelbel.application.R;
import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.ui.view.widget.RecyclerListView;

@SuppressWarnings("all")
public class TrailersSectionView extends FrameLayout {

    private ProgressBar progressBar;
    private RecyclerListView recyclerView;
    private SectionTrailersListener sectionListener;

    public interface SectionTrailersListener {
        void onTrailerClick(View view, int position);
        boolean onTrailerLongClick(View view, int position);
    }

    public TrailersSectionView(Context context) {
        super(context);

        setClickable(false);
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
        textView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, 48, Gravity.START | Gravity.TOP, 16, 0, 16 + 16 + 24, 0));
        addView(textView);

        progressBar = new ProgressBar(context);
        progressBar.setLayoutParams(LayoutHelper.makeFrame(24, 24, Gravity.END | Gravity.TOP, 0, 8, 16, 8));
        addView(progressBar);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView = new RecyclerListView(context);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 0, 48, 0, 0));
        recyclerView.setOnItemClickListener((view, position) -> {
            if (sectionListener != null) {
                sectionListener.onTrailerClick(view, position);
            }
        });
        recyclerView.setOnItemLongClickListener((view, position) -> {
            if (sectionListener != null) {
                sectionListener.onTrailerLongClick(view, position);
                return true;
            }
            return false;
        });
        addView(recyclerView);
    }

    public TrailersSectionView setListener(SectionTrailersListener listener) {
        sectionListener = listener;
        return this;
    }

    public TrailersSectionView setAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
        return this;
    }

    public RecyclerListView getRecyclerView() {
        return recyclerView;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        setMeasuredDimension(width, height);
    }
}