package org.michaelbel.moviemade.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.mvp.base.BaseActivity;
import org.michaelbel.moviemade.rest.model.v3.Keyword;
import org.michaelbel.moviemade.ui.fragment.KeywordMoviesFragment;
import org.michaelbel.moviemade.utils.AndroidUtilsDev;

public class KeywordActivity extends BaseActivity {

    public Toolbar toolbar;
    public TextView toolbarTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyword);

        Keyword keyword = getIntent().getParcelableExtra("keyword");

        toolbar = findViewById(R.id.toolbar);
        toolbar.setLayoutParams(AndroidUtilsDev.getLayoutParams(toolbar));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());

        toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText(keyword.name);

        startFragment(KeywordMoviesFragment.newInstance(keyword.id), R.id.fragment_view);
    }
}