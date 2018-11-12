package org.michaelbel.moviemade.ui.modules.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import org.michaelbel.bottombar.BottomNavigationBar;
import org.michaelbel.bottombar.BottomNavigationItem;
import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.extensions.DeviceUtil;
import org.michaelbel.moviemade.ui.base.BaseActivity;
import org.michaelbel.moviemade.ui.modules.main.fragments.NowPlayingFragment;
import org.michaelbel.moviemade.ui.modules.main.fragments.TopRatedFragment;
import org.michaelbel.moviemade.ui.modules.main.fragments.UpcomingFragment;
import org.michaelbel.moviemade.ui.modules.search.SearchActivity;
import org.michaelbel.moviemade.ui.modules.settings.SettingsActivity;
import org.michaelbel.moviemade.ui.modules.main.views.topbar.TopBar;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    public static final String KEY_CURRENT_FRAGMENT = "fragment";
    public static final int CURRENT_FRAGMENT_DEFAULT = 0;

    private NowPlayingFragment nowPlayingFragment;
    private TopRatedFragment topRatedFragment;
    private UpcomingFragment upcomingFragment;

    @Inject
    SharedPreferences sharedPreferences;

    @BindView(R.id.topbar)
    public TopBar topbar;

    @BindView(R.id.search_icon)
    public ImageView searchIcon;

    @BindView(R.id.settings_icon)
    public ImageView settingsIcon;

    @BindView(R.id.bottom_navigation_bar)
    public BottomNavigationBar bottomBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppThemeTransparentStatusBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Moviemade.getComponent().injest(this);

        topbar.setTitle(R.string.app_name);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) topbar.getLayoutParams();
        params.topMargin = DeviceUtil.getStatusBarHeight(this);

        searchIcon.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SearchActivity.class)));
        settingsIcon.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SettingsActivity.class)));

        nowPlayingFragment = new NowPlayingFragment();
        topRatedFragment = new TopRatedFragment();
        upcomingFragment = new UpcomingFragment();

        bottomBar.setBarBackgroundColor(R.color.primary);
        bottomBar.setActiveColor(R.color.md_white);
        bottomBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_DEFAULT);
        bottomBar
            .addItem(new BottomNavigationItem(R.drawable.ic_fire, R.string.NowPlaying).setActiveColorResource(R.color.accent))
            .addItem(new BottomNavigationItem(R.drawable.ic_star_circle, R.string.TopRated).setActiveColorResource(R.color.accent))
            .addItem(new BottomNavigationItem(R.drawable.ic_movieroll, R.string.Upcoming).setActiveColorResource(R.color.accent))
            .setFirstSelectedPosition(sharedPreferences.getInt(KEY_CURRENT_FRAGMENT, CURRENT_FRAGMENT_DEFAULT))
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

                sharedPreferences.edit().putInt(KEY_CURRENT_FRAGMENT, position).apply();
            }

            @Override
            public void onTabUnselected(int position) {}

            @Override
            public void onTabReselected(int position) {
                switch (position) {
                    case 0:
                        if (nowPlayingFragment.getAdapter().isEmpty()) {
                            nowPlayingFragment.presenter.loadNowPlayingMovies();
                        } else {
                            nowPlayingFragment.recyclerView.smoothScrollToPosition(0);
                        }
                        break;
                    case 1:
                        if (topRatedFragment.getAdapter().isEmpty()) {
                            topRatedFragment.presenter.loadNowPlayingMovies();
                        } else {
                            topRatedFragment.recyclerView.smoothScrollToPosition(0);
                        }
                        break;
                    case 2:
                        if (upcomingFragment.getAdapter().isEmpty()) {
                            upcomingFragment.presenter.loadNowPlayingMovies();
                        } else {
                            upcomingFragment.recyclerView.smoothScrollToPosition(0);
                        }
                        break;
                }
            }
        });

        startCurrentFragment();
    }

    private void startCurrentFragment() {
        int position = sharedPreferences.getInt(KEY_CURRENT_FRAGMENT, CURRENT_FRAGMENT_DEFAULT);

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