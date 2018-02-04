package org.michaelbel.moviemade.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.databinding.ActivityFavoriteBinding;
import org.michaelbel.moviemade.mvp.base.BaseActivity;
import org.michaelbel.moviemade.ui.fragment.FavoriteMoviesFragment;
import org.michaelbel.moviemade.ui.view.widget.FragmentsPagerAdapter;
import org.michaelbel.moviemade.utils.AndroidUtilsDev;

public class FavoriteActivity extends BaseActivity {

    public ActivityFavoriteBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_favorite);

        binding.toolbar.setLayoutParams(AndroidUtilsDev.getLayoutParams(binding.toolbar));
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(view -> finish());

        binding.toolbarTitle.setText(R.string.Favorites);

        FragmentsPagerAdapter adapter = new FragmentsPagerAdapter(this, getSupportFragmentManager());
        adapter.addFragment(new FavoriteMoviesFragment(), R.string.Movies);

        binding.viewPager.setAdapter(adapter);

        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        binding.tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        binding.tabLayout.setBackgroundColor(ContextCompat.getColor(this, Theme.primaryColor()));
        binding.tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, Theme.accentColor()));
        binding.tabLayout.setTabTextColors(ContextCompat.getColor(this, Theme.secondaryTextColor()), ContextCompat.getColor(this, Theme.accentColor()));
    }
}