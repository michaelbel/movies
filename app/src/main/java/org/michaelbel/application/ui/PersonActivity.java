package org.michaelbel.application.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;

import org.michaelbel.application.R;
import org.michaelbel.application.databinding.ActivityPersonBinding;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.rest.model.Cast;
import org.michaelbel.application.rest.model.Movie;
import org.michaelbel.application.ui.mvp.BaseActivity;
import org.michaelbel.application.ui.mvp.BaseActivityModel;
import org.michaelbel.application.ui.mvp.BasePresenter;
import org.michaelbel.application.ui.fragment.ListMoviesFragment;
import org.michaelbel.application.ui.fragment.PersonFragment;
import org.michaelbel.application.ui.view.widget.FragmentsPagerAdapter;

@SuppressWarnings("all")
public class PersonActivity extends BaseActivity implements BaseActivityModel {

    private Cast person;
    public ActivityPersonBinding binding;
    private BasePresenter<BaseActivityModel> presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_person);
        presenter = new BasePresenter<>();
        presenter.attachView(this);

        if (savedInstanceState == null) {
            person = (Cast) getIntent().getSerializableExtra("person");
        }

        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(binding.toolbar);

        binding.toolbarTitle.setText(person.name);

        FragmentsPagerAdapter adapter = new FragmentsPagerAdapter(this, getSupportFragmentManager());
        adapter.addFragment(PersonFragment.newInstance(person), R.string.Info);
        adapter.addFragment(ListMoviesFragment.newInstance(ListMoviesFragment.LIST_BY_PERSON, person), R.string.Movies);

        binding.viewPager.setAdapter(adapter);

        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        binding.tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        binding.tabLayout.setBackgroundColor(ContextCompat.getColor(this, Theme.primaryColor()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void startMovie(Movie movie) {
        Intent intent = new Intent(this, MovieActivity.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }
}