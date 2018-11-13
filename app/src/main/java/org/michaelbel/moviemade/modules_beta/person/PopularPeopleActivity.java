package org.michaelbel.moviemade.modules_beta.person;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.eventbus.Events;
import org.michaelbel.moviemade.ui.base.BaseActivity;
import org.michaelbel.moviemade.modules_beta.watchlist.FavoriteActivity;
import org.michaelbel.moviemade.modules_beta.genres.GenresActivity;
import org.michaelbel.moviemade.modules_beta.watchlist.WatchlistActivity;
import org.michaelbel.moviemade.modules_beta.view.NavigationView;
import org.michaelbel.moviemade.ui.modules.about.AboutActivity;
import org.michaelbel.moviemade.ui.modules.main.MainActivity;
import org.michaelbel.moviemade.ui.modules.search.SearchActivity;
import org.michaelbel.moviemade.ui.modules.settings.SettingsActivity;

public class PopularPeopleActivity extends BaseActivity {

    public Toolbar toolbar;
    public TextView toolbarTitle;
    public DrawerLayout drawerLayout;
    public NavigationView navigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_people);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(0x33000000);

        drawerLayout = findViewById(R.id.drawer_layout);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));

        toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText(R.string.PopularPeople);

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setOnNavigationItemSelectedListener((view, position) -> {
            drawerLayout.closeDrawer(GravityCompat.START);

            if (position == 2) {
                startActivity(new Intent(this, MainActivity.class));
                this.finish();
            } else if (position == 3) {
                startActivity(new Intent(this, GenresActivity.class));
                this.finish();
            } else if (position == 4) {
                // PopularPeopleActivity
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

        startFragment(PopularPeopleFragment.newInstance(), R.id.fragment_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(R.string.search)
            .setIcon(R.drawable.ic_search)
            .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM)
            .setOnMenuItemClickListener(item -> {
                Intent intent = new Intent(PopularPeopleActivity.this, SearchActivity.class);
                //intent.putExtra("search_tab", SearchActivity.TAB_PEOPLE);
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
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }

        super.onBackPressed();
    }

    /*@Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            if (!binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.openDrawer(GravityCompat.START);
            } else {
                binding.drawerLayout.closeDrawer(GravityCompat.START);
            }
        }

        return super.onKeyUp(keyCode, event);
    }*/
}