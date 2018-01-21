package org.michaelbel.moviemade;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;

import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.databinding.ActivityPersonBinding;
import org.michaelbel.moviemade.mvp.base.BaseActivity;
import org.michaelbel.moviemade.rest.model.Cast;
import org.michaelbel.moviemade.rest.model.v3.People;
import org.michaelbel.moviemade.ui.fragment.ListMoviesFragment;
import org.michaelbel.moviemade.ui.fragment.PersonFragment;
import org.michaelbel.moviemade.ui.view.widget.FragmentsPagerAdapter;

import java.util.Locale;

public class PersonActivity extends BaseActivity {

    private Cast castPerson;

    public ActivityPersonBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_person);

        castPerson = (Cast) getIntent().getSerializableExtra("cast_person");
        People peoplePerson = getIntent().getParcelableExtra("people_person");

        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(view -> finish());

        FragmentsPagerAdapter adapter = new FragmentsPagerAdapter(this, getSupportFragmentManager());
        if (castPerson != null) {
            adapter.addFragment(PersonFragment.newInstance(castPerson), R.string.Info);
            adapter.addFragment(ListMoviesFragment.newInstance(ListMoviesFragment.LIST_BY_PERSON, castPerson), R.string.Movies);
        } else if (peoplePerson != null) {
            adapter.addFragment(PersonFragment.newInstance(peoplePerson), R.string.Info);
            //adapter.addFragment(ListMoviesFragment.newInstance(ListMoviesFragment.LIST_BY_PERSON, peoplePerson), R.string.Movies);
        }

        binding.viewPager.setAdapter(adapter);

        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        binding.tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        binding.tabLayout.setBackgroundColor(ContextCompat.getColor(this, Theme.primaryColor()));
        binding.tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, Theme.accentColor()));
        binding.tabLayout.setTabTextColors(ContextCompat.getColor(this, Theme.secondaryTextColor()), ContextCompat.getColor(this, Theme.accentColor()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(R.string.Share)
            .setIcon(R.drawable.ic_share)
            .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM)
            .setOnMenuItemClickListener(menuItem -> {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, String.format(Locale.US, Url.TMDB_PERSON, castPerson.id));
                startActivity(Intent.createChooser(intent, getString(R.string.ShareVia)));
                return true;
            });

        return super.onCreateOptionsMenu(menu);
    }
}