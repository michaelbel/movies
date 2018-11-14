package org.michaelbel.moviemade.modules_beta.keywords;

import android.os.Bundle;
import android.widget.TextView;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.data.dao.Keyword;
import org.michaelbel.moviemade.ui.base.BaseActivity;
import org.michaelbel.moviemade.utils.AndroidUtilsDev;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

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
        toolbarTitle.setText(keyword.getName());

        startFragment(KeywordMoviesFragment.newInstance(keyword.getId()), R.id.fragment_view);
    }
}