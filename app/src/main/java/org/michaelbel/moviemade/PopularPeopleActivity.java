package org.michaelbel.moviemade;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.view.MenuItem;

import org.michaelbel.moviemade.databinding.ActivityPopularPeopleBinding;
import org.michaelbel.moviemade.mvp.base.BaseActivity;
import org.michaelbel.moviemade.ui.fragment.PopularPeopleFragment;
import org.michaelbel.moviemade.util.AndroidUtilsDev;

public class PopularPeopleActivity extends BaseActivity {

    public ActivityPopularPeopleBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_popular_people);

        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) binding.toolbar.getLayoutParams();
        params.setScrollFlags(AndroidUtilsDev.floatingToolbar() ? AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP : 0);

        binding.toolbar.setLayoutParams(params);
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(binding.toolbar);

        binding.toolbarTitle.setText(R.string.PopularPeople);

        startFragment(PopularPeopleFragment.newInstance(), binding.fragmentView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}