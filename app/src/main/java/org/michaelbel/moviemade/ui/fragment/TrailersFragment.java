package org.michaelbel.moviemade.ui.fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.arellomobile.mvp.presenter.InjectPresenter;

import org.michaelbel.material.extensions.Extensions;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.annotation.EmptyViewMode;
import org.michaelbel.moviemade.mvp.presenter.TrailersPresenter;
import org.michaelbel.moviemade.mvp.view.MvpTrailersView;
import org.michaelbel.moviemade.rest.model.v3.Trailer;
import org.michaelbel.moviemade.ui.activity.TrailersActivity;
import org.michaelbel.moviemade.ui.widget.RecyclerListView;
import org.michaelbel.moviemade.ui_old.adapter.TrailersAdapter;
import org.michaelbel.moviemade.ui.view.EmptyView;
import org.michaelbel.moviemade.ui_old.view.widget.PaddingItemDecoration;
import org.michaelbel.moxy.android.MvpAppCompatFragment;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TrailersFragment extends MvpAppCompatFragment implements MvpTrailersView {

    private TrailersAdapter adapter;
    private TrailersActivity activity;
    private GridLayoutManager gridLayoutManager;
    private PaddingItemDecoration itemDecoration;

    private EmptyView emptyView;
    private ProgressBar progressBar;
    public RecyclerListView recyclerView;

    @InjectPresenter
    public TrailersPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (TrailersActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trailers, container, false);
        emptyView = view.findViewById(R.id.empty_view);
        progressBar = view.findViewById(R.id.progress_bar);
        recyclerView = view.findViewById(R.id.recycler_view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        itemDecoration = new PaddingItemDecoration();
        itemDecoration.setOffset(Extensions.dp(activity, 4));

        int spanCount = activity.getResources().getInteger(R.integer.trailers_span_layout_count);

        adapter = new TrailersAdapter();
        gridLayoutManager = new GridLayoutManager(activity, spanCount);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);

        emptyView.setOnClickListener(v -> {
            emptyView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            presenter.loadTrailers(activity.movieId);
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setEmptyView(emptyView);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setPadding(0, Extensions.dp(activity,2), 0, Extensions.dp(activity,2));
        recyclerView.setOnItemClickListener((v, position) -> {
            Trailer trailer = adapter.trailers.get(position);
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailer.key)));
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        refreshLayout();
    }

    @Override
    public void setTrailers(ArrayList<Trailer> trailers) {
        adapter.setTrailers(trailers);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        emptyView.setVisibility(View.VISIBLE);
        emptyView.setMode(EmptyViewMode.MODE_NO_CONNECTION);
        progressBar.setVisibility(View.GONE);
    }

    private void refreshLayout() {
        int spanCount = activity.getResources().getInteger(R.integer.trailers_span_layout_count);

        Parcelable state = gridLayoutManager.onSaveInstanceState();
        gridLayoutManager = new GridLayoutManager(activity, spanCount);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.removeItemDecoration(itemDecoration);
        itemDecoration.setOffset(0);
        recyclerView.addItemDecoration(itemDecoration);
        gridLayoutManager.onRestoreInstanceState(state);
    }
}