package org.michaelbel.moviemade.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.model.MovieRealm;
import org.michaelbel.moviemade.mvp.base.BaseActivity;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.rest.model.v3.Review;
import org.michaelbel.moviemade.ui.fragment.ReviewFragment;
import org.michaelbel.moviemade.utils.AndroidUtilsDev;

public class ReviewActivity extends BaseActivity {

    public Toolbar toolbar;
    public LinearLayout authorLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setLayoutParams(AndroidUtilsDev.getLayoutParams(toolbar));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());

        authorLayout = findViewById(R.id.author_layout);

        Review review = getIntent().getParcelableExtra("review");
        Movie movie = (Movie) getIntent().getSerializableExtra("movie");
        MovieRealm movieRealm = getIntent().getParcelableExtra("movieRealm");

        if (movieRealm != null) {
            startFragment(ReviewFragment.newInstance(review, movieRealm), R.id.fragment_view);
        } else {
            startFragment(ReviewFragment.newInstance(review, movie), R.id.fragment_view);
        }
    }
}