package org.michaelbel.moviemade.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.Moviemade;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.app.eventbus.Events;
import org.michaelbel.moviemade.mvp.base.BaseActivity;
import org.michaelbel.moviemade.ui.fragment.ListMoviesFragment;
import org.michaelbel.moviemade.ui.view.NavigationView;
import org.michaelbel.moviemade.ui.view.widget.FragmentsPagerAdapter;

public class MainActivity extends BaseActivity {

    public Toolbar toolbar;
    public ViewPager viewPager;
    public TabLayout tabLayout;
    public DrawerLayout drawerLayout;
    public NavigationView navigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppThemeLight);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(0x33000000);

        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));

        navigationView.setOnNavigationItemSelectedListener((view, position) -> {
            drawerLayout.closeDrawer(GravityCompat.START);

            if (position == 2) {
                // MainActivity
            } else if (position == 3) {
                startActivity(new Intent(this, GenresActivity.class));
                this.finish();
            } else if (position == 4) {
                startActivity(new Intent(this, PopularPeopleActivity.class));
                this.finish();
            } else if (position == 6) {
                startActivity(new Intent(this, WatchlistActivity.class));
            } else if (position == 7) {
                startActivity(new Intent(this, FavoriteActivity.class));
            } else if (position == 9) {
                startActivity(new Intent(this, SettingsActivity.class));
            } else if (position == 10) {
                startActivity(new Intent(this, AboutActivity.class));
            }
        });

        FragmentsPagerAdapter adapter = new FragmentsPagerAdapter(this, getSupportFragmentManager());
        adapter.addFragment(ListMoviesFragment.newInstance(ListMoviesFragment.LIST_NOW_PLAYING), R.string.NowPlaying);
        adapter.addFragment(ListMoviesFragment.newInstance(ListMoviesFragment.LIST_POPULAR), R.string.Popular);
        adapter.addFragment(ListMoviesFragment.newInstance(ListMoviesFragment.LIST_TOP_RATED), R.string.TopRated);
        adapter.addFragment(ListMoviesFragment.newInstance(ListMoviesFragment.LIST_UPCOMING), R.string.Upcoming);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setBackgroundColor(ContextCompat.getColor(this, Theme.primaryColor()));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, Theme.selectedTabColor()));
        tabLayout.setTabTextColors(ContextCompat.getColor(this, Theme.unselectedTabColor()), ContextCompat.getColor(this, Theme.selectedTabColor()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(R.string.Search)
            .setIcon(R.drawable.ic_search)
            .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM)
            .setOnMenuItemClickListener(item -> {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("search_tab", SearchActivity.TAB_MOVIES);
                startActivity(intent);
                return true;
            });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ((Moviemade) getApplication()).eventBus().toObservable().subscribe(o -> {
            if (o instanceof Events.ChangeTheme) {
                navigationView.updateTheme();
                tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, Theme.selectedTabColor()));
                tabLayout.setTabTextColors(ContextCompat.getColor(this, Theme.unselectedTabColor()), ContextCompat.getColor(this, Theme.selectedTabColor()));
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}