package org.michaelbel.moviemade.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import org.michaelbel.topbar.TopBar;
import org.michaelbel.bottombar.BottomNavigationBar;
import org.michaelbel.bottombar.BottomNavigationItem;
import org.michaelbel.material.extensions.Extensions;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.mvp.base.BaseActivity;
import org.michaelbel.moviemade.ui.fragment.NowPlayingFragment;
import org.michaelbel.moviemade.ui.fragment.TopRatedFragment;
import org.michaelbel.moviemade.ui.fragment.UpcomingFragment;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

public class MainActivity extends BaseActivity {

    private Context context;
    private SharedPreferences sharedPreferences;

    private NowPlayingFragment nowPlayingFragment;
    private TopRatedFragment topRatedFragment;
    private UpcomingFragment upcomingFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppThemeTransparentStatusBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;
        sharedPreferences = context.getSharedPreferences("mainconfig", Context.MODE_PRIVATE);

        TopBar topbar = findViewById(R.id.topbar);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) topbar.getLayoutParams();
        params.topMargin = Extensions.getStatusBarHeight(context) + Extensions.dp(context, 4);

        ImageView searchIcon = findViewById(R.id.search_icon);
        searchIcon.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SearchActivity.class)));

        ImageView settingsIcon = findViewById(R.id.settings_icon);
        settingsIcon.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SettingsActivity.class)));

        nowPlayingFragment = new NowPlayingFragment();
        topRatedFragment = new TopRatedFragment();
        upcomingFragment = new UpcomingFragment();

        BottomNavigationBar bottomBar = findViewById(R.id.bottom_navigation_bar);
        bottomBar.setElevation(Extensions.dp(context, 4));
        bottomBar.setBarBackgroundColor(R.color.primary);
        bottomBar.setActiveColor(R.color.md_white);
        bottomBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_DEFAULT);
        bottomBar
            .addItem(new BottomNavigationItem(R.drawable.ic_fire, R.string.NowPlaying).setActiveColorResource(R.color.accent))
            .addItem(new BottomNavigationItem(R.drawable.ic_star_circle, R.string.TopRated).setActiveColorResource(R.color.accent))
            .addItem(new BottomNavigationItem(R.drawable.ic_movieroll, R.string.Upcoming).setActiveColorResource(R.color.accent))
            .setFirstSelectedPosition(sharedPreferences.getInt("fragment", 0))
            .initialise();
        bottomBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position) {
                    case 0:
                        startFragment(nowPlayingFragment, R.id.fragment_view);
                        break;
                    case 1:
                        startFragment(topRatedFragment, R.id.fragment_view);
                        break;
                    case 2:
                        startFragment(upcomingFragment, R.id.fragment_view);
                        break;
                }

                sharedPreferences.edit().putInt("fragment", position).apply();
            }

            @Override
            public void onTabUnselected(int position) {}

            @Override
            public void onTabReselected(int position) {
                switch (position) {
                    case 0:
                        nowPlayingFragment.recyclerView.smoothScrollToPosition(0);
                        break;
                    case 1:
                        topRatedFragment.recyclerView.smoothScrollToPosition(0);
                        break;
                    case 2:
                        upcomingFragment.recyclerView.smoothScrollToPosition(0);
                        break;
                }
            }
        });

        startCurrentFragment();
    }

    private void startCurrentFragment() {
        int position = sharedPreferences.getInt("fragment", 0);

        switch (position) {
            case 0:
                startFragment(nowPlayingFragment, R.id.fragment_view);
                break;
            case 1:
                startFragment(topRatedFragment, R.id.fragment_view);
                break;
            case 2:
                startFragment(upcomingFragment, R.id.fragment_view);
                break;
        }
    }
}