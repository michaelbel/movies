package org.michaelbel.moviemade.ui.modules.reviews.activity;

import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.data.entity.Movie;
import org.michaelbel.moviemade.data.entity.Review;
import org.michaelbel.moviemade.ui.base.BaseActivity;
import org.michaelbel.moviemade.ui.modules.reviews.fragment.ReviewFragment;
import org.michaelbel.moviemade.utils.IntentsKt;
import org.michaelbel.moviemade.utils.SpannableUtil;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;

public class ReviewActivity extends BaseActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.toolbar_title) AppCompatTextView toolbarTitle;
    @BindView(R.id.toolbar_subtitle) AppCompatTextView toolbarSubtitle;

    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Movie movie = (Movie) getIntent().getSerializableExtra(IntentsKt.MOVIE);
        Review review = (Review) getIntent().getSerializableExtra(IntentsKt.REVIEW);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbarTitle.setText(movie.getTitle());
        toolbarSubtitle.setText(SpannableUtil.boldText(getString(R.string.review_by), getString(R.string.review_by, review.getAuthor())));

        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);

        if (savedInstanceState == null) {
            startFragment(ReviewFragment.newInstance(review), R.id.fragment_view);
        }
    }
}