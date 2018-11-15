package org.michaelbel.moviemade.ui.modules.reviews.activity;

import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;

import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.data.dao.Movie;
import org.michaelbel.moviemade.data.dao.Review;
import org.michaelbel.moviemade.ui.base.BaseActivity;
import org.michaelbel.moviemade.ui.modules.reviews.fragment.ReviewFragment;
import org.michaelbel.moviemade.utils.SpannableUtil;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewActivity extends BaseActivity {

    public static final String MOVIE = "movie";
    public static final String REVIEW = "review";

    public Movie movie;
    public Review review;

    @BindView(R.id.toolbar) public Toolbar toolbar;
    @BindView(R.id.toolbar_title) AppCompatTextView toolbarTitle;
    @BindView(R.id.toolbar_subtitle) AppCompatTextView toolbarSubtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        ButterKnife.bind(this);
        Moviemade.getComponent().injest(this);

        movie = (Movie) getIntent().getSerializableExtra(MOVIE);
        review = (Review) getIntent().getSerializableExtra(REVIEW);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> finish());

        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);

        toolbarTitle.setText(movie.getTitle());
        toolbarSubtitle.setText(SpannableUtil.boldText(getString(R.string.review_by), getString(R.string.review_by, review.getAuthor())));

        ReviewFragment fragment = (ReviewFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (fragment != null) {
            fragment.setReview(review);
        }
    }
}