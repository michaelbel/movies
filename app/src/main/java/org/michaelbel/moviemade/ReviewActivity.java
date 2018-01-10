package org.michaelbel.moviemade;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;

import org.michaelbel.moviemade.databinding.ActivityReviewBinding;
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

        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) binding.toolbar.getLayoutParams();
        params.setScrollFlags(AndroidUtilsDev.floatingToolbar() ? AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP : 0);

        binding.toolbar.setLayoutParams(params);
        setSupportActionBar(binding.toolbar);

        if (savedInstanceState == null) {
            Review review = getIntent().getParcelableExtra("review");
            Movie movie = (Movie) getIntent().getSerializableExtra("movie");

            startFragment(ReviewFragment.newInstance(review, movie), binding.fragmentView);
        }
    }
}