package org.michaelbel.moviemade.ui.modules.watchlist;

import android.os.Bundle;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.ui.base.BaseActivity;
import org.michaelbel.moviemade.utils.IntentsKt;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class WatchlistActivity extends BaseActivity {

    public int accountId;
    private Unbinder unbinder;

    @BindView(R.id.toolbar) public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);
        unbinder = ButterKnife.bind(this);

        accountId = getIntent().getIntExtra(IntentsKt.ACCOUNT_ID, 0);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}