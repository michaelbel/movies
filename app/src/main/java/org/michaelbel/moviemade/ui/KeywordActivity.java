package org.michaelbel.moviemade.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.databinding.ActivityKeywordBinding;
import org.michaelbel.moviemade.mvp.base.BaseActivity;
import org.michaelbel.moviemade.rest.model.v3.Keyword;
import org.michaelbel.moviemade.ui.fragment.KeywordMoviesFragment;
import org.michaelbel.moviemade.util.AndroidUtilsDev;

public class KeywordActivity extends BaseActivity {

    public ActivityKeywordBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_keyword);

        Keyword keyword = getIntent().getParcelableExtra("keyword");

        binding.toolbar.setLayoutParams(AndroidUtilsDev.getLayoutParams(binding.toolbar));
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(view -> finish());

        binding.toolbarTitle.setText(keyword.name);
        binding.progressBar.setVisibility(View.GONE);

        startFragment(KeywordMoviesFragment.newInstance(keyword.id), binding.fragmentView);
    }
}