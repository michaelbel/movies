package org.michaelbel.moviemade.ui.modules.favorite;

import android.os.Bundle;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.ui.base.BaseActivity;
import org.michaelbel.moviemade.utils.IntentsKt;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteActivity extends BaseActivity {

    public int accountId;

    @BindView(R.id.toolbar) public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        ButterKnife.bind(this);

        accountId = getIntent().getIntExtra(IntentsKt.ACCOUNT_ID, 0);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
    }
}