package org.michaelbel.moviemade.ui.modules.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.ui.base.BaseActivity;
import org.michaelbel.moviemade.ui.modules.account.AccountFragment;
import org.michaelbel.moviemade.ui.modules.main.fragments.NowPlayingFragment;
import org.michaelbel.moviemade.ui.modules.main.fragments.TopRatedFragment;
import org.michaelbel.moviemade.ui.modules.main.fragments.UpcomingFragment;
import org.michaelbel.moviemade.ui.modules.main.appbar.MainAppBar;
import org.michaelbel.moviemade.ui.modules.search.SearchActivity;
import org.michaelbel.moviemade.ui.modules.settings.SettingsActivity;
import org.michaelbel.moviemade.ui.widgets.bottombar.BottomNavigationBar;
import org.michaelbel.moviemade.ui.widgets.bottombar.BottomNavigationItem;
import org.michaelbel.moviemade.utils.DeviceUtil;
import org.michaelbel.moviemade.utils.SharedPrefsKt;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {

    public static final int DEFAULT_FRAGMENT = 0;

    private Unbinder unbinder;
    private NowPlayingFragment nowPlayingFragment;
    private TopRatedFragment topRatedFragment;
    private UpcomingFragment upcomingFragment;
    private AccountFragment profileFragment;

    @Inject SharedPreferences sharedPreferences;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.app_bar) MainAppBar appBar;
    @BindView(R.id.toolbar_title) AppCompatTextView toolbarTitle;
    @BindView(R.id.bottom_navigation_bar) BottomNavigationBar bottomBar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_search) {
            startActivity(new Intent(MainActivity.this, SearchActivity.class));
        } else if (item.getItemId() == R.id.item_settings) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppThemeTransparentStatusBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        Moviemade.getComponent().injest(this);

        setSupportActionBar(toolbar);
        toolbarTitle.setText(R.string.app_name);
        appBar.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent20));

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBar.getLayoutParams();
        params.topMargin = DeviceUtil.INSTANCE.getStatusBarHeight(this);

        nowPlayingFragment = new NowPlayingFragment();
        topRatedFragment = new TopRatedFragment();
        upcomingFragment = new UpcomingFragment();
        profileFragment = new AccountFragment();

        bottomBar.setTabSelectedListener(this);
        bottomBar.setBarBackgroundColor(R.color.primary);
        bottomBar.setActiveColor(R.color.md_white);
        bottomBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_DEFAULT);
        bottomBar.setFirstSelectedPosition(sharedPreferences.getInt(SharedPrefsKt.KEY_MAIN_FRAGMENT, DEFAULT_FRAGMENT))
            .addItem(new BottomNavigationItem(R.drawable.ic_fire, R.string.now_playing).setActiveColorResource(R.color.accent))
            .addItem(new BottomNavigationItem(R.drawable.ic_star_circle, R.string.top_rated).setActiveColorResource(R.color.accent))
            .addItem(new BottomNavigationItem(R.drawable.ic_movieroll, R.string.upcoming).setActiveColorResource(R.color.accent))
            .addItem(new BottomNavigationItem(R.drawable.ic_account_circle, R.string.account).setActiveColorResource(R.color.accent))
            .initialise();

        if (savedInstanceState == null) {
            startCurrentFragment();
        }
    }

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
            case 3:
                startFragment(profileFragment, R.id.fragment_view);
                toolbarTitle.setText(null);
                appBar.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.transparent));
                break;
        }

        if (position != 3) {
            sharedPreferences.edit().putInt(SharedPrefsKt.KEY_MAIN_FRAGMENT, position).apply();
            toolbarTitle.setText(R.string.app_name);
            appBar.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.transparent20));
        }
    }

    @Override
    public void onTabReselected(int position) {
        if (position != 3) {
            scrollToTop(position);
        }
    }

    @Override
    public void onTabUnselected(int position) {}

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String action = intent.getAction();
        String data = intent.getDataString();

        if (Intent.ACTION_VIEW.equals(action) && data != null) {
            profileFragment.presenter.createSessionId(sharedPreferences.getString(SharedPrefsKt.KEY_TOKEN, ""));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.toolbar)
    void toolbarClick(View v) {
        int position = bottomBar.getCurrentSelectedPosition();
        if (position != 3) {
            scrollToTop(position);
        }
    }

    private void scrollToTop(int position) {
        switch (position) {
            case 0:
                if (nowPlayingFragment.getAdapter().getItemCount() == 0) {
                    nowPlayingFragment.presenter.getNowPlaying();
                } else {
                    nowPlayingFragment.recyclerView.smoothScrollToPosition(0);
                }
                break;
            case 1:
                if (topRatedFragment.getAdapter().getItemCount() == 0) {
                    topRatedFragment.presenter.getTopRated();
                } else {
                    topRatedFragment.recyclerView.smoothScrollToPosition(0);
                }
                break;
            case 2:
                if (upcomingFragment.getAdapter().getItemCount() == 0) {
                    upcomingFragment.presenter.getUpcoming();
                } else {
                    upcomingFragment.recyclerView.smoothScrollToPosition(0);
                }
                break;
        }
    }

    private void startCurrentFragment() {
        int position = sharedPreferences.getInt(SharedPrefsKt.KEY_MAIN_FRAGMENT, DEFAULT_FRAGMENT);

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