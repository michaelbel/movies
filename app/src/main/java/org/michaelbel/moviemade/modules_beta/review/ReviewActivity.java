package org.michaelbel.moviemade.modules_beta.review;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.widget.LinearLayout;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.model.MovieRealm;
import org.michaelbel.moviemade.ui.base.BaseActivity;
import org.michaelbel.moviemade.data.dao.Movie;
import org.michaelbel.moviemade.rest.model.v3.Review;
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