package org.michaelbel.moviemade.ui.modules.favorites;

import android.os.Bundle;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.ui.base.BaseActivity;
import org.michaelbel.moviemade.utils.IntentsKt;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;

public class FavoriteActivity extends BaseActivity {

    private int accountId;
    private FavoritesFragment fragment;

    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        accountId = getIntent().getIntExtra(IntentsKt.ACCOUNT_ID, 0);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.setOnClickListener(v -> fragment.getRecyclerView().smoothScrollToPosition(0));

        fragment = (FavoritesFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
    }

    public int getAccountId() {
        return accountId;
    }
}