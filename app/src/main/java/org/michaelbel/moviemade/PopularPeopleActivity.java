package org.michaelbel.moviemade;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import org.michaelbel.moviemade.databinding.ActivityPopularPeopleBinding;
import org.michaelbel.moviemade.mvp.base.BaseActivity;
import org.michaelbel.moviemade.ui.fragment.PopularPeopleFragment;

public class PopularPeopleActivity extends BaseActivity {

    public ActivityPopularPeopleBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_popular_people);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(0x33000000);

        binding.toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(view -> binding.drawerLayout.openDrawer(GravityCompat.START));

        binding.toolbarTitle.setText(R.string.PopularPeople);
        binding.navigationView.setOnNavigationItemSelectedListener((view, position) -> {
            binding.drawerLayout.closeDrawer(GravityCompat.START);

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

        startFragment(PopularPeopleFragment.newInstance(), binding.fragmentView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(R.string.Search)
                .setIcon(R.drawable.ic_search)
                .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM)
                .setOnMenuItemClickListener(item -> {
                    Intent intent = new Intent(PopularPeopleActivity.this, SearchActivity.class);
                    intent.putExtra("search_tab", SearchActivity.TAB_PEOPLE);
                    startActivity(intent);
                    return true;
                });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
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