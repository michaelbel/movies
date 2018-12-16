package org.michaelbel.moviemade.ui.modules.keywords.fragment;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.data.entity.Keyword;
import org.michaelbel.moviemade.receivers.NetworkChangeListener;
import org.michaelbel.moviemade.receivers.NetworkChangeReceiver;
import org.michaelbel.moviemade.ui.base.BaseFragment;
import org.michaelbel.moviemade.ui.modules.keywords.KeywordsAdapter;
import org.michaelbel.moviemade.ui.modules.keywords.KeywordsMvp;
import org.michaelbel.moviemade.ui.modules.keywords.KeywordsPresenter;
import org.michaelbel.moviemade.ui.modules.keywords.activity.KeywordsActivity;
import org.michaelbel.moviemade.ui.widgets.EmptyView;
import org.michaelbel.moviemade.ui.widgets.RecyclerListView;
import org.michaelbel.moviemade.utils.EmptyViewMode;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

public class KeywordsFragment extends BaseFragment implements KeywordsMvp, NetworkChangeListener {

    private KeywordsAdapter adapter;
    private KeywordsActivity activity;
    private NetworkChangeReceiver networkChangeReceiver;
    private boolean connectionFailure = false;

    @InjectPresenter KeywordsPresenter presenter;

    @BindView(R.id.empty_view) EmptyView emptyView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.recycler_view) public RecyclerListView recyclerView;

    public KeywordsPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (KeywordsActivity) getActivity();
        networkChangeReceiver = new NetworkChangeReceiver(this);
        activity.registerReceiver(networkChangeReceiver, new IntentFilter(NetworkChangeReceiver.INTENT_ACTION));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new KeywordsAdapter();

        recyclerView.setAdapter(adapter);
        recyclerView.setEmptyView(emptyView);
        recyclerView.setLayoutManager(ChipsLayoutManager.newBuilder(activity).setOrientation(ChipsLayoutManager.HORIZONTAL).build());
        recyclerView.setOnItemClickListener((v, position) -> {
            Keyword keyword = adapter.getKeywords().get(position);
            activity.startKeyword(keyword);
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_keywords;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity.unregisterReceiver(networkChangeReceiver);
        presenter.onDestroy();
    }

    @OnClick(R.id.empty_view)
    void emptyViewClick(View v) {
        emptyView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        presenter.getKeywords(activity.getMovie().getId());
    }

    @Override
    public void setKeywords(@NotNull List<Keyword> keywords) {
        connectionFailure = false;
        adapter.setKeywords(keywords);
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
            presenter.getKeywords(activity.getMovie().getId());
        }
    }
}