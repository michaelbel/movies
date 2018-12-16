package org.michaelbel.moviemade.ui.modules.watchlist;

import android.os.Bundle;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.ui.base.BaseActivity;
import org.michaelbel.moviemade.utils.IntentsKt;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;

public class WatchlistActivity extends BaseActivity {

    // TODO make private.
    // TODO make add getter
    public int accountId;
    private WatchlistFragment fragment;

    // TODO make private.
    // TODO make add getter
    @BindView(R.id.toolbar) public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);

        accountId = getIntent().getIntExtra(IntentsKt.ACCOUNT_ID, 0);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.setOnClickListener(v -> fragment.recyclerView.smoothScrollToPosition(0));

        fragment = (WatchlistFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
    }
}