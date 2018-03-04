package org.michaelbel.moviemade.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.mvp.base.BaseActivity;
import org.michaelbel.moviemade.ui.fragment.FavoriteMoviesFragment;
import org.michaelbel.moviemade.ui.view.widget.FragmentsPagerAdapter;
import org.michaelbel.moviemade.utils.AndroidUtilsDev;

public class FavoriteActivity extends BaseActivity {

    public Toolbar toolbar;
    public TextView toolbarTitle;
    public ViewPager viewPager;
    public TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.toolbar_title);
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        toolbar.setLayoutParams(AndroidUtilsDev.getLayoutParams(toolbar));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());

        toolbarTitle.setText(R.string.Favorites);

        FragmentsPagerAdapter adapter = new FragmentsPagerAdapter(this, getSupportFragmentManager());
        adapter.addFragment(new FavoriteMoviesFragment(), R.string.Movies);

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setBackgroundColor(ContextCompat.getColor(this, Theme.primaryColor()));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, Theme.selectedTabColor()));
        tabLayout.setTabTextColors(ContextCompat.getColor(this, Theme.unselectedTabColor()), ContextCompat.getColor(this, Theme.selectedTabColor()));
    }
}