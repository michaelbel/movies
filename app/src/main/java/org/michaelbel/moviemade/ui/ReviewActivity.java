package org.michaelbel.moviemade.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.databinding.ActivityReviewBinding;
import org.michaelbel.moviemade.model.MovieRealm;
import org.michaelbel.moviemade.mvp.base.BaseActivity;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.rest.model.v3.Review;
import org.michaelbel.moviemade.ui.fragment.ReviewFragment;
import org.michaelbel.moviemade.util.AndroidUtilsDev;

public class ReviewActivity extends BaseActivity {

    public ActivityReviewBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_review);

        binding.toolbar.setLayoutParams(AndroidUtilsDev.getLayoutParams(binding.toolbar));
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(view -> finish());

        Review review = getIntent().getParcelableExtra("review");
        Movie movie = (Movie) getIntent().getSerializableExtra("movie");
        MovieRealm movieRealm = getIntent().getParcelableExtra("movieRealm");

        if (movieRealm != null) {
            startFragment(ReviewFragment.newInstance(review, movieRealm), binding.fragmentView);
        } else {
            startFragment(ReviewFragment.newInstance(review, movie), binding.fragmentView);
        }
    }
}