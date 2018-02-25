package org.michaelbel.moviemade.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.TextView;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.mvp.base.BaseActivity;
import org.michaelbel.moviemade.rest.model.v3.Collection;
import org.michaelbel.moviemade.ui.fragment.CollectionFragment;

public class CollectionActivity extends BaseActivity {

    public Toolbar toolbar;
    public TextView toolbarTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(0x33000000);

        Collection collection = getIntent().getParcelableExtra("collection");

        /*binding.collapsingLayout.setContentScrimColor(ContextCompat.getColor(this, Theme.primaryColor()));
        binding.collapsingLayout.setStatusBarScrimColor(ContextCompat.getColor(this, android.R.color.transparent));
        binding.collapsingLayout.setTitleEnabled(true);
        binding.collapsingLayout.setCollapsedTitleTextAppearance(R.style.TitleCollapsed);
        binding.collapsingLayout.setExpandedTitleTextAppearance(R.style.TitleExpanded);
        binding.collapsingLayout.setExpandedTitleMarginStart(ScreenUtils.dp(16));
        binding.collapsingLayout.setExpandedTitleMarginEnd(ScreenUtils.dp(88));
        binding.collapsingLayout.setExpandedTitleMarginBottom(ScreenUtils.dp(16));
        binding.collapsingLayout.setExpandedTitleTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        binding.collapsingLayout.setCollapsedTitleTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));

        boolean isExpanded = (binding.appbarLayout.getHeight() - binding.appbarLayout.getBottom()) == 0;
        if (!isExpanded) {
            binding.appbarLayout.setExpanded(true, true);
        }

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) binding.appbarLayout.getLayoutParams();
        if (params.getBehavior() != null) {
            ((AppBarLayoutBehavior) params.getBehavior()).setEnabled(true);
        }*/

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());

        toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText(collection.name);

        startFragment(CollectionFragment.newInstance(collection), R.id.fragment_view);
    }
}