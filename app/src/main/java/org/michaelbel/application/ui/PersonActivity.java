package org.michaelbel.application.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import org.michaelbel.application.ui.view.widget.FragmentsPagerAdapter;
import org.michaelbel.application.R;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.ui.fragment.PersonFragment;

public class PersonActivity extends AppCompatActivity {

    private int personId;
    private String personName;

    public Toolbar toolbar;
    public TextView toolbarTextView;
    public ViewPager viewPager;
    public TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        if (savedInstanceState == null) {
            personId = getIntent().getIntExtra("personId", 0);
            personName = getIntent().getStringExtra("personName");
        }

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);

        toolbarTextView = findViewById(R.id.toolbar_title);
        toolbarTextView.setText(personName);

        FragmentsPagerAdapter adapter = new FragmentsPagerAdapter(this, getSupportFragmentManager());
        adapter.addFragment(PersonFragment.newInstance(personId, personName), R.string.Info);

        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);

        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setBackgroundColor(ContextCompat.getColor(this, Theme.primaryColor()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void startMovie(int movieId, String movieTitle) {
        Intent intent = new Intent(this, MovieActivity.class);
        intent.putExtra("movieId", movieId);
        intent.putExtra("movieTitle", movieTitle);
        startActivity(intent);
    }
}