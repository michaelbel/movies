package org.michaelbel.moviemade;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;

import org.michaelbel.moviemade.databinding.ActivityWatchlistBinding;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.mvp.base.BaseActivity;
import org.michaelbel.moviemade.ui.fragment.WatchlistMoviesFragment;
import org.michaelbel.moviemade.ui.view.widget.FragmentsPagerAdapter;
import org.michaelbel.moviemade.util.AndroidUtilsDev;

public class WatchlistActivity extends BaseActivity {

    public ActivityWatchlistBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_watchlist);

        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) binding.toolbar.getLayoutParams();
        params.setScrollFlags(AndroidUtilsDev.floatingToolbar() ? AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP : 0);

        binding.toolbar.setLayoutParams(params);
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(binding.toolbar);

        binding.toolbarTitle.setText(R.string.Watchlist);

        FragmentsPagerAdapter adapter = new FragmentsPagerAdapter(this, getSupportFragmentManager());
        adapter.addFragment(new WatchlistMoviesFragment(), R.string.Movies);

        binding.viewPager.setAdapter(adapter);

        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        binding.tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        binding.tabLayout.setBackgroundColor(ContextCompat.getColor(this, Theme.primaryColor()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}