package org.michaelbel.moviemade.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.databinding.ActivityCollectionBinding;
import org.michaelbel.moviemade.mvp.base.BaseActivity;
import org.michaelbel.moviemade.rest.model.v3.Collection;
import org.michaelbel.moviemade.ui.fragment.CollectionFragment;

public class CollectionActivity extends BaseActivity {

    public ActivityCollectionBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_collection);

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

        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        //binding.toolbar.setLayoutParams(AndroidUtilsDev.getLayoutParams(binding.toolbar));
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(view -> finish());

        //binding.toolbarTitle.setText(collection.name);
        //binding.collapsingLayout.setTitle(collection.name);

        startFragment(CollectionFragment.newInstance(collection), binding.fragmentView);
    }
}