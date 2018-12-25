package org.michaelbel.moviemade.ui.modules.keywords.fragment;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.data.entity.Keyword;
import org.michaelbel.moviemade.ui.base.BaseFragment;
import org.michaelbel.moviemade.ui.modules.keywords.KeywordsContract;
import org.michaelbel.moviemade.ui.modules.keywords.activity.KeywordsActivity;
import org.michaelbel.moviemade.ui.modules.keywords.adapter.KeywordsAdapter;
import org.michaelbel.moviemade.ui.receivers.NetworkChangeListener;
import org.michaelbel.moviemade.ui.receivers.NetworkChangeReceiver;
import org.michaelbel.moviemade.ui.widgets.EmptyView;
import org.michaelbel.moviemade.ui.widgets.RecyclerListView;
import org.michaelbel.moviemade.utils.EmptyViewMode;
import org.michaelbel.moviemade.utils.IntentsKt;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;

public class KeywordsFragment extends BaseFragment implements KeywordsContract.View, NetworkChangeListener {

    private int movieId;
    private KeywordsAdapter adapter;
    private KeywordsActivity activity;

    private NetworkChangeReceiver networkChangeReceiver;
    private boolean connectionFailure = false;

    @Inject KeywordsContract.Presenter presenter;

    @BindView(R.id.empty_view) EmptyView emptyView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.recycler_view) RecyclerListView recyclerView;

    public static KeywordsFragment newInstance(int movieId) {
        Bundle args = new Bundle();
        args.putInt(IntentsKt.MOVIE_ID, movieId);

        KeywordsFragment fragment = new KeywordsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (KeywordsActivity) getActivity();

        networkChangeReceiver = new NetworkChangeReceiver(this);
        activity.registerReceiver(networkChangeReceiver, new IntentFilter(NetworkChangeReceiver.INTENT_ACTION));

        Moviemade.get(activity).getFragmentComponent().inject(this);
        presenter.setView(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_keywords, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity.getToolbar().setOnClickListener(v -> recyclerView.smoothScrollToPosition(0));

        adapter = new KeywordsAdapter();

        recyclerView.setAdapter(adapter);
        recyclerView.setEmptyView(emptyView);
        recyclerView.setLayoutManager(ChipsLayoutManager.newBuilder(activity).setOrientation(ChipsLayoutManager.HORIZONTAL).build());
        recyclerView.setOnItemClickListener((v, position) -> {
            Keyword keyword = adapter.getKeywords().get(position);
            activity.startKeyword(keyword);
        });

        emptyView.setOnClickListener(v -> {
            emptyView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            presenter.getKeywords(movieId);
        });

        movieId = getArguments() != null ? getArguments().getInt(IntentsKt.MOVIE_ID) : 0;
        presenter.getKeywords(movieId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity.unregisterReceiver(networkChangeReceiver);
        presenter.onDestroy();
    }

    @Override
    public void setKeywords(@NotNull List<Keyword> keywords) {
        connectionFailure = false;
        adapter.addKeywords(keywords);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setError(@EmptyViewMode int mode) {
        connectionFailure = true;
        emptyView.setVisibility(View.VISIBLE);
        emptyView.setMode(mode);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onNetworkChanged() {
        if (connectionFailure && adapter.getItemCount() == 0) {
            presenter.getKeywords(movieId);
        }
    }
}