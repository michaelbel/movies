package org.michaelbel.application.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import org.michaelbel.application.R;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.rest.model.Movie;
import org.michaelbel.application.ui.fragment.ListMoviesFragment;
import org.michaelbel.application.ui.view.NavigationView;
import org.michaelbel.application.ui.view.widget.FragmentsPagerAdapter;
import org.michaelbel.application.util.KeyboardUtils;

@SuppressWarnings("all")
public class MainActivity extends AppCompatActivity {

    public Toolbar toolbar;
    public ViewPager viewPager;
    public TabLayout tabLayout;
    public DrawerLayout drawerLayout;
    public AppBarLayout appBarLayout;
    public NavigationView navigationView;

    private String requestToken;
    private CustomTabsServiceConnection connection;
    private Uri uri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppThemeLight);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(0x33000000);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        //toolbar.setNavigationIcon(R.drawable.ic_hamburger);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer);
        appBarLayout = findViewById(R.id.app_bar);

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setOnNavigationItemSelectedListener((view, position) -> {
            drawerLayout.closeDrawer(GravityCompat.START);

            if (position == 2) {
                // Movies
            } else if (position == 3) {
                startActivity(new Intent(this, GenresActivity.class));
            } else if (position == 4) {
                startActivity(new Intent(this, PopularPeopleActivity.class));
            } else if (position == 6) {
                startActivity(new Intent(this, FavsActivity.class));
            } else if (position == 7) {
              // Watchlist
            } else if (position == 9) {
                startActivity(new Intent(this, SettingsActivity.class));
            } else if (position == 10) {
                startActivity(new Intent(this, AboutActivity.class));
            }
        });

        viewPager = findViewById(R.id.view_pager);

        FragmentsPagerAdapter adapter = new FragmentsPagerAdapter(this, getSupportFragmentManager());
        adapter.addFragment(ListMoviesFragment.newInstance(ListMoviesFragment.LIST_NOW_PLAYING), R.string.NowPlaying);
        adapter.addFragment(ListMoviesFragment.newInstance(ListMoviesFragment.LIST_POPULAR), R.string.Popular);
        adapter.addFragment(ListMoviesFragment.newInstance(ListMoviesFragment.LIST_TOP_RATED), R.string.TopRated);
        adapter.addFragment(ListMoviesFragment.newInstance(ListMoviesFragment.LIST_UPCOMING), R.string.Upcoming);
        viewPager.setAdapter(adapter);

        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setBackgroundColor(ContextCompat.getColor(this, Theme.primaryColor()));
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
            if (drawerLayout != null) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    public boolean onKeyUp(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                if (getCurrentFocus() != null) {
                    KeyboardUtils.hideKeyboard(getCurrentFocus());
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            } else {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    public void startMovie(Movie movie) {
        Intent intent = new Intent(this, MovieActivity.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }
}