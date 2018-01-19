package org.michaelbel.moviemade;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import org.michaelbel.moviemade.databinding.ActivityTrailersBinding;
import org.michaelbel.moviemade.mvp.base.BaseActivity;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.rest.model.Trailer;
import org.michaelbel.moviemade.ui.fragment.TrailersFragment;
import org.michaelbel.moviemade.util.AndroidUtilsDev;

import java.util.ArrayList;

public class TrailersActivity extends BaseActivity {

    public ActivityTrailersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_trailers);

        binding.toolbar.setLayoutParams(AndroidUtilsDev.getLayoutParams(binding.toolbar));
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(view -> finish());

        binding.toolbarTitle.setTitle(R.string.Trailers);

        ArrayList<Trailer> list = (ArrayList<Trailer>) getIntent().getSerializableExtra("list");
        Movie movie = (Movie) getIntent().getSerializableExtra("movie");
        startFragment(TrailersFragment.newInstance(movie, list), binding.fragmentView);
    }
}