package org.michaelbel.moviemade;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import org.michaelbel.moviemade.databinding.ActivityMainBinding;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.mvp.base.BaseActivity;
import org.michaelbel.moviemade.ui.fragment.ListMoviesFragment;
import org.michaelbel.moviemade.ui.view.widget.FragmentsPagerAdapter;
import org.michaelbel.moviemade.util.AndroidUtils;
import org.michaelbel.moviemade.util.AndroidUtilsDev;

public class MainActivity extends BaseActivity {

    public ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppThemeLight);
        super.onCreate(savedInstanceState);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(0x33000000);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.toolbar.setNavigationIcon(AndroidUtilsDev.hamburgerIcon() ? R.drawable.ic_hamburger : R.drawable.ic_menu);
        setSupportActionBar(binding.toolbar);

        binding.navigationView.setOnNavigationItemSelectedListener((view, position) -> {
            binding.drawerLayout.closeDrawer(GravityCompat.START);

            if (position == -1) {
                // Movies
            } else if (position == -200) {
                startActivity(new Intent(this, GenresActivity.class));
            } else if (position == 2) {
                startActivity(new Intent(this, PopularPeopleActivity.class));
            } else if (position == -2) {
                //startActivity(new Intent(this, WatchlistActivity.class));
            } else if (position == -3) {
                //startActivity(new Intent(this, FavoriteActivity.class));
            } else if (position == 4) {
                startActivity(new Intent(this, SettingsActivity.class));
            } else if (position == 5) {
                startActivity(new Intent(this, AboutActivity.class));
            }
        });

        FragmentsPagerAdapter adapter = new FragmentsPagerAdapter(this, getSupportFragmentManager());
        adapter.addFragment(ListMoviesFragment.newInstance(ListMoviesFragment.LIST_NOW_PLAYING), R.string.NowPlaying);
        adapter.addFragment(ListMoviesFragment.newInstance(ListMoviesFragment.LIST_POPULAR), R.string.Popular);
        adapter.addFragment(ListMoviesFragment.newInstance(ListMoviesFragment.LIST_TOP_RATED), R.string.TopRated);
        adapter.addFragment(ListMoviesFragment.newInstance(ListMoviesFragment.LIST_UPCOMING), R.string.Upcoming);
        binding.viewPager.setAdapter(adapter);

        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        binding.tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        binding.tabLayout.setBackgroundColor(ContextCompat.getColor(this, Theme.primaryColor()));
        binding.tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, Theme.accentColor()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(R.string.Search)
            .setIcon(R.drawable.ic_search)
            .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM)
            .setOnMenuItemClickListener(item -> {
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                return true;
            });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (binding.drawerLayout != null) {
                binding.drawerLayout.openDrawer(GravityCompat.START);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout != null && binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_MENU) {
            if (!binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                if (AndroidUtils.isKeyboardShowed(getCurrentFocus())) {
                    AndroidUtils.hideKeyboard(getCurrentFocus());
                    binding.drawerLayout.openDrawer(GravityCompat.START);
                }
            } else {
                binding.drawerLayout.closeDrawer(GravityCompat.START);
            }
        }

        return super.onKeyLongPress(keyCode, event);
    }
}